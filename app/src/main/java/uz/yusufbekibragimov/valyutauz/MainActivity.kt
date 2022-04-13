package uz.yusufbekibragimov.valyutauz

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import uz.yusufbekibragimov.valyutauz.navigation.nav_graph.SetUpNavGraph
import uz.yusufbekibragimov.valyutauz.screens.home_screen.HomeViewModel
import uz.yusufbekibragimov.valyutauz.ui.theme.*

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel by viewModels<HomeViewModel>()

        viewModel.theme.observe(this){
            Log.d("TAGTAG", "onCreate: $it")
        }

        setContent {
            ValyutaUzTheme() {
                navController = rememberNavController()
                SetUpNavGraph(navController = navController,viewModel)
            }
        }
    }
}