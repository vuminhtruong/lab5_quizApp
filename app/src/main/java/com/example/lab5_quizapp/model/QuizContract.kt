package com.example.lab5_quizapp.model

import android.provider.BaseColumns

class QuizContract {

    class QuestionTable : BaseColumns {
        companion object {
            const val TABLE_NAME : String = "quiz_question"
            const val COLUMN_QUESTION : String = "question"
            const val COLUMN_OPTION1 : String = "option1"
            const val COLUMN_OPTION2 : String = "option2"
            const val COLUMN_OPTION3 : String = "option3"
            const val COLUMN_OPTION4 : String = "option4"
            const val COLUMN_ANSWER : String = "answer"
            const val COLUMN_DIFF : String = "difficult"
        }
    }
}