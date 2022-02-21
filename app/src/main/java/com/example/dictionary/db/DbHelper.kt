package com.example.dictionary.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DbHelper(context: Context) : SQLiteOpenHelper(context, DbMainClass.DB_NAME, null, DbMainClass.DB_VERSION) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(DbMainClass.SQL_CREATE_TABLE);
    }

    override fun onUpgrade(db: SQLiteDatabase?, version1: Int, version2: Int) {
        db?.execSQL(DbMainClass.SQL_DELETE_TABLE);
        onCreate(db);
    }

}