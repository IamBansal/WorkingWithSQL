package com.example.workingwithsql

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.HashMap

class ProfileUpdate : AppCompatActivity() {

    private var firstName: EditText? = null
    private var lastName: EditText? = null
    private var username: EditText? = null
    private var phone: EditText? = null
    private var firebaseAuth: FirebaseAuth? = null
    private var firebaseData: DatabaseReference? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_update)

        firstName = findViewById(R.id.etFirstName)
        lastName = findViewById(R.id.etLastName)
        username = findViewById(R.id.etUserName)
        phone = findViewById(R.id.etPhone)
        firebaseAuth = FirebaseAuth.getInstance()

        //For getting the logged in user's id to update data to that id.
        firebaseData = FirebaseDatabase.getInstance().reference.child("Users")
            .child(firebaseAuth?.currentUser!!.uid)

        findViewById<Button>(R.id.btnUpdateProfile).setOnClickListener {
            saveUserInfo()
        }

    }

    //Function for updating profile.
    private fun saveUserInfo() {

        val firstNameText = firstName?.text.toString().trim()
        val lastNameText = lastName?.text.toString().trim()
        val usernameText = username?.text.toString().trim()
        val phoneNumber = phone?.text.toString().trim()

        if (TextUtils.isEmpty(firstNameText) || TextUtils.isEmpty(usernameText) || TextUtils.isEmpty(
                phoneNumber
            )
        ) {
            Toast.makeText(this, "Fill the required credentials.", Toast.LENGTH_SHORT).show()
        } else {

            //HashMap used for key-value pair -- Json format
            val userInfo = HashMap<String, Any>()
            userInfo["First Name"] = firstNameText
            userInfo["Last Name"] = lastNameText
            userInfo["Username"] = usernameText
            userInfo["Phone Number"] = phoneNumber

            //For updating data to realtime database
            firebaseData?.updateChildren(userInfo)?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Profile updated.", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Error : ${task.exception?.message}", Toast.LENGTH_SHORT)
                        .show()
                }

            }

        }

    }

}