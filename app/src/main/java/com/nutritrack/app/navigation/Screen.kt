package com.nutritrack.app.navigation

sealed class SCCcreen(val route: String) {
    object Welcome : Screen("welcome")
    object Login : Screen("login")
    object Questionnaire : Screen("questionnaire")
    object CategorySelection : Screen("category_selection")
    object PersonaSelection : Screen("persona_selection")
    object TimingsSelection : Screen("timings_selection")
    object Home : Screen("home")
    object Insights : Screen("insights")
} 