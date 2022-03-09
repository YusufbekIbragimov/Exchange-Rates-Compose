package uz.yusufbekibragimov.valyutauz.screens

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.Animatable
import androidx.compose.animation.core.animate
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.delay
import uz.yusufbekibragimov.valyutauz.R
import uz.yusufbekibragimov.valyutauz.navigation.Screen
import uz.yusufbekibragimov.valyutauz.ui.theme.Purple500
import uz.yusufbekibragimov.valyutauz.ui.theme.Purple700

@Composable
@Preview(showBackground = true)
fun LoginScreenPreview() {
    SplashScreen(rememberNavController())
}

@Composable
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
fun LoginScreenPreviewDark() {
    SplashScreen(rememberNavController())
}

/**
 * Created by Ibragimov Yusufbek
 * Date: 16.02.2022
 * Project: ComposeNavigation
 **/

@Composable
fun SplashScreen(
    navController: NavHostController
) {
    var startAnimation by remember { mutableStateOf(false) }
    val alphaAnim = animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(
            durationMillis = 1000
        )
    )
    LaunchedEffect(key1 = true) {
        startAnimation = true
        delay(1000)
        navController.navigate(Screen.HOME.route) {

        }
    }
    Box(
        modifier = Modifier
            .background(color = if (isSystemInDarkTheme()) Color.Black else Purple500)
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            modifier = Modifier
                .clip(CircleShape)
                .size(160.dp)
                .alpha(alphaAnim.value),
            painter = painterResource(id = R.drawable.app_logo),
            contentDescription = "share"
        )
    }
}