package com.nutritrack.app.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.nutritrack.app.components.TimePickerField
import com.nutritrack.app.model.QuestionnaireResponse
import com.nutritrack.app.navigation.Screen
import com.nutritrack.app.utils.SessionManager

@Composable
fun TimingsSelectionScreen(navController: NavController, sessionManager: SessionManager) {
    var biggestMealTime by remember { mutableStateOf("") }
    var sleepTime by remember { mutableStateOf("") }
    var wakeTime by remember { mutableStateOf("") }
    
    // Load existing timings if available
    LaunchedEffect(Unit) {
        sessionManager.getQuestionnaireResponse()?.let { response ->
            biggestMealTime = response.biggestMealTime
            sleepTime = response.sleepTime
            wakeTime = response.wakeTime
        }
    }
    
    // Get persona description
    val personaDescription = remember {
        val response = sessionManager.getQuestionnaireResponse()
        when (response?.selectedPersona) {
            "Fitness Enthusiast" -> "You're focused on maintaining a healthy lifestyle with regular exercise and balanced nutrition."
            "Busy Professional" -> "You have a hectic schedule and need quick, nutritious meal solutions."
            "Health Conscious" -> "You prioritize whole foods and mindful eating habits."
            else -> ""
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "Meal & Sleep Timings",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Text(
            text = "Set your meal and sleep schedule",
            fontSize = 16.sp,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Persona Recap Card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            elevation = 4.dp,
            shape = RoundedCornerShape(8.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Your Persona",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = personaDescription)
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedButton(
                    onClick = { navController.navigate(Screen.PersonaSelection.route) },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Change Persona")
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Time Selection Fields
        TimePickerField(
            label = "Select Biggest Meal Time",
            value = biggestMealTime,
            onTimeSelected = { biggestMealTime = it }
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        TimePickerField(
            label = "Select Sleep Time",
            value = sleepTime,
            onTimeSelected = { sleepTime = it }
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        TimePickerField(
            label = "Select Wake-up Time",
            value = wakeTime,
            onTimeSelected = { wakeTime = it }
        )

        Spacer(modifier = Modifier.weight(1f))

        // Navigation Buttons
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            OutlinedButton(
                onClick = { navController.navigateUp() },
                modifier = Modifier.weight(1f).padding(end = 8.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Back")
            }
            
            Button(
                onClick = {
                    // Save timings
                    val currentResponse = sessionManager.getQuestionnaireResponse() ?: QuestionnaireResponse(
                        selectedCategories = emptySet(),
                        selectedPersona = "",
                        biggestMealTime = "",
                        sleepTime = "",
                        wakeTime = ""
                    )
                    val updatedResponse = currentResponse.copy(
                        biggestMealTime = biggestMealTime,
                        sleepTime = sleepTime,
                        wakeTime = wakeTime
                    )
                    sessionManager.saveQuestionnaireResponse(updatedResponse)
                    
                    // Mark questionnaire as completed and navigate to home
                    sessionManager.setQuestionnaireCompleted(true)
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Welcome.route) { inclusive = true }
                    }
                },
                modifier = Modifier.weight(1f).padding(start = 8.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF6200EE))
            ) {
                Text("Complete", color = Color.White)
            }
        }
    }
} 