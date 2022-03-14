package uz.yusufbekibragimov.valyutauz.navigation.nav_graph

import androidx.compose.animation.*
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import uz.yusufbekibragimov.valyutauz.navigation.HOME_ROUTE
import uz.yusufbekibragimov.valyutauz.navigation.Screen
import uz.yusufbekibragimov.valyutauz.screens.DetailScreen
import uz.yusufbekibragimov.valyutauz.screens.HomeScreen
import uz.yusufbekibragimov.valyutauz.screens.home_screen.HomeViewModel

/**
 * Created by Ibragimov Yusufbek
 * Date: 16.02.2022
 * Project: ComposeNavigation
 **/

fun NavGraphBuilder.homeNavGraph(
    navController: NavHostController
) {
    navigation(
        startDestination = Screen.HOME.route,
        route = HOME_ROUTE
    ){
        composable(route = Screen.HOME.route) {
            val model = hiltViewModel<HomeViewModel>()
            EnterAnimation {
                HomeScreen(navController = navController, viewModel = model)
                model.getList("")
            }
        }
        composable(route = Screen.DETAIL.route) {
            EnterAnimation {
                DetailScreen(navController = navController)
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