package com.nutritrack.app.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.nutritrack.app.components.BottomNavigationBar
import com.nutritrack.app.model.QuestionnaireResponse
import com.nutritrack.app.navigation.Screen
import com.nutritrack.app.utils.SessionManager

@Composable
fun InsightsScreen(navController: NavController, sessionManager: SessionManager) {
    Scaffold(
        bottomBar = { BottomNavigationBar(navController) }
    ) { paddingValues ->
        val response = sessionManager.getQuestionnaireResponse()
        val calculatedScore = sessionManager.calculateScore()
        val scrollState = rememberScrollState()

        // Calculate individual scores based on questionnaire responses
        val foodScores = calculateFoodScores(response)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Insights: Food Score",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(5.dp))

            // Display food category scores
            foodScores.forEach { (category, score) ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = category, fontSize = 14.sp, modifier = Modifier.weight(2f))
                    Spacer(modifier = Modifier.weight(0.5f))
                    Slider(
                        value = score.toFloat(),
                        onValueChange = {},
                        valueRange = 0f..10f,
                        colors = SliderDefaults.colors(
                            thumbColor = Color(0xFF6200EA),
                            activeTrackColor = Color(0xFF6200EA)
                        ),
                        modifier = Modifier.weight(5f)
                    )
                    Text(text = "$score/10", fontSize = 14.sp, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
                }
                Spacer(modifier = Modifier.height(2.dp))
            }

            Text(
                text = "Total Food Quality Score",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Slider(
                    value = calculatedScore.toFloat(),
                    onValueChange = {},
                    valueRange = 0f..100f,
                    colors = SliderDefaults.colors(
                        thumbColor = Color(0xFF6200EA),
                        activeTrackColor = Color(0xFF6200EA)
                    ),
                    modifier = Modifier.weight(2f)
                )
                Text(
                    text = "${calculatedScore.toInt()}/100",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { /* Share action */ },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF6200EA)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "📤 Share with someone", color = Color.White)
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = { /* Improve diet action */ },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF6200EA)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "🥗 Improve my diet!", color = Color.White)
            }
        }
    }
}

private fun calculateFoodScores(response: QuestionnaireResponse?): Map<String, Int> {
    if (response == null) return emptyMap()

    val scores = mutableMapOf<String, Int>()
    
    // Calculate scores based on selected categories
    val selectedCategories = response.selectedCategories
    
    // Vegetables and Fruits
    scores["Vegetables"] = if (selectedCategories.contains("Vegetables")) 10 else 0
    scores["Fruits"] = if (selectedCategories.contains("Fruits")) 10 else 0
    
    // Grains
    scores["Grains & Cereals"] = if (selectedCategories.contains("Grains")) 10 else 0
    
    // Proteins
    val hasProteins = selectedCategories.any { it in listOf("Red Meat", "Seafood", "Poultry", "Fish", "Eggs") }
    scores["Meat & Alternatives"] = if (hasProteins) 10 else 0
    
    // Dairy (if applicable)
    scores["Dairy"] = if (selectedCategories.contains("Dairy")) 10 else 0
    
    // Water (based on persona)
    scores["Water"] = when (response.selectedPersona) {
        "Health Devotee" -> 10
        "Mindful Eater" -> 8
        "Wellness Striver" -> 6
        "Balance Seeker" -> 5
        "Health Procrastinator" -> 3
        "Food Carefree" -> 2
        else -> 0
    }
    
    // Unsaturated Fats (based on selected categories)
    scores["Unsaturated Fats"] = if (selectedCategories.contains("Nuts/Seeds")) 10 else 0
    
    // Sodium (based on persona)
    scores["Sodium"] = when (response.selectedPersona) {
        "Health Devotee" -> 2
        "Mindful Eater" -> 4
        "Wellness Striver" -> 6
        "Balance Seeker" -> 7
        "Health Procrastinator" -> 8
        "Food Carefree" -> 10
        else -> 5
    }
    
    // Sugar (based on persona)
    scores["Sugar"] = when (response.selectedPersona) {
        "Health Devotee" -> 2
        "Mindful Eater" -> 4
        "Wellness Striver" -> 6
        "Balance Seeker" -> 7
        "Health Procrastinator" -> 8
        "Food Carefree" -> 10
        else -> 5
    }
    
    // Alcohol (based on persona)
    scores["Alcohol"] = when (response.selectedPersona) {
        "Health Devotee" -> 2
        "Mindful Eater" -> 4
        "Wellness Striver" -> 6
        "Balance Seeker" -> 7
        "Health Procrastinator" -> 8
        "Food Carefree" -> 10
        else -> 5
    }
    
    // Discretionary Foods (based on persona)
    scores["Discretionary Foods"] = when (response.selectedPersona) {
        "Health Devotee" -> 2
        "Mindful Eater" -> 4
        "Wellness Striver" -> 6
        "Balance Seeker" -> 7
        "Health Procrastinator" -> 8
        "Food Carefree" -> 10
        else -> 5
    }
    
    return scores
}
