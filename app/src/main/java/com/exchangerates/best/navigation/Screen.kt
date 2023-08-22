package com.exchangerates.best.navigation

const val AUTH_ROUTE = "AUTH_ROUTE"
const val HOME_ROUTE = "HOME_ROUTE"

const val ROOT_ROUTE = "ROOT_ROUTE"

sealed class Screen(var route: String) {

    object HOME : Screen("home_screen")
    object SPLASH : Screen("splash_screen")

}