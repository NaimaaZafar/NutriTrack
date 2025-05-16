package com.nutritrack.app.components

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.nutritrack.app.navigation.Screen

@Composable
fun BottomNavigationBar(navController: NavController) {
    BottomNavigation(
        backgroundColor = Color.White,
        elevation = 8.dp
    ) {
        val navBackStackEntry = navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry.value?.destination?.route

        BottomNavigationItem(
            icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
            label = { Text("Home") },
            selected = currentRoute == Screen.Home.route,
            onClick = {
                if (currentRoute != Screen.Home.route) {
                    navController.navigate(Screen.Home.route)
                }
            },
            selectedContentColor = Color(0xFF6200EA),
            unselectedContentColor = Color.Gray
        )

        BottomNavigationItem(
            icon = { Icon(Icons.Default.Info, contentDescription = "Insights") },
            label = { Text("Insights") },
            selected = currentRoute == Screen.Insights.route,
            onClick = {
                if (currentRoute != Screen.Insights.route) {
                    navController.navigate(Screen.Insights.route)
                }
            },
            selectedContentColor = Color(0xFF6200EA),
            unselectedContentColor = Color.Gray
        )
    }
} 