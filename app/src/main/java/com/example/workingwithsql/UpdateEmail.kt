package com.example.workingwithsql

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class UpdateEmail : AppCompatActivity() {

    private var oldEmail: EditText? = null
    private var passWord: EditText? = null
    private var newEmail: EditText? = null
    private var updateButton: Button? = null
    private var firebaseAuth: FirebaseAuth? = null
    private var user: FirebaseUser? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_email)

        updateButton = findViewById(R.id.btnUpdate)
        oldEmail = findViewById(R.id.etEmailOld)
        newEmail = findViewById(R.id.etEmailNew)
        passWord = findViewById(R.id.etPass)
        firebaseAuth = FirebaseAuth.getInstance()
        user = firebaseAuth?.currentUser!!

        updateButton?.setOnClickListener {
            updateEmail()
        }

    }

    //Function for updating email id.
    private fun updateEmail() {

        val emailOld = oldEmail?.text.toString().trim()
        val password = passWord?.text.toString().trim()
        val emailNew = newEmail?.text.toString().trim()

        //For re-authenticating
        if (TextUtils.isEmpty(emailOld) || TextUtils.isEmpty(emailNew) || TextUtils.isEmpty(password)) {
            Toast.makeText(applicationContext, "Fields can't be empty.", Toast.LENGTH_SHORT).show()
        } else {
            val userInfo = EmailAuthProvider.getCredential(emailOld, password)

            user?.reauthenticate(userInfo)?.addOnCompleteListener { task ->
                if (task.isSuccessful) {

                    //For updating
                    user!!.updateEmail(emailNew).addOnCompleteListener { task1 ->
                        if (task1.isSuccessful) {
                            Toast.makeText(
                                applicationContext,
                                "Email updated successfully.",
                                Toast.LENGTH_SHORT
                            ).show()
                            startActivity(Intent(applicationContext, MainActivity::class.java))
                        } else {
                            Toast.makeText(
                                applicationContext,
                                "Error : ${task1.exception?.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                } else {
                    Toast.makeText(
                        applicationContext,
                        "Error : ${task.exception?.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }
        }

    }
}