package com.exchangerates.best.navigation.nav_graph

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.exchangerates.best.navigation.HOME_ROUTE
import com.exchangerates.best.navigation.Screen
import com.exchangerates.best.screens.HomeScreen
import com.exchangerates.best.screens.home_screen.HomeViewModel

fun NavGraphBuilder.homeNavGraph(
    navController: NavHostController,
    viewModel: HomeViewModel
) {
    navigation(
        startDestination = Screen.HOME.route,
        route = HOME_ROUTE
    ) {
        composable(route = Screen.HOME.route) {
            EnterAnimation {
                HomeScreen(navController = navController, viewModel = viewModel)
                viewModel.getList("2023-04-01")
            }
        }
    }

}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun EnterAnimation(content: @Composable () -> Unit) {
    AnimatedVisibility(
        visible = true,
        enter = fadeIn(initialAlpha = 0.2f) + slideInHorizontally(),
        exit = fadeOut(targetAlpha = 0.2f) + slideOutHorizontally(),
        content = content,
        initiallyVisible = false
    )
}