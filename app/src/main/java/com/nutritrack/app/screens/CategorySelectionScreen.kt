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
import com.nutritrack.app.model.QuestionnaireResponse
import com.nutritrack.app.navigation.Screen
import com.nutritrack.app.utils.SessionManager

@Composable
fun CategorySelectionScreen(navController: NavController, sessionManager: SessionManager) {
    val selectedCategories = remember { mutableStateListOf<String>() }
    
    // Load existing categories if available
    LaunchedEffect(Unit) {
        sessionManager.getQuestionnaireResponse()?.selectedCategories?.let {
            selectedCategories.addAll(it)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "Food Categories",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Text(
            text = "Select all the food categories you can eat",
            fontSize = 16.sp,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Food Categories
        val foodOptions = listOf("Fruits", "Vegetables", "Grains", "Red Meat", "Seafood", "Poultry", "Fish", "Eggs", "Nuts/Seeds")

        Column {
            foodOptions.chunked(3).forEach { rowItems ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    rowItems.forEach { item ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(end = 8.dp)
                        ) {
                            Checkbox(
                                checked = selectedCategories.contains(item),
                                onCheckedChange = {
                                    if (it) selectedCategories.add(item) else selectedCategories.remove(item)
                                }
                            )
                            Text(text = item)
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
                    // Save selected categories
                    val currentResponse = sessionManager.getQuestionnaireResponse() ?: QuestionnaireResponse(
                        selectedCategories = emptySet(),
                        selectedPersona = "",
                        biggestMealTime = "",
                        sleepTime = "",
                        wakeTime = ""
                    )
                    val updatedResponse = currentResponse.copy(selectedCategories = selectedCategories.toSet())
                    sessionManager.saveQuestionnaireResponse(updatedResponse)
                    
                    // Navigate to persona selection
                    navController.navigate(Screen.PersonaSelection.route)
                },
                modifier = Modifier.weight(1f).padding(start = 8.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF6200EE))
            ) {
                Text("Next", color = Color.White)
            }
        }
    }
} 