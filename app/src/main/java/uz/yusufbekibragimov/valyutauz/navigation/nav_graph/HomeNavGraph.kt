package uz.yusufbekibragimov.valyutauz.navigation.nav_graph

import androidx.compose.animation.*
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import uz.yusufbekibragimov.valyutauz.navigation.HOME_ROUTE
import uz.yusufbekibragimov.valyutauz.navigation.Screen
import uz.yusufbekibragimov.valyutauz.screens.DetailScreen
import uz.yusufbekibragimov.valyutauz.screens.HomeScreen
import uz.yusufbekibragimov.valyutauz.screens.analysis_Screen.AnalysisScreen
import uz.yusufbekibragimov.valyutauz.screens.home_screen.HomeViewModel
import uz.yusufbekibragimov.valyutauz.screens.news_Screen.NewsScreen

/**
 * Created by Ibragimov Yusufbek
 * Date: 16.02.2022
 * Project: ComposeNavigation
 **/

fun NavGraphBuilder.homeNavGraph(
    navController: NavHostController,
    viewModel: HomeViewModel
) {
    navigation(
        startDestination = Screen.HOME.route,
        route = HOME_ROUTE
    ){
        composable(route = Screen.HOME.route) {
            EnterAnimation {
                HomeScreen(navController = navController, viewModel = viewModel)
                viewModel.getList("")
            }
        }
        composable(route = Screen.DETAIL.route) {
            EnterAnimation {
                DetailScreen(navController = navController)
            }
        }
        composable(route = Screen.ANALYSIS.route) {
            EnterAnimation {
                AnalysisScreen(navController = navController)
            }
        }
        composable(route = Screen.NEWS.route) {
            EnterAnimation {
                NewsScreen(navController = navController)
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