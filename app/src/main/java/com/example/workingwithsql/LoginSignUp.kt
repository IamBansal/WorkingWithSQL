package com.example.workingwithsql

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class LoginSignUp : AppCompatActivity() {

    private var signUpBtn: Button? = null
    private var loginBtn: Button? = null
    private var emailId: EditText? = null
    private var password: EditText? = null
    private var firebaseAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_sign_up)

        signUpBtn = findViewById(R.id.btnSign)
        loginBtn = findViewById(R.id.btnLogin)
        emailId = findViewById(R.id.editTextTextEmailAddress)
        password = findViewById(R.id.editTextTextPassword)
        firebaseAuth = FirebaseAuth.getInstance()

        findViewById<TextView>(R.id.tvReset).setOnClickListener {
            startActivity(Intent(this, ResetActivity::class.java))
        }

        findViewById<TextView>(R.id.tvUpdateEmail).setOnClickListener {
            startActivity(Intent(this, UpdateEmail::class.java))
        }

        signUpBtn?.setOnClickListener {
            registerNewUser()
        }

        loginBtn?.setOnClickListener {
            loginUser()
        }

    }

    //Function for logging in the user.
    private fun loginUser() {

        val emailText = emailId?.text.toString().trim()
        val passwordText = password?.text.toString().trim()

        if (TextUtils.isEmpty(emailText) || TextUtils.isEmpty(passwordText)) {
            Toast.makeText(applicationContext, "Fields can't be empty.", Toast.LENGTH_SHORT).show()
        } else {
            firebaseAuth?.signInWithEmailAndPassword(emailText, passwordText)
                ?.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        //For logging in only verified users.
                        val user : FirebaseUser = firebaseAuth?.currentUser!!
                        if (user.isEmailVerified) {
                            startActivity(Intent(applicationContext, AddSubject::class.java))
                            Toast.makeText(
                                applicationContext,
                                "Logged in successfully.",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            Toast.makeText(
                                applicationContext,
                                "Email not verified.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                        val error = task.exception?.message
                        Toast.makeText(applicationContext, "Error : $error", Toast.LENGTH_LONG)
                            .show()
                    }

                }

        }

    }

    //Function for registering new user.
    private fun registerNewUser() {

        val emailText = emailId?.text.toString().trim()
        val passwordText = password?.text.toString().trim()

        //For checking if user has not entered email or password.
        if (TextUtils.isEmpty(emailText) || TextUtils.isEmpty(passwordText)) {
            Toast.makeText(applicationContext, "Fields can't be empty.", Toast.LENGTH_SHORT).show()
        } else {
            //For checking status on clicking signup button.
            firebaseAuth?.createUserWithEmailAndPassword(emailText, passwordText)
                ?.addOnCompleteListener { task ->

                    //If registration was successful
                    if (task.isSuccessful) {
                        Toast.makeText(applicationContext, "Account created.", Toast.LENGTH_SHORT)
                            .show()

                        //For verifying the user.
                        val user:FirebaseUser = firebaseAuth?.currentUser!!
                        user.sendEmailVerification().addOnCompleteListener { task1 ->
                            if (task1.isSuccessful) {
                                Toast.makeText(applicationContext, "Verify your credentials.", Toast.LENGTH_SHORT)
                                    .show()
                            } else {
                                Toast.makeText(applicationContext, "Error : ${task1.exception?.message}", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }

                    }

                    //If some error occurred like, invalid format for email.
                    else {
                        val error = task.exception?.message
                        Toast.makeText(applicationContext, "Error : $error", Toast.LENGTH_LONG)
                            .show()
                    }
                }

        }

    }
}