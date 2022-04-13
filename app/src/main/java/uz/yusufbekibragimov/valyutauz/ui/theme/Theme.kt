package uz.yusufbekibragimov.valyutauz.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import uz.yusufbekibragimov.valyutauz.screens.home_screen.HomeViewModel

private val DarkColorPalette = darkColors(
    primary = BackSearchNight,
    primaryVariant = Purple500,
    secondary = Purple700,
    secondaryVariant = BackToolNight,
    background = White80,
    surface = GreenChart,
    onBackground = BackSheet,
    onSurface = White100,
    onSecondary = Black80
)

private val LightColorPalette = lightColors(
    primary = BackSearch,
    primaryVariant = Purple500,
    secondary = Purple700,
    secondaryVariant = BackToolLight,
    background = Black80,
    surface = GreenChart,
    onBackground = White100,
    onSurface = Black100,
    onSecondary = White80
)

@Composable
fun ValyutaUzTheme(themeViewModel: HomeViewModel = viewModel(), content: @Composable () -> Unit) {

    val theme by themeViewModel.theme.observeAsState(initial = false)

    val colors = if (theme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    if (theme){
        rememberSystemUiController().setNavigationBarColor(color = BackToolNight)
        rememberSystemUiController().setStatusBarColor(color = BackToolNight)
    } else {
        rememberSystemUiController().setNavigationBarColor(color = Color.White)
        rememberSystemUiController().setStatusBarColor(color = Purple500)
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}