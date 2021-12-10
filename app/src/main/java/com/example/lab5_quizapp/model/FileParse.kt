package com.example.lab5_quizapp.model

import org.json.JSONArray
import java.io.IOException
import java.io.InputStream

class FileParse {
    public fun parseQuestions(inputStream: InputStream) : JSONArray {
        var json:String? = null
        try {
            json = inputStream.bufferedReader().use { it.readText() }
            return JSONArray(json)
        } catch (exception: IOException) {
            exception.printStackTrace()
        }
        return JSONArray()
    }
}