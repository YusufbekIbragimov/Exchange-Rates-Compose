package com.exchangerates.best

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.exchangerates.best.navigation.nav_graph.SetUpNavGraph
import com.exchangerates.best.screens.home_screen.HomeViewModel
import com.exchangerates.best.ui.theme.ExchangeRateTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel by viewModels<HomeViewModel>()

        setContent {
            ExchangeRateTheme() {
                navController = rememberNavController()
                SetUpNavGraph(navController = navController, viewModel)
            }
        }
    }
}