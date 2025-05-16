package com.nutritrack.app.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.nutritrack.app.R
import com.nutritrack.app.model.QuestionnaireResponse
import com.nutritrack.app.navigation.Screen
import com.nutritrack.app.utils.SessionManager

@Composable
fun PersonaSelectionScreen(navController: NavController, sessionManager: SessionManager) {
    var selectedPersona by remember { mutableStateOf("") }
    var showPersonaDialog by remember { mutableStateOf(false) }
    var selectedPersonaForDialog by remember { mutableStateOf("") }
    
    // Load existing persona if available
    LaunchedEffect(Unit) {
        sessionManager.getQuestionnaireResponse()?.selectedPersona?.let {
            selectedPersona = it
        }
    }

    val personaDescriptions = mapOf(
        "Health Devotee" to "A person who is deeply committed to maintaining optimal health through careful food choices and regular exercise. They prioritize nutrition and are well-informed about dietary guidelines.",
        "Mindful Eater" to "Someone who pays close attention to their eating habits, focusing on quality and portion control. They make conscious food choices but maintain flexibility.",
        "Wellness Striver" to "An individual actively working towards better health habits. They're learning about nutrition and making gradual improvements to their diet.",
        "Balance Seeker" to "A person who aims to maintain a reasonable balance between healthy eating and enjoyment. They're moderate in their approach to nutrition.",
        "Health Procrastinator" to "Someone who knows they should make healthier choices but often postpones making dietary changes. They're interested in health but struggle with consistency.",
        "Food Carefree" to "An individual who doesn't regularly prioritize nutritional value in their food choices. They eat primarily based on convenience and preference."
    )

    val personaImages = mapOf(
        "Health Devotee" to R.drawable.img,
        "Mindful Eater" to R.drawable.img,
        "Wellness Striver" to R.drawable.img,
        "Balance Seeker" to R.drawable.img,
        "Health Procrastinator" to R.drawable.img,
        "Food Carefree" to R.drawable.img
    )

    if (showPersonaDialog) {
        Dialog(onDismissRequest = { showPersonaDialog = false }) {
            Card(
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                elevation = 8.dp
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = personaImages[selectedPersonaForDialog] ?: R.drawable.img),
                        contentDescription = "Persona Image",
                        modifier = Modifier
                            .size(100.dp)
                            .padding(bottom = 16.dp)
                    )
                    
                    Text(
                        text = selectedPersonaForDialog,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    
                    Text(
                        text = personaDescriptions[selectedPersonaForDialog] ?: "",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                    
                    Button(
                        onClick = { showPersonaDialog = false },
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF6200EA))
                    ) {
                        Text("Dismiss", color = Color.White)
                    }
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "Your Persona",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Text(
            text = "Select the persona that best describes your eating habits",
            fontSize = 16.sp,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Persona Selection
        val personaOptions = listOf("Health Devotee", "Mindful Eater", "Wellness Striver", "Balance Seeker", "Health Procrastinator", "Food Carefree")

        Column {
            personaOptions.chunked(2).forEach { rowItems ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    rowItems.forEach { persona ->
                        Button(
                            onClick = { 
                                selectedPersona = persona
                                selectedPersonaForDialog = persona
                                showPersonaDialog = true
                            },
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = if (selectedPersona == persona) Color(0xFF6200EE) else Color.LightGray
                            ),
                            modifier = Modifier
                                .padding(4.dp)
                                .weight(1f)
                        ) {
                            Text(text = persona, color = Color.White)
                        }
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
        }

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
                    // Save selected persona
                    val currentResponse = sessionManager.getQuestionnaireResponse() ?: QuestionnaireResponse(
                        selectedCategories = emptySet(),
                        selectedPersona = "",
                        biggestMealTime = "",
                        sleepTime = "",
                        wakeTime = ""
                    )
                    //val currentResponse = sessionManager.getQuestionnaireResponse() ?: QuestionnaireResponse()
                    val updatedResponse = currentResponse.copy(selectedPersona = selectedPersona)
                    sessionManager.saveQuestionnaireResponse(updatedResponse)
                    
                    // Navigate to timings selection
                    navController.navigate(Screen.TimingsSelection.route)
                },
                modifier = Modifier.weight(1f).padding(start = 8.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF6200EE)),
                enabled = selectedPersona.isNotEmpty()
            ) {
                Text("Next", color = Color.White)
            }
        }
    }
} 