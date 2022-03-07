package uz.yusufbekibragimov.valyutauz.navigation

/**
 * Created by Ibragimov Yusufbek
 * Date: 16.02.2022
 * Project: ComposeNavigation
 **/

const val AUTH_ROUTE = "AUTH_ROUTE"
const val HOME_ROUTE = "HOME_ROUTE"

const val ROOT_ROUTE = "ROOT_ROUTE"

sealed class Screen(var route: String) {

    object HOME : Screen("home_screen")
    object DETAIL : Screen("detail_screen")
    object SPLASH : Screen("splash_screen")
    object SIGNUP : Screen("signup_screen")

}