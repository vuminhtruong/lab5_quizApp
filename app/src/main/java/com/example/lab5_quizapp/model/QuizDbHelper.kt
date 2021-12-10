package com.example.lab5_quizapp.model

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import org.json.JSONArray

class QuizDbHelper(
    context: Context?,
) : SQLiteOpenHelper(context, "MyQuizAppDB.db", null, 1) {

    private lateinit var db : SQLiteDatabase
    private var context : Context? = context

    override fun onCreate(db : SQLiteDatabase?) {
        this.db = db!!
        val SQL_CREATE_QUESTION_TABLE : String = "CREATE TABLE " +
                QuizContract.QuestionTable.TABLE_NAME + " ( " +
                BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                QuizContract.QuestionTable.COLUMN_QUESTION + " TEXT," +
                QuizContract.QuestionTable.COLUMN_OPTION1 + " TEXT, " +
                QuizContract.QuestionTable.COLUMN_OPTION2 +" TEXT, " +
                QuizContract.QuestionTable.COLUMN_OPTION3 +" TEXT, " +
                QuizContract.QuestionTable.COLUMN_OPTION4 +" TEXT, " +
                QuizContract.QuestionTable.COLUMN_ANSWER +" INTEGER, " +
                QuizContract.QuestionTable.COLUMN_DIFF +" TEXT " +
                ")"
        db.execSQL(SQL_CREATE_QUESTION_TABLE)
        insertQuestion(FileParse().parseQuestions(context!!.assets.open("Quiz.json")))
    }

    override fun onUpgrade(db : SQLiteDatabase?, p1: Int, p2: Int) {
        this.db = db!!
        db.execSQL("DROP TABLE IF EXISTS "+ QuizContract.QuestionTable.TABLE_NAME)
        onCreate(db)
    }

    private fun insertQuestion(questionsList :JSONArray){
        for (i in 0 until questionsList.length()){
            var jsonObject =questionsList.getJSONObject(i)

            val values = ContentValues().apply {
                put(QuizContract.QuestionTable.COLUMN_QUESTION, jsonObject.getString("question"))
                put(QuizContract.QuestionTable.COLUMN_OPTION1, jsonObject.getString("option1"))
                put(QuizContract.QuestionTable.COLUMN_OPTION2, jsonObject.getString("option2"))
                put(QuizContract.QuestionTable.COLUMN_OPTION3, jsonObject.getString("option3"))
                put(QuizContract.QuestionTable.COLUMN_OPTION4, jsonObject.getString("option4"))
                put(QuizContract.QuestionTable.COLUMN_ANSWER, jsonObject.getInt("answer"))
                put(QuizContract.QuestionTable.COLUMN_DIFF,jsonObject.getString("difficulty"))
            }
            db.insert(QuizContract.QuestionTable.TABLE_NAME,null,values)
        }
    }


    @SuppressLint("Range")
    public fun getQuestion(difficulty:String) : ArrayList<Question>{
        var questionList = ArrayList<Question>()
        db = readableDatabase
        var arg = arrayOf(difficulty)
        var c : Cursor = db.rawQuery("SELECT * FROM "+ QuizContract.QuestionTable.TABLE_NAME
            +" WHERE "+ QuizContract.QuestionTable.COLUMN_DIFF +" = ?",arg)
        if(c.moveToFirst()){
            do{
                val question = c.getString(c.getColumnIndex(QuizContract.QuestionTable.COLUMN_QUESTION))
                val option1 = c.getString(c.getColumnIndex(QuizContract.QuestionTable.COLUMN_OPTION1))
                val option2 = c.getString(c.getColumnIndex(QuizContract.QuestionTable.COLUMN_OPTION2))
                val option3 = c.getString(c.getColumnIndex(QuizContract.QuestionTable.COLUMN_OPTION3))
                val option4 = c.getString(c.getColumnIndex(QuizContract.QuestionTable.COLUMN_OPTION4))
                val ans = c.getInt(c.getColumnIndex(QuizContract.QuestionTable.COLUMN_ANSWER))
                val diff = c.getString(c.getColumnIndex(QuizContract.QuestionTable.COLUMN_DIFF))
                questionList.add(Question(question, option1, option2, option3, option4,ans,diff))
            }while (c.moveToNext())
        }
        c.close()
        return questionList
    }
}