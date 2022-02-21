package com.example.dictionary

import android.icu.text.MeasureFormat
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.dictionary.db.DbManager
import com.example.dictionary.db.Item
import com.google.android.material.dialog.MaterialAlertDialogBuilder


class Words : AppCompatActivity() {
    private val dbManager = DbManager(this)
    var listWordsDb: ArrayList<Item> = arrayListOf()
    var listWords: ArrayList<String> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_words)

        val logoView = findViewById<TextView>(R.id.logo_letter)
        val logo: String = intent.getStringExtra("letter").toString()

        logoView.setText(logo)

        loadWord(logo.toLowerCase())
    }

    fun loadWord(logoLetter: String) {
        dbManager.openDb()

        val listView = findViewById<ListView>(R.id.list_words)

        listWordsDb = dbManager.loadWords(logoLetter)

        if(listWordsDb.isEmpty()) {
            val logoNotWords = findViewById<TextView>(R.id.logo_not_words)

            logoNotWords.setText("You don't have words now ...")

            dbManager.close()
            return
        }

        for (item in listWordsDb) {
            listWords.add("${item.word} - ${item.translate}")
        }
        val wordsAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, listWords)

        listView.adapter = wordsAdapter

        listView.setOnItemClickListener { adapterList, view, i, l ->
            this.alertDelete(i, view, wordsAdapter)
        }

        dbManager.close()
    }

    override fun onDestroy() {
        super.onDestroy()

        dbManager.close()
    }

    fun alertDelete(index: Int, view: View, adapter: ArrayAdapter<String>) {
        val hash = listWordsDb[index].hash

        MaterialAlertDialogBuilder(this).apply {
            setTitle("Delete word ?")
            setMessage("Are you want delete word ? (${listWords[index]})")
            setPositiveButton("Delete"){ _, _->
                dbManager.openDb()
                dbManager.deleteWordByHash(hash)
                listWords.removeAt(index)

                if(listWords.isEmpty()) {
                    val logoNotWords = findViewById<TextView>(R.id.logo_not_words)
                    logoNotWords.setText("You don't have words now ...")
                }
                adapter.notifyDataSetChanged()
                dbManager.close()
            }
            setNeutralButton("Cancel"){ _, _->}
        }.create().show()
    }
}