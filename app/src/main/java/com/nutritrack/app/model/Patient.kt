package com.nutritrack.app.model

data class Patient(
    val patientId: String,
    val phoneNumber: String,
    val foodQualityScore: Int,
    val fruitsScore: Int,
    val vegetablesScore: Int,
    val wholeGrainsScore: Int,
    val processedFoodsScore: Int,
    val persona: String
) 