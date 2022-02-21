package com.example.dictionary.db

import android.provider.BaseColumns

object DbMainClass : BaseColumns {
    const val LOGO_LETTER = "logoLetter"
    const val DB_NAME = "dictionaryDb"
    const val TABLE_NAME = "words"
    const val WORD_COLUMN = "word"
    const val TRANSLATE_COLUMN = "translate"
    const val HASH_COLUMN = "hash"
    const val DB_VERSION = 1
    const val SQL_DELETE_TABLE = "DROP TABLE IF EXISTS ${TABLE_NAME}"
    val SQL_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS ${TABLE_NAME}" +
            "(${BaseColumns._ID} INTEGER PRIMARY KEY," +
            "${WORD_COLUMN} VARCHAR(250) NOT NULL," +
            "${TRANSLATE_COLUMN} VARCHAR(250) NOT NULL," +
            "${HASH_COLUMN} UNIQUE NOT NULL, ${LOGO_LETTER} VARCHAR(2) NOT NULL);";
}