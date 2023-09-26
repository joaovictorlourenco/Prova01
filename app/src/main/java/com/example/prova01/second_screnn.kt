package com.example.prova01

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Toast

class second_screnn : AppCompatActivity() {

    lateinit var backToscreen : Button
    lateinit var SecondList: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second_screnn)

        backToscreen = findViewById(R.id.backToscreen)
        SecondList = findViewById(R.id.secondList)

        var listBussiness = intent.getStringArrayListExtra("listBussiness") as ArrayList<String>

        // O ArrayAdapter
        val arrayAdapter: ArrayAdapter<String> = ArrayAdapter(
            this, android.R.layout.simple_list_item_1, listBussiness
        )

        // Associando o ArrayAdapter à ListView
        SecondList.adapter = arrayAdapter

        listBussiness.forEach(){
            val toast = Toast.makeText(applicationContext, it, Toast.LENGTH_SHORT)
            toast.show()
        }

        backToscreen.setOnClickListener() {
            this.finish()
        }

    }
}