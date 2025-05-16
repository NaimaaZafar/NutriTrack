package com.nutritrack.app.utils

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.nutritrack.app.model.QuestionnaireResponse
import com.nutritrack.app.model.User

class SessionManager(context: Context) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = sharedPreferences.edit()
    private val gson = Gson()

    companion object {
        private const val PREF_NAME = "NutriTrackSession"
        private const val KEY_USER_ID = "userId"
        private const val KEY_PHONE_NUMBER = "phoneNumber"
        private const val KEY_USER_SEX = "userSex"
        private const val KEY_TOTAL_SCORE = "totalScore"
        private const val KEY_IS_LOGGED_IN = "isLoggedIn"
        private const val KEY_QUESTIONNAIRE_COMPLETED = "questionnaireCompleted"
        private const val KEY_QUESTIONNAIRE_RESPONSE = "questionnaireResponse"
    }

    fun saveUserSession(user: User) {
        editor.putString(KEY_USER_ID, user.id)
        editor.putString(KEY_PHONE_NUMBER, user.phoneNumber)
        editor.putString(KEY_USER_SEX, user.sex)
        editor.putFloat(KEY_TOTAL_SCORE, user.totalScore.toFloat())
        editor.putBoolean(KEY_IS_LOGGED_IN, true)
        editor.apply()
    }

    fun getUserSession(): User? {
        if (!isLoggedIn()) return null
        
        return User(
            id = sharedPreferences.getString(KEY_USER_ID, "") ?: "",
            phoneNumber = sharedPreferences.getString(KEY_PHONE_NUMBER, "") ?: "",
            sex = sharedPreferences.getString(KEY_USER_SEX, "") ?: "",
            totalScore = sharedPreferences.getFloat(KEY_TOTAL_SCORE, 0f).toDouble()
        )
    }

    fun isLoggedIn(): Boolean {
        return sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false)
    }

    fun logout() {
        editor.clear()
        editor.apply()
    }

    fun setQuestionnaireCompleted(completed: Boolean) {
        editor.putBoolean(KEY_QUESTIONNAIRE_COMPLETED, completed)
        editor.apply()
    }

    fun isQuestionnaireCompleted(): Boolean {
        return sharedPreferences.getBoolean(KEY_QUESTIONNAIRE_COMPLETED, false)
    }

    fun saveQuestionnaireResponse(response: QuestionnaireResponse) {
        val json = gson.toJson(response)
        editor.putString(KEY_QUESTIONNAIRE_RESPONSE, json)
        editor.apply()
    }

    fun getQuestionnaireResponse(): QuestionnaireResponse? {
        val json = sharedPreferences.getString(KEY_QUESTIONNAIRE_RESPONSE, null)
        if (json == null) return null
        
        val type = object : TypeToken<QuestionnaireResponse>() {}.type
        return gson.fromJson(json, type)
    }

    fun calculateScore(): Double {
        val response = getQuestionnaireResponse() ?: return 0.0
        
        // Calculate score based on selected categories and persona
        var score = 0.0
        
        // Category scoring (each category is worth 10 points, max 90 points)
        val categoryScore = response.selectedCategories.size * 10.0
        score += categoryScore.coerceAtMost(90.0)
        
        // Persona scoring (20 points)
        when (response.selectedPersona) {
            "Health Devotee" -> score += 20.0
            "Mindful Eater" -> score += 18.0
            "Wellness Striver" -> score += 15.0
            "Balance Seeker" -> score += 12.0
            "Health Procrastinator" -> score += 8.0
            "Food Carefree" -> score += 5.0
        }
        
        // Meal timing scoring (10 points)
        if (response.biggestMealTime.isNotEmpty() && 
            response.sleepTime.isNotEmpty() && 
            response.wakeTime.isNotEmpty()) {
            score += 10.0
        }
        
        return score.coerceIn(0.0, 100.0)
    }
} 