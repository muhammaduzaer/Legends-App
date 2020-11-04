package com.example.legends

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.buttonSaveInputName).setOnClickListener { addReaderName(it) }
    }

    private fun addReaderName(view: View) {
        val inputName = findViewById<EditText>(R.id.editTextInputName)
        val readerName = findViewById<TextView>(R.id.textViewInputName)

        readerName.text = inputName.text
        inputName.visibility = View.GONE
        view.visibility = View.GONE
        readerName.visibility = View.VISIBLE
    }
}