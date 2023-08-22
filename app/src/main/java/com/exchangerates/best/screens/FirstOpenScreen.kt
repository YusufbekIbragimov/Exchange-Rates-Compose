package com.exchangerates.best.screens

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.delay
import com.exchangerates.best.R
import com.exchangerates.best.navigation.Screen

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
        targetValue = if (startAnimation) 1.8f else 0f,
        animationSpec = spring(
            stiffness = 100f,
            dampingRatio = 0.3f
        )
    )
    LaunchedEffect(key1 = true) {
        delay(500)
        startAnimation = true
        delay(1500)
        navController.navigate(Screen.HOME.route) {

        }
    }
    Box(
        modifier = Modifier
            .background(color = MaterialTheme.colors.secondaryVariant)
            .fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .graphicsLayer {
                    scaleX = alphaAnim.value
                    scaleY = alphaAnim.value
                }
                .background(color = MaterialTheme.colors.secondaryVariant)
                .size(200.dp)
        ) {
            Image(
                modifier = Modifier
                    .size(130.dp),
                painter = painterResource(id = R.drawable.app_logo),
                contentDescription = "share"
            )
            Text(
                text = stringResource(id = R.string.app_name),
                textAlign = TextAlign.Center,
                fontFamily = FontFamily(Font(R.font.my_regular)),
                modifier = Modifier.padding(top = 16.dp),
                color = Color.White
            )
        }

    }
}