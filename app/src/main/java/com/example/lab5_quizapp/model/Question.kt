package com.example.lab5_quizapp.model

data class Question(
    var question: String = "",
    var option1: String = "",
    var option2: String = "",
    var option3: String = "",
    var option4: String = "",
    var answer: Int = -1,
    var difficulty: String = ""
){
    companion object{
        val DIFFICULT_EASY = "Easy"
        val DIFFICULT_MEDIUM = "Medium"
        val DIFFICULT_HARD = "Hard"
    }
}