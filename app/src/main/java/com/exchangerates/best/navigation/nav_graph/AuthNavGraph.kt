package com.exchangerates.best.navigation.nav_graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.exchangerates.best.navigation.AUTH_ROUTE
import com.exchangerates.best.navigation.Screen
import com.exchangerates.best.screens.SplashScreen

fun NavGraphBuilder.authNavGraph(
    navController: NavHostController
) {
    navigation(
        startDestination = Screen.SPLASH.route,
        route = AUTH_ROUTE
    ) {
        composable(route = Screen.SPLASH.route) {
            SplashScreen(navController = navController)
        }
    }

}