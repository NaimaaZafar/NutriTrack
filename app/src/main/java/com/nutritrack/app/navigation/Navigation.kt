package com.nutritrack.app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.nutritrack.app.screens.CategorySelectionScreen
import com.nutritrack.app.screens.HomeScreen
import com.nutritrack.app.screens.InsightsScreen
import com.nutritrack.app.screens.LoginScreen
import com.nutritrack.app.screens.PersonaSelectionScreen
import com.nutritrack.app.screens.QuestionnaireScreen
import com.nutritrack.app.screens.TimingsSelectionScreen
import com.nutritrack.app.screens.WelcomeScreen
import com.nutritrack.app.utils.SessionManager

sealed class Screen(val route: String) {
    object Welcome : Screen("welcome")
    object Login : Screen("login")
    object Questionnaire : Screen("questionnaire")
    object CategorySelection : Screen("category_selection")
    object PersonaSelection : Screen("persona_selection")
    object TimingsSelection : Screen("timings_selection")
    object Home : Screen("home")
    object Insights : Screen("insights")
}

@Composable
fun NutriTrackNavigation(sessionManager: SessionManager) {
    val navController = rememberNavController()
    val context = LocalContext.current

    NavHost(navController = navController, startDestination = Screen.Welcome.route) {
        composable(Screen.Welcome.route) { WelcomeScreen(navController) }
        composable(Screen.Login.route) { LoginScreen(navController, context) }
        composable(Screen.Questionnaire.route) { QuestionnaireScreen(navController, sessionManager) }
        composable(Screen.CategorySelection.route) { CategorySelectionScreen(navController, sessionManager) }
        composable(Screen.PersonaSelection.route) { PersonaSelectionScreen(navController, sessionManager) }
        composable(Screen.TimingsSelection.route) { TimingsSelectionScreen(navController, sessionManager) }
        composable(Screen.Home.route) { HomeScreen(navController, sessionManager) }
        composable(Screen.Insights.route) { InsightsScreen(navController, sessionManager) }
    }
}
