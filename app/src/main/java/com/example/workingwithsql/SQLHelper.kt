package com.example.workingwithsql

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SQLHelper(context: Context) : SQLiteOpenHelper(context, DB_Name, null, 1) {

    companion object {
        const val DB_Name = "subjects.db "
        const val TB_Name = "Subject "
        const val id = "ID"
        const val title = "S_title"
        const val desc = "S_desc"
    }

    override fun onCreate(p0: SQLiteDatabase?) {
        p0?.execSQL("create table $TB_Name(ID INTEGER PRIMARY KEY AUTOINCREMENT, S_title TEXT, S_desc TEXT)")
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        p0?.execSQL("DROP TABLE IF EXISTS $TB_Name")
    }

    fun addData(title_text : String, desc_text : String) {
        val DB = this.writableDatabase
        val values = ContentValues()
        values.put(title, title_text)
        values.put(desc, desc_text)

        DB.insert(TB_Name, null, values)
    }

    fun deleteData(id: String): Int {
        val DB = this.writableDatabase
        return DB.delete(TB_Name, "id = ?", arrayOf(id))
    }

    val dataGetter : Cursor
        get() {
            val DB = this.writableDatabase
            return DB.rawQuery("select * from $TB_Name", null)
        }

}