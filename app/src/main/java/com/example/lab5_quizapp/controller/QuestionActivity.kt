package com.example.lab5_quizapp.controller

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.lab5_quizapp.R
import com.example.lab5_quizapp.model.QuizDbHelper
import com.example.lab5_quizapp.model.Question
import kotlin.collections.ArrayList
import kotlinx.android.synthetic.main.activity_question.*
import java.util.*

class QuestionActivity: AppCompatActivity(),View.OnClickListener {
    private var score: Int = 0
    private var answered : Boolean = false
    private var currentOption:Int = 0
    private var questionCounter:Int = 1
    private var questionCountTotal = 0
    private var testId: Int = 0
    private lateinit var currentQuestion: Question
    private var username: String? = null
    private var questionList = ArrayList<Question>()
    private val TIME_COUNT_DOWN:Long = 30000
    private var timeLeftInMillis:Long = 0
    private lateinit var countDownTimer:CountDownTimer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question)

        testId = intent.getIntExtra("test_id",0)
        username = intent.getStringExtra("user_name")

        val dbHelper = QuizDbHelper(this)
        questionList = if(testId % 3 == 1){
            dbHelper.getQuestion(Question.DIFFICULT_EASY)
        }else if(testId % 3 == 2)
            dbHelper.getQuestion(Question.DIFFICULT_MEDIUM)
        else
            dbHelper.getQuestion(Question.DIFFICULT_HARD)
        currentQuestion = questionList[questionCounter-1]

        questionCountTotal=questionList.size
        questionList.shuffle()

        setQuestion()
        variant_1.setOnClickListener(this)
        variant_2.setOnClickListener(this)
        variant_3.setOnClickListener(this)
        variant_4.setOnClickListener(this)
        btn_submit.setOnClickListener(this)
    }

    override fun onClick(v: View?){
        when(v?.id){
            R.id.variant_1 -> {
                selectOptionView(variant_1,1)
            }
            R.id.variant_2 -> {
                selectOptionView(variant_2,2)
            }
            R.id.variant_3 -> {
                selectOptionView(variant_3,3)
            }
            R.id.variant_4 -> {
                selectOptionView(variant_4,4)
            }
            R.id.btn_submit ->{
                if(currentOption == 0){
                    questionCounter++

                    when{
                        questionCounter <= questionCountTotal -> {
                            setQuestion()
                        }else-> {
                        val intent = Intent(this, ResultActivity::class.java)
                        intent.putExtra("user_name",username)
                        intent.putExtra("correct_answer",score / 10)
                        intent.putExtra("total_question",questionCountTotal)
                        startActivity(intent)
                        finish()
                        }
                    }
                }else{
                    currentQuestion =questionList[questionCounter-1]
                    answered = true
                    if(currentQuestion.answer != currentOption){
                        answerView(currentOption, R.drawable.wrong_option)
                    }else{
                        score+=10
                        text_view_score.text = "Score:$score"
                    }
                    if(answered){
                        btn_submit.text="ОТПРАВИТЬ"
                        answered = false
                    }
                    answerView(currentQuestion.answer, R.drawable.correct_option)
                    currentOption = 0
                    countDownTimer.cancel()
                }
            }
        }
    }

    private fun answerView(currentOption: Int, drawableView: Int) {
        when(currentOption){
            1 -> {
                variant_1.background = ContextCompat.getDrawable(
                    this,drawableView
                )
            }
            2 -> {
                variant_2.background = ContextCompat.getDrawable(
                    this,drawableView
                )
            }
            3 -> {
                variant_3.background = ContextCompat.getDrawable(
                    this,drawableView
                )
            }
            4 -> {
                variant_4.background = ContextCompat.getDrawable(
                    this,drawableView
                )
            }
        }
    }

    private fun setQuestion() {
        val ques = questionList[questionCounter-1]

        defaultOptionsView()
        text_view_question.text = ques.question
        text_view_difficult.text = "Difficult: ${ques.difficulty}"
        variant_1.text = ques.option1
        variant_2.text = ques.option2
        variant_3.text = ques.option3
        variant_4.text = ques.option4
        text_view_questionCount.text = "$questionCounter/$questionCountTotal"
        btn_submit.text = "ГОТОВО"
        progressBar.max = questionCountTotal
        progressBar.progress = questionCounter
        timeLeftInMillis = TIME_COUNT_DOWN

        startCountDown()
    }

    private fun startCountDown() {
        countDownTimer = object : CountDownTimer(timeLeftInMillis,1000){
            override fun onTick(millisUntilFinished: Long) {
                timeLeftInMillis = millisUntilFinished
                updateCountDown()
            }

            override fun onFinish(){
                timeLeftInMillis = 0
                updateCountDown()
                answerView(currentQuestion.answer, R.drawable.correct_option)
                btn_submit.text = "ОТПРАВИТЬ"
            }
        }.start()

    }

    private fun updateCountDown(){
        val minutes = timeLeftInMillis /1000/60
        val seconds = (timeLeftInMillis/1000)%60

        val timeFormat = String.format(Locale.getDefault(),"%02d:%02d",minutes,seconds)
        text_view_countdown.text = timeFormat
        if(timeLeftInMillis < 10000)
            text_view_countdown.setTextColor(Color.RED)
        else
            text_view_countdown.setTextColor(Color.BLACK)
    }

    private fun selectOptionView(tv: TextView?, selectedOptionNumber: Int) {
        defaultOptionsView()
        currentOption = selectedOptionNumber

        tv!!.setTextColor(Color.parseColor("#363A43"))
        tv.setTypeface(tv.typeface,Typeface.BOLD)
        tv.background = ContextCompat.getDrawable(
            this,
            R.drawable.selected_option
        )
    }

    private fun defaultOptionsView() {
        val options = ArrayList<TextView>()
        options.add(0,variant_1)
        options.add(1,variant_2)
        options.add(2,variant_3)
        options.add(3,variant_4)

        for (option in options) {
            option.setTextColor(Color.parseColor("#7A8089"))
            option.typeface = Typeface.DEFAULT
            option.background = ContextCompat.getDrawable(
                this,
                R.drawable.border_tv
            )
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        if(countDownTimer!=null)
            countDownTimer.cancel()
    }

}