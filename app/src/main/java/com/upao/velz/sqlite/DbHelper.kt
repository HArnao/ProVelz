package com.upao.velz.sqlite

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DbHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        val DATABASE_NAME = "velz.db"
        val DATABASE_VERSION = 1
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(Tables().CREATE_TABLE_USER)
        db.execSQL(Tables().CREATE_TABLE_APPOINTMENT)
        /*db.execSQL(Tables().CREATE_TABLE_TREATMENT)*/
        Log.d("DbHelper", "Tablas creadas correctamente")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS " + Tables().TABLE_USER)
        db.execSQL(Tables().CREATE_TABLE_USER)

        db.execSQL("DROP TABLE IF EXISTS " + Tables().TABLE_APPOINTMENT)
        db.execSQL(Tables().CREATE_TABLE_APPOINTMENT)

        /*db.execSQL("DROP TABLE IF EXISTS " + Tables().TABLE_TREATMENT)
        db.execSQL(Tables().CREATE_TABLE_TREATMENT)*/

        onCreate(db)
    }

}