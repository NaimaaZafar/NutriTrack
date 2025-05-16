package com.nutritrack.app.model

data class QuestionnaireResponse(
    val selectedCategories: Set<String>,
    val selectedPersona: String,
    val biggestMealTime: String,
    val sleepTime: String,
    val wakeTime: String
) 