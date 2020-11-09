package com.example.legends.widgets

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.res.ColorStateList
import android.graphics.Canvas
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.RippleDrawable
import android.graphics.drawable.StateListDrawable
import android.os.Build
import android.util.AttributeSet
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.widget.AppCompatButton
import com.example.legends.R


class CustomButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatButton(context, attrs, defStyleAttr) {

    private companion object {
        val TOP_LEFT = 1
        val TOP_RIGHT = 2
        val BOTTOM_RIGHT = 4
        val BOTTOM_LEFT = 8
    }

    private var mShapeMode = 0
    var mFillColor = 0

    private var mPressedColor = 0

    private var mStrokeColor = 0

    private var mStrokeWidth = 0

    private var mCornerRadius = 0
    private var mCornerPosition = 0


    private var mActiveEnable = false


    private var mStartColor = 0

    private var mEndColor = 0


    private var mOrientation = 0

    private var mDrawablePosition = -1

    private val normalGradientDrawable: GradientDrawable by lazy { GradientDrawable() }
    private val pressedGradientDrawable: GradientDrawable by lazy { GradientDrawable() }

    private val stateListDrawable: StateListDrawable by lazy { StateListDrawable() }

    private var contentWidth = 0f

    private var contentHeight = 0f

    init {
        context.obtainStyledAttributes(attrs, R.styleable.OraanButton).apply {
            mShapeMode = getInt(R.styleable.OraanButton_csb_shapeMode, 0)
            mFillColor = getColor(R.styleable.OraanButton_csb_fillColor, 0xFFFFFFFF.toInt())
            mPressedColor = getColor(R.styleable.OraanButton_csb_pressedColor, 0xFF666666.toInt())
            mStrokeColor = getColor(R.styleable.OraanButton_csb_strokeColor, 0)
            mStrokeWidth = getDimensionPixelSize(R.styleable.OraanButton_csb_strokeWidth, 0)
            mCornerRadius = getDimensionPixelSize(R.styleable.OraanButton_csb_cornerRadius, 0)
            mCornerPosition = getInt(R.styleable.OraanButton_csb_cornerPosition, -1)
            mActiveEnable = getBoolean(R.styleable.OraanButton_csb_activeEnable, false)
            mDrawablePosition = getInt(R.styleable.OraanButton_csb_drawablePosition, -1)
            mStartColor = getColor(R.styleable.OraanButton_csb_startColor, 0xFFFFFFFF.toInt())
            mEndColor = getColor(R.styleable.OraanButton_csb_endColor, 0xFFFFFFFF.toInt())
            mOrientation = getColor(R.styleable.OraanButton_csb_orientation, 0)
            recycle()
        }
        val FOCUS_TOUCH_LISTENER: View.OnTouchListener = View.OnTouchListener { v, event ->
            val drawable = v.background
            when (event.action) {
                MotionEvent.ACTION_DOWN, MotionEvent.ACTION_BUTTON_PRESS -> {

                    drawable.setColorFilter(0x20000000, PorterDuff.Mode.SRC_ATOP)
                    v.invalidate()
                }
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                    drawable.clearColorFilter()
                    v.invalidate()
                }
            }
            false
        }

        this.setOnTouchListener(FOCUS_TOUCH_LISTENER)
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        with(normalGradientDrawable) {
            if (mStartColor != 0xFFFFFFFF.toInt() && mEndColor != 0xFFFFFFFF.toInt()) {
                colors = intArrayOf(mStartColor, mEndColor)
                when (mOrientation) {
                    0 -> orientation = GradientDrawable.Orientation.TOP_BOTTOM
                    1 -> orientation = GradientDrawable.Orientation.LEFT_RIGHT
                }
            } else {
                setColor(mFillColor)
            }
            when (mShapeMode) {
                0 -> shape = GradientDrawable.RECTANGLE
                1 -> shape = GradientDrawable.OVAL
                2 -> shape = GradientDrawable.LINE
                3 -> shape = GradientDrawable.RING
            }
            if (mCornerPosition == -1) {
                cornerRadius = mCornerRadius.toFloat()
            } else {
                cornerRadii = getCornerRadiusByPosition()
            }
            if (mStrokeColor != 0) {
                setStroke(mStrokeWidth, mStrokeColor)
            }
        }

        background = if (mActiveEnable) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                RippleDrawable(ColorStateList.valueOf(mPressedColor), normalGradientDrawable, null)
            } else {
                with(pressedGradientDrawable) {
                    setColor(mPressedColor)
                    when (mShapeMode) {
                        0 -> shape = GradientDrawable.RECTANGLE
                        1 -> shape = GradientDrawable.OVAL
                        2 -> shape = GradientDrawable.LINE
                        3 -> shape = GradientDrawable.RING
                    }
                    cornerRadius = mCornerRadius.toFloat()
                    setStroke(mStrokeWidth, mStrokeColor)
                }

                stateListDrawable.apply {
                    addState(intArrayOf(android.R.attr.state_pressed), pressedGradientDrawable)
                    addState(intArrayOf(), normalGradientDrawable)
                }
            }
        } else {
            normalGradientDrawable
        }
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        if (mDrawablePosition > -1) {
            compoundDrawables.let {
                val drawable: Drawable? = compoundDrawables[mDrawablePosition]
                drawable?.let {
                    val drawablePadding = compoundDrawablePadding
                    when (mDrawablePosition) {
                        0, 2 -> {
                            val drawableWidth = it.intrinsicWidth
                            val textWidth = paint.measureText(text.toString())
                            contentWidth = textWidth + drawableWidth + drawablePadding
                            val rightPadding = (width - contentWidth).toInt()
                            setPadding(0, 0, rightPadding, 0)
                        }
                        1, 3 -> {
                            val drawableHeight = it.intrinsicHeight
                            val fm = paint.fontMetrics
                            val singeLineHeight = Math.ceil(fm.descent.toDouble() - fm.ascent.toDouble()).toFloat()
                            val totalLineSpaceHeight = (lineCount - 1) * lineSpacingExtra
                            val textHeight = singeLineHeight * lineCount + totalLineSpaceHeight
                            contentHeight = textHeight + drawableHeight + drawablePadding
                            val bottomPadding = (height - contentHeight).toInt()
                            setPadding(0, 0, 0, bottomPadding)
                        }
                    }
                }

            }
        }
        gravity = Gravity.CENTER
        isClickable = true
        changeTintContextWrapperToActivity()
    }

    override fun onDraw(canvas: Canvas) {
        when {
            contentWidth > 0 && (mDrawablePosition == 0 || mDrawablePosition == 2) -> canvas.translate((width - contentWidth) / 2, 0f)
            contentHeight > 0 && (mDrawablePosition == 1 || mDrawablePosition == 3) -> canvas.translate(0f, (height - contentHeight) / 2)
        }
        super.onDraw(canvas)
    }

    private fun changeTintContextWrapperToActivity() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            getActivity()?.let {
                var clazz: Class<*>? = this::class.java
                while (clazz != null) {
                    try {
                        val field = clazz.getDeclaredField("mContext")
                        field.isAccessible = true
                        field.set(this, it)
                        break
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                    clazz = clazz.superclass
                }
            }
        }
    }


    private fun getActivity(): Activity? {
        var context = context
        while (context is ContextWrapper) {
            if (context is Activity) {
                return context
            }
            context = context.baseContext
        }
        return null
    }


    private fun getCornerRadiusByPosition(): FloatArray {
        val result = floatArrayOf(0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f)
        val cornerRadius = mCornerRadius.toFloat()
        if (containsFlag(mCornerPosition, TOP_LEFT)) {
            result[0] = cornerRadius
            result[1] = cornerRadius
        }
        if (containsFlag(mCornerPosition, TOP_RIGHT)) {
            result[2] = cornerRadius
            result[3] = cornerRadius
        }
        if (containsFlag(mCornerPosition, BOTTOM_RIGHT)) {
            result[4] = cornerRadius
            result[5] = cornerRadius
        }
        if (containsFlag(mCornerPosition, BOTTOM_LEFT)) {
            result[6] = cornerRadius
            result[7] = cornerRadius
        }
        return result
    }

    private fun containsFlag(flagSet: Int, flag: Int): Boolean {
        return flagSet or flag == flagSet
    }
}