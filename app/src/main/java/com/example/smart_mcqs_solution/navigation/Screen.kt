package com.example.smart_mcqs_solution.navigation

sealed class Screen(val route: String) {
    object Splash : Screen("splash_screen")
    object Dashboard : Screen("dashboard_screen")
    object Quiz : Screen("quiz_screen")
}