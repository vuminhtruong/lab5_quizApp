package com.example.lab5_quizapp.controller

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.lab5_quizapp.R
import kotlinx.android.synthetic.main.activity_result.*

class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val username = intent.getStringExtra("user_name")
        tv_name.text = username

        val totalQuestions = intent.getIntExtra("total_question", 0)
        val correctAnswers = intent.getIntExtra("correct_answer", 0)

        tv_score.text = "Вы ответили на $correctAnswers из $totalQuestions вопросов"

        btn_finish.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

    }
}