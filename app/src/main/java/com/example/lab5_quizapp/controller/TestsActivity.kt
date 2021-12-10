package com.example.lab5_quizapp.controller

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lab5_quizapp.R
import com.example.lab5_quizapp.model.Question
import com.example.lab5_quizapp.view.Adapter

class TestsActivity : AppCompatActivity(){
    private var username: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tests)

        username = intent.getStringExtra("user_name")
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_tests)
        recyclerView.layoutManager = GridLayoutManager(this,2)
        val data = ArrayList<String>()
        for(i in 1..12){
            if(i%3 == 1)
                data.add("Test $i\n"+ Question.DIFFICULT_EASY)
            else if(i%3 == 2)
                data.add("Test $i\n"+ Question.DIFFICULT_MEDIUM)
            else
                data.add("Test $i\n"+ Question.DIFFICULT_HARD)
        }
        val adapter = Adapter(data)
        recyclerView.adapter = adapter

        adapter.setOnClick(object : Adapter.onItemClickListener{
            override fun onItemClick(position: Int) {
                val intent = Intent(this@TestsActivity, QuestionActivity::class.java)
                intent.putExtra("test_id",position+1)
                intent.putExtra("user_name",username)
                startActivity(intent)
            }
        })
    }
}