package com.example.workingwithsql

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.IOException
import java.util.*

class ImageUploader : AppCompatActivity() {

    private var imageView: ImageView? = null
    private var uploadButton: Button? = null
    private var imageUri: Uri? = null
    private var firebaseStorage: FirebaseStorage? = null
    private var imageRef: StorageReference? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_uploader)

        imageView = findViewById(R.id.ivImage)
        uploadButton = findViewById(R.id.btnUploadImage)
        firebaseStorage = FirebaseStorage.getInstance()
        imageRef = firebaseStorage?.reference

        imageView?.setOnClickListener {
            pickImage()
        }

        uploadButton?.setOnClickListener {
            uploadImageToFirebase()
        }

    }

    //For uploading image to firebase storage
    private fun uploadImageToFirebase() {
        if (imageUri != null) {
            val ref = imageRef?.child(UUID.randomUUID().toString())
            ref?.putFile(imageUri!!)?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(applicationContext, "Image Uploaded", Toast.LENGTH_SHORT).show()
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

    //For picking image from gallery.
    private fun pickImage() {

        val gallery = Intent()
        gallery.type = "image/*"
        gallery.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(gallery, 111)

    }

    //for setting picked image to image view.
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 111 && resultCode == Activity.RESULT_OK && data != null) {
            imageUri = data.data
            imageView?.setImageURI(imageUri)

            try {

                val image = MediaStore.Images.Media.getBitmap(contentResolver, imageUri)
                imageView?.setImageBitmap(image)

            } catch (error: IOException) {
                Toast.makeText(applicationContext, "error : $error", Toast.LENGTH_SHORT).show()
            }

        }

    }

}