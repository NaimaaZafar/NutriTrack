package com.nutritrack.app.util

import android.content.Context
import com.nutritrack.app.model.User

class CsvParser {
    fun readUserData(context: Context): List<User> {
        val inputStream = context.assets.open("nutritrack_data.csv")
        val reader = inputStream.bufferedReader()
        val header = reader.readLine()
        
        return reader.lineSequence()
            .filter { it.isNotBlank() }
            .map { line ->
                val tokens = line.split(",")
                User(
                    id = tokens[1],
                    phoneNumber = tokens[0],
                    sex = tokens[2],
                    totalScore = if (tokens[2] == "Male") 
                        tokens[3].toDouble() else tokens[4].toDouble()
                )
            }.toList()
    }
} 