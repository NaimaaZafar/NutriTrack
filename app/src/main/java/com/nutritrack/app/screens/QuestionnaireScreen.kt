package com.nutritrack.app.screens

import android.app.TimePickerDialog
import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
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
import java.util.*

@Composable
fun QuestionnaireScreen(navController: NavController, sessionManager: SessionManager) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.img_1),
            contentDescription = "Questionnaire",
            modifier = Modifier
                .size(200.dp)
                .padding(vertical = 16.dp)
        )
        
        Text(
            text = "Let's Get to Know You",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Text(
            text = "Answer a few questions to help us personalize your nutrition journey",
            fontSize = 16.sp,
            color = Color.Gray,
            modifier = Modifier.padding(horizontal = 32.dp)
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Steps
        StepItem(
            number = "1",
            title = "Food Categories",
            description = "Select the types of food you can eat"
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        StepItem(
            number = "2",
            title = "Your Persona",
            description = "Choose the persona that best describes you"
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        StepItem(
            number = "3",
            title = "Meal Timings",
            description = "Set your meal and sleep schedule"
        )

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = { navController.navigate(Screen.CategorySelection.route) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF6200EE))
        ) {
            Text("Start Questionnaire", color = Color.White)
        }
    }
}

@Composable
private fun StepItem(number: String, title: String, description: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(32.dp)
                .padding(4.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = number,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }
        
        Spacer(modifier = Modifier.width(16.dp))
        
        Column {
            Text(
                text = title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = description,
                fontSize = 14.sp,
                color = Color.Gray
            )
        }
    }
}

@Composable
fun TimePickerField(label: String, value: String, onTimeSelected: (String) -> Unit) {
    val context = LocalContext.current

    OutlinedButton(
        onClick = { showTimePicker(context, onTimeSelected) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Text(text = if (value.isNotEmpty()) value else label, color = Color.Black)
    }
}

fun showTimePicker(context: Context, onTimeSelected: (String) -> Unit) {
    val calendar = Calendar.getInstance()
    val hour = calendar.get(Calendar.HOUR_OF_DAY)
    val minute = calendar.get(Calendar.MINUTE)

    TimePickerDialog(
        context,
        { _, selectedHour, selectedMinute ->
            val formattedTime = String.format("%02d:%02d", selectedHour, selectedMinute)
            onTimeSelected(formattedTime)
        },
        hour,
        minute,
        true // Use 24-hour format
    ).show()
}
