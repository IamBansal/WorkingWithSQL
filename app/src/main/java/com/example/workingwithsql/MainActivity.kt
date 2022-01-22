package com.example.workingwithsql

import android.content.Intent
import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.ArrayList

class MainActivity : AppCompatActivity() {

    private lateinit var lists : ArrayList<CustomItem>
    private lateinit var DB : SQLHelper
    private lateinit var data : Cursor


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.btnGo).setOnClickListener {
            startActivity(Intent(this, AddSubject::class.java))
        }

        //Just for checking the firebase vala scene.
        findViewById<Button>(R.id.btnFireBAse).setOnClickListener {
            startActivity(Intent(this, LoginSignUp::class.java))
        }

        findViewById<Button>(R.id.btnUpload).setOnClickListener {
            startActivity(Intent(this, ImageUploader::class.java))
        }

        lists = ArrayList<CustomItem>()
        DB = SQLHelper(applicationContext)
        data = DB.dataGetter

        val adapter = CustomItemAdapter(applicationContext, lists)
        val recyclerView = findViewById<RecyclerView>(R.id.rvRecycler)

        showData()

        recyclerView.layoutManager = GridLayoutManager(applicationContext, 1)
        recyclerView.adapter = adapter

    }

    //For exitting the app when back is pressed at launcher.
    override fun onBackPressed() {
        finishAffinity()
    }

    private fun showData(){
        if (data.count == 0) {
            Toast.makeText(this, "There is no item in notes.", Toast.LENGTH_SHORT).show()
        }

        while (data.moveToNext()) {
            lists.add(CustomItem(data.getString(0), data.getString(1), data.getString(2)))
        }
    }

    override fun onStart() {
        super.onStart()
        showData()
    }

}