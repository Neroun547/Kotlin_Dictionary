package com.example.dictionary.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase

class DbManager (val context: Context) {
    val dbHelper = DbHelper(context)
    var db: SQLiteDatabase? = null

    fun openDb() {
        db = dbHelper.writableDatabase
    }

    fun close() {
        dbHelper.close()
    }

    fun addWord(word: String, translate: String, logoLetter: String, hash: String) {
        val values = ContentValues().apply {
            put(DbMainClass.WORD_COLUMN , word)
            put(DbMainClass.HASH_COLUMN, hash)
            put(DbMainClass.TRANSLATE_COLUMN, translate)
            put(DbMainClass.LOGO_LETTER, logoLetter)
        }

        db?.insert(DbMainClass.TABLE_NAME, null, values)
    }

    fun loadWords(logoLetter: String): ArrayList<Item> {
        val dataList = ArrayList<Item>()

        val cursor = db?.rawQuery("SELECT * FROM ${DbMainClass.TABLE_NAME} WHERE logoLetter=?", arrayOf(logoLetter))

        with(cursor) {
            while(this!!.moveToNext()) {
                val item = object : Item {
                    override val word: String = getString(getColumnIndexOrThrow(DbMainClass.WORD_COLUMN))
                    override val translate: String = getString(getColumnIndexOrThrow(DbMainClass.TRANSLATE_COLUMN))
                    override val hash: String = getString(getColumnIndexOrThrow(DbMainClass.HASH_COLUMN))
                    override val logoLetter: String = getString(getColumnIndexOrThrow(DbMainClass.LOGO_LETTER))
                }

                dataList.add(item);
            }
        }

        cursor?.close()

        return dataList
    }

    fun deleteWordByHash(hash: String) {
        db?.execSQL("DELETE FROM ${DbMainClass.TABLE_NAME} WHERE hash='${hash}'")
    }
}