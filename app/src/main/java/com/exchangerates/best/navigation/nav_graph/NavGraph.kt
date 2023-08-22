package com.exchangerates.best.navigation.nav_graph

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.exchangerates.best.navigation.AUTH_ROUTE
import com.exchangerates.best.navigation.ROOT_ROUTE
import com.exchangerates.best.screens.home_screen.HomeViewModel

@Composable
fun SetUpNavGraph(
    navController: NavHostController,
    viewModel: HomeViewModel
) {
    NavHost(
        navController = navController,
        startDestination = AUTH_ROUTE,
        route = ROOT_ROUTE
    ) {
        homeNavGraph(navController = navController,viewModel)
        authNavGraph(navController = navController)
    }
}