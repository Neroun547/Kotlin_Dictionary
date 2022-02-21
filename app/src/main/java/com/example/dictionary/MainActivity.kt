package com.example.dictionary

import android.content.ContentValues.TAG
import android.content.Intent
import android.nfc.Tag
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val listView = findViewById<ListView>(R.id.listLetters)
        val containerDictionary = findViewById<LinearLayout>(R.id.container_dictionary)
        val listLetters =  arrayListOf<String>("A", "B", "C", "D", "E", "F", "G", "H", "I", "G",
            "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z")

        val lettersAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, listLetters)

        listView.setOnItemClickListener{ adapterView, view, i, l ->
            val intent = Intent(this, Words::class.java)
            intent.putExtra("letter", listLetters[i])

            startActivity(intent)
        }

        listView.adapter = lettersAdapter
    }

    fun addWordIntent(view: View) {
        val intent = Intent(this, AddWord::class.java)

        startActivity(intent)
    }
}