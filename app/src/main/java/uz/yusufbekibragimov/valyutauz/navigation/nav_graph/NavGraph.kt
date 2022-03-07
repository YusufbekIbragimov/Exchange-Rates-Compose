package uz.yusufbekibragimov.valyutauz.navigation.nav_graph

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.lifecycle.viewmodel.compose.viewModel
import uz.yusufbekibragimov.valyutauz.navigation.AUTH_ROUTE
import uz.yusufbekibragimov.valyutauz.navigation.ROOT_ROUTE
import uz.yusufbekibragimov.valyutauz.screens.home_screen.HomeViewModel

/**
 * Created by Ibragimov Yusufbek
 * Date: 16.02.2022
 * Project: ComposeNavigation
 **/

@Composable
fun SetUpNavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = AUTH_ROUTE,
        route = ROOT_ROUTE
    ) {
        homeNavGraph(navController = navController)
        authNavGraph(navController = navController)
    }
}