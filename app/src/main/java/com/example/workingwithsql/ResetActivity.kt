package com.example.workingwithsql

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class ResetActivity : AppCompatActivity() {

    private var emailId: EditText? = null
    private var resetButton: Button? = null
    private var fireBaseAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset)

        emailId = findViewById(R.id.etEmailAddressReset)
        resetButton = findViewById(R.id.btnReset)
        fireBaseAuth = FirebaseAuth.getInstance()

        resetButton?.setOnClickListener {
            resetPassword()
        }

    }

    //Function for Password Reset.
    private fun resetPassword() {

        val emailText = emailId?.text.toString().trim()

        if (TextUtils.isEmpty(emailText)) {
            Toast.makeText(applicationContext, "Field can't be empty.", Toast.LENGTH_SHORT).show()
        } else {
            fireBaseAuth?.sendPasswordResetEmail(emailText)?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(
                        applicationContext,
                        "Request for reset sent successfully.\nWait for mail.",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    val error = task.exception?.message
                    Toast.makeText(applicationContext, "Error : $error", Toast.LENGTH_LONG).show()
                }

            }

        }

    }
}