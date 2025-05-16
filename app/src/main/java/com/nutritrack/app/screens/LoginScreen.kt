package com.nutritrack.app.screens

import android.content.Context
import androidx.compose.foundation.background
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
import java.io.BufferedReader
import java.io.InputStreamReader

@Composable
fun LoginScreen(navController: NavController, context: Context) {
    var userId by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var error by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Log in",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(24.dp))

        // ID Selection Dropdown
        OutlinedTextField(
            value = userId,
            onValueChange = { userId = it },
            label = { Text("My ID (Provided by your Clinician)") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Phone Number Input
        OutlinedTextField(
            value = phoneNumber,
            onValueChange = { phoneNumber = it },
            label = { Text("Phone number") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Error Message
        if (error.isNotEmpty()) {
            Text(text = error, color = MaterialTheme.colors.error)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Disclaimer Text
        Text(
            text = "This app is only for pre-registered users. Please have your ID and phone number handy before continuing.",
            fontSize = 12.sp,
            color = Color.Gray,
            modifier = Modifier.padding(horizontal = 8.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Continue Button
        Button(
            onClick = {
                if (userId.isEmpty() || phoneNumber.isEmpty()) {
                    error = "Please enter both ID and phone number."
                } else if (validateUser(context, userId, phoneNumber)) {
                    navController.navigate("questionnaire")
                } else {
                    error = "Invalid credentials. Please try again."
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF6200EE))
        ) {
            Text(text = "Continue", color = Color.White, fontSize = 16.sp)
        }
    }
}

fun validateUser(context: Context, inputId: String, inputPhone: String): Boolean {
    try {
        val inputStream = context.assets.open("patient_data.csv")
        val reader = BufferedReader(InputStreamReader(inputStream))
        reader.useLines { lines ->
            lines.forEach { line ->
                val tokens = line.split(",")
                if (tokens.size >= 2) {
                    val storedId = tokens[0].trim()
                    val storedPhone = tokens[1].trim()
                    if (storedId == inputId && storedPhone == inputPhone) {
                        return true
                    }
                }
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return false
}
