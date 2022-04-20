package uz.yusufbekibragimov.valyutauz.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.StartOffset
import androidx.compose.animation.core.repeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Created by Ibragimov Yusufbek
 * Date: 14.04.2022
 * Project: ValyutaUz
 **/

@Composable
fun ChangeableBack(list: ArrayList<Color>) {

    var state1 by remember { mutableStateOf(1) }
    var state2 by remember { mutableStateOf(2) }
    var state3 by remember { mutableStateOf(3) }
    var state4 by remember { mutableStateOf(4) }
    var state5 by remember { mutableStateOf(5) }
    var state6 by remember { mutableStateOf(6) }

    val ringColor1 by animateColorAsState(
        targetValue = when (state1) {
            1 -> list[1]
            2 -> list[2]
            3 -> list[3]
            4 -> list[4]
            5 -> list[5]
            6 -> list[6]
            else -> list[0]
        },
        animationSpec = tween(
            durationMillis = 3000,
        )
    )

    val ringColor2 by animateColorAsState(
        targetValue = when (state2) {
            1 -> list[1]
            2 -> list[2]
            3 -> list[3]
            4 -> list[4]
            5 -> list[5]
            6 -> list[6]
            else -> list[0]
        },
        animationSpec = tween(
            durationMillis = 3000,
        )
    )

    val ringColor3 by animateColorAsState(
        targetValue = when (state3) {
            1 -> list[1]
            2 -> list[2]
            3 -> list[3]
            4 -> list[4]
            5 -> list[5]
            6 -> list[6]
            else -> list[0]
        },
        animationSpec = tween(
            durationMillis = 3000,
        )
    )

    val ringColor4 by animateColorAsState(
        targetValue = when (state4) {
            1 -> list[1]
            2 -> list[2]
            3 -> list[3]
            4 -> list[4]
            5 -> list[5]
            6 -> list[6]
            else -> list[0]
        },
        animationSpec = tween(
            durationMillis = 3000,
        )
    )

    val ringColor5 by animateColorAsState(
        targetValue = when (state5) {
            1 -> list[1]
            2 -> list[2]
            3 -> list[3]
            4 -> list[4]
            5 -> list[5]
            6 -> list[6]
            else -> list[0]
        },
        animationSpec = tween(
            durationMillis = 3000,
        )
    )

    val ringColor6 by animateColorAsState(
        targetValue = when (state6) {
            1 -> list[1]
            2 -> list[2]
            3 -> list[3]
            4 -> list[4]
            5 -> list[5]
            6 -> list[6]
            else -> list[0]
        },
        animationSpec = tween(
            durationMillis = 3000,
        )
    )

    Column(modifier = Modifier
        .fillMaxSize()
        .blur(125.dp)) {
        Row(
            modifier = Modifier.weight(1f)
        ) {
            Box(
                modifier = Modifier
                    .background(ringColor1)
                    .weight(1f)
                    .fillMaxSize()
            )
            Box(
                modifier = Modifier
                    .background(ringColor2)
                    .weight(1f)
                    .fillMaxSize()
            )
        }
        Row(
            modifier = Modifier.weight(1f)
        ) {
            Box(
                modifier = Modifier
                    .background(ringColor3)
                    .weight(1f)
                    .fillMaxSize()
            )
            Box(
                modifier = Modifier
                    .background(ringColor4)
                    .weight(1f)
                    .fillMaxSize()
            )
        }
        Row(
            modifier = Modifier.weight(1f)
        ) {
            Box(
                modifier = Modifier
                    .background(ringColor5)
                    .weight(1f)
                    .fillMaxSize()
            )
            Box(
                modifier = Modifier
                    .background(ringColor6)
                    .weight(1f)
                    .fillMaxSize()
            )
        }
    }
    val c = LocalContext.current
    LaunchedEffect(true) {
        /*for (i in 1..100){

            delay(500)
        }*/
        repeat(100) {
            state1 = it%3+1
            state2 = it%3+2
            state3 = it%3+3
            state4 = it%3+1
            state5 = it%3+2
            state6 = it%3+3
            Log.d("TAGTAG", "ChangeableBack: $it")
            delay(3000)
        }
    }
}