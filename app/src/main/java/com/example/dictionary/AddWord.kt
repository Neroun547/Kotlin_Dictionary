package com.example.dictionary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.example.dictionary.db.DbManager

class AddWord : AppCompatActivity() {
    private val dbManager = DbManager(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_word)
    }

    fun addWord(view: View) {
        val word = findViewById<EditText>(R.id.word)
        val translate = findViewById<EditText>(R.id.translate)

        if(word.text.toString().isEmpty()) {
            Toast.makeText(this, "Write word", Toast.LENGTH_SHORT).show()

            return
        }

        if(translate.text.toString().isEmpty()) {
            Toast.makeText(this, "Write translate", Toast.LENGTH_SHORT).show()

            return
        }

        val hash = this.generateHash()

        dbManager.openDb()
        dbManager.addWord(word.text.toString().toLowerCase(), translate.text.toString().toLowerCase(), word.text.substring(0, 1).toLowerCase(), hash)

        word.setText("")
        translate.setText("")

        Toast.makeText(this, "Word add", Toast.LENGTH_SHORT).show()
    }

    fun generateHash(): String {
        val charPool : List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')

        val randomString = (1..30)
                .map { i -> kotlin.random.Random.nextInt(0, charPool.size) }
                .map(charPool::get)
                .joinToString("");

        return randomString
    }

    override fun onDestroy() {
        super.onDestroy()

        dbManager.close()
    }
}