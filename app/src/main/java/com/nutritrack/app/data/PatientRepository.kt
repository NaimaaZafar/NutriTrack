package com.nutritrack.app.data

import android.content.Context
import com.nutritrack.app.model.Patient
import java.io.BufferedReader
import java.io.InputStreamReader

class PatientRepository(private val context: Context) {
    
    private val patients = mutableListOf<Patient>()
    
    init {
        loadPatientsFromCsv()
    }
    
    private fun loadPatientsFromCsv() {
        try {
            val inputStream = context.assets.open("patient_data.csv")
            val reader = BufferedReader(InputStreamReader(inputStream))
            
            // Skip header
            reader.readLine()
            
            var line: String?
            while (reader.readLine().also { line = it } != null) {
                line?.let {
                    val values = it.split(",")
                    if (values.size >= 8) {
                        val patient = Patient(
                            patientId = values[0],
                            phoneNumber = values[1],
                            foodQualityScore = values[2].toInt(),
                            fruitsScore = values[3].toInt(),
                            vegetablesScore = values[4].toInt(),
                            wholeGrainsScore = values[5].toInt(),
                            processedFoodsScore = values[6].toInt(),
                            persona = values[7]
                        )
                        patients.add(patient)
                    }
                }
            }
            reader.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    
    fun authenticatePatient(patientId: String, phoneNumber: String): Patient? {
        return patients.find { it.patientId == patientId && it.phoneNumber == phoneNumber }
    }
    
    fun getPatientById(patientId: String): Patient? {
        return patients.find { it.patientId == patientId }
    }
} 