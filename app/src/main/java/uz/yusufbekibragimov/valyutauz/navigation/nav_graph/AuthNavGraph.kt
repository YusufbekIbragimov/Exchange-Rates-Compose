package uz.yusufbekibragimov.valyutauz.navigation.nav_graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import uz.yusufbekibragimov.valyutauz.navigation.AUTH_ROUTE
import uz.yusufbekibragimov.valyutauz.navigation.Screen
import uz.yusufbekibragimov.valyutauz.screens.SignUpScreen
import uz.yusufbekibragimov.valyutauz.screens.SplashScreen

/**
 * Created by Ibragimov Yusufbek
 * Date: 16.02.2022
 * Project: ComposeNavigation
 **/

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
        composable(route = Screen.SIGNUP.route) {
            SignUpScreen(navController = navController)
        }
    }

}