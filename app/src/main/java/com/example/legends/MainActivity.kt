package com.example.legends

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import com.example.legends.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

//        findViewById<Button>(R.id.buttonSaveInputName).setOnClickListener { addReaderName(it) }

        binding.buttonSaveInputName.setOnClickListener {
            addReaderName(it)
        }
    }

    private fun addReaderName(view: View) {
//        val inputName = findViewById<EditText>(R.id.editTextInputName)
//        val readerName = findViewById<TextView>(R.id.textViewInputName)

        binding.apply {
        textViewInputName.text = binding.editTextInputName.text
        editTextInputName.visibility = View.GONE
        buttonSaveInputName.visibility = View.GONE
        textViewInputName.visibility = View.VISIBLE
        }
    }
}