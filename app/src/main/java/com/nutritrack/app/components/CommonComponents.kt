package com.nutritrack.app.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun TimePickerField(
    label: String,
    value: String,
    onTimeSelected: (String) -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }
    val timeFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())
    
    OutlinedButton(
        onClick = { showDialog = true },
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text = if (value.isNotEmpty()) value else label)
    }
    
    if (showDialog) {
        val calendar = Calendar.getInstance()
        var selectedHour by remember { mutableStateOf(calendar.get(Calendar.HOUR_OF_DAY)) }
        var selectedMinute by remember { mutableStateOf(calendar.get(Calendar.MINUTE)) }
        
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Select Time") },
            text = {
                Column {
                    // Time picker implementation
                    // Note: This is a simplified version. You might want to use a more sophisticated time picker
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        // Hours
                        Column {
                            Text("Hour")
                            Slider(
                                value = selectedHour.toFloat(),
                                onValueChange = { selectedHour = it.toInt() },
                                valueRange = 0f..23f,
                                steps = 23
                            )
                            Text("${selectedHour}:00")
                        }
                        
                        // Minutes
                        Column {
                            Text("Minute")
                            Slider(
                                value = selectedMinute.toFloat(),
                                onValueChange = { selectedMinute = it.toInt() },
                                valueRange = 0f..59f,
                                steps = 59
                            )
                            Text("${selectedMinute}")
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        calendar.set(Calendar.HOUR_OF_DAY, selectedHour)
                        calendar.set(Calendar.MINUTE, selectedMinute)
                        onTimeSelected(timeFormat.format(calendar.time))
                        showDialog = false
                    }
                ) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
} 