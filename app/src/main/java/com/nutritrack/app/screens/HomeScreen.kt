package com.nutritrack.app.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.nutritrack.app.R
import com.nutritrack.app.components.BottomNavigationBar
import com.nutritrack.app.navigation.Screen
import com.nutritrack.app.utils.SessionManager

@Composable
fun HomeScreen(navController: NavController, sessionManager: SessionManager) {
    Scaffold(
        bottomBar = { BottomNavigationBar(navController) }
    ) { paddingValues ->
        val user = sessionManager.getUserSession()
        val calculatedScore = sessionManager.calculateScore()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        )
        {
            val greetingText = "Hello!"
            Text(
                text = greetingText,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Greeting and Edit Button in the same row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "You've already filled in your Food Intake Questionnaire, but you can change details here:",
                    fontSize = 14.sp,
                    modifier = Modifier.weight(1f)
                )

                Button(
                    onClick = { navController.navigate(Screen.Questionnaire.route) },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF6200EA)),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.height(36.dp) // Adjust the height to match the UI
                ) {
                    Text(text = "Edit", color = Color.White)
                }
            }


            Spacer(modifier = Modifier.height(16.dp))

            // Circular Food Image
            Box(
                modifier = Modifier
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.img),
                    contentDescription = "Food Image",
                    modifier = Modifier.size(200.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Score Section
            Card(
                shape = RoundedCornerShape(12.dp),
                elevation = 4.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "My Score", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "Your Food Quality Score", fontSize = 16.sp)
                    Text(
                        text = "%.1f/100".format(calculatedScore),
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF4CAF50)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Food Score Explanation
            Text(
                text = "What is the Food Quality Score?",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Your Food Quality Score provides a snapshot of how well your eating patterns align with established food guidelines, helping you identify both strengths and opportunities for improvement in your diet.\n\nThis personalized measurement considers various food groups including vegetables, fruits, whole grains, and proteins to give you practical insights for making healthier food choices.",
                fontSize = 14.sp
            )
        }
    }
}
