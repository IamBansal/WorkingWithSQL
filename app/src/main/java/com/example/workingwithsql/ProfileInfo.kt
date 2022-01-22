package com.example.workingwithsql

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class ProfileInfo : AppCompatActivity() {

    private var okayButton : Button? = null
    private var updateButton : Button? = null
    private var firstNameInfo : TextView? = null
    private var lastNameInfo : TextView? = null
    private var usernameInfo : TextView? = null
    private var phoneInfo : TextView? = null
    private var firebaseAuth : FirebaseAuth? = null
    private var firebaseData : DatabaseReference? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_info)

        okayButton = findViewById(R.id.btnOkayInfo)
        updateButton = findViewById(R.id.btnUpadteInfo)
        firstNameInfo = findViewById(R.id.tvInfoFirstName)
        lastNameInfo = findViewById(R.id.tvInfoLastName)
        usernameInfo = findViewById(R.id.tvInfoUsername)
        phoneInfo = findViewById(R.id.tvInfoPhone)
        firebaseAuth = FirebaseAuth.getInstance()
        firebaseData = FirebaseDatabase.getInstance().reference.child("Users").child(firebaseAuth?.currentUser!!.uid)

        okayButton?.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        updateButton?.setOnClickListener {
            startActivity(Intent(this, ProfileUpdate::class.java))
        }

        firebaseData?.addValueEventListener(object : ValueEventListener{
            @SuppressLint("SetTextI18n")
            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()) {
                    val firstNameText = snapshot.child("First Name").value as String
                    val lastNameText = snapshot.child("Last Name").value as String
                    val usernameText = snapshot.child("Username").value as String
                    val phoneText = snapshot.child("Phone Number").value as String

                    firstNameInfo?.text = "First Name : $firstNameText"
                    lastNameInfo?.text = "Last Name : $lastNameText"
                    usernameInfo?.text = "Username : $usernameText"
                    phoneInfo?.text = "Phone No. : $phoneText"

                }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

    }
}