package com.nutritrack.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.nutritrack.app.navigation.NutriTrackNavigation
import com.nutritrack.app.ui.theme.NutriTrackTheme
import com.nutritrack.app.utils.SessionManager

class WelcomeActivity : ComponentActivity() {
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        sessionManager = SessionManager(this)
        
        setContent {
            NutriTrackTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    NutriTrackNavigation(sessionManager)
                }
            }
        }
    }
} 