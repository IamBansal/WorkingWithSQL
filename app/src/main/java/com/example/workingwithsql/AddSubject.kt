package com.example.workingwithsql

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class AddSubject : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_subject)

        val DB = SQLHelper(applicationContext)
        val title_input = findViewById<EditText>(R.id.etTitle)
        val desc_input = findViewById<EditText>(R.id.etDesc)

        findViewById<Button>(R.id.btnAdd).setOnClickListener {
            val title_text = title_input.text.toString().trim()
            val desc_text = desc_input.text.toString().trim()

            DB.addData(title_text, desc_text)
            Toast.makeText(this, "Subject Added", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, MainActivity::class.java))

        }

    }
}