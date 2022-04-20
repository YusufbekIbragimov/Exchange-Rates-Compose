package uz.yusufbekibragimov.valyutauz.screens.news_Screen

import android.util.Log
import android.view.MotionEvent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import uz.yusufbekibragimov.valyutauz.R
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.absoluteValue

@Composable
fun NewsScreen(navController: NavHostController) {

    Parallax()

}

val maxAngle = 20f
val maxTranslation = 45f

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun Parallax() {
    var angle by remember { mutableStateOf(Pair(0f, 0f)) }
    var start by remember { mutableStateOf(Pair(-1f, -1f)) }
    var viewSize by remember { mutableStateOf(Size.Zero) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
            .onGloballyPositioned { coordinates ->
                viewSize = Size(
                    width = coordinates.size.width.toFloat(),
                    height = coordinates.size.height.toFloat()
                )
            }
            .pointerInteropFilter { m ->
                Log.d("TAGTAG", "Parallax: 1")
                when (m.action) {
                    MotionEvent.ACTION_UP -> {
                        start = Pair(-1f, -1f)
                        angle = Pair(0f, 0f)
                    }
                    MotionEvent.ACTION_DOWN -> {
                        start = Pair(m.rawX, m.rawY)
                        angle = Pair(0f, 0f)
                    }
                    MotionEvent.ACTION_MOVE -> {
                        if (viewSize != Size.Zero) {
                            val end = Pair(m.rawX, m.rawY)
                            val newAngle = getRotationAngles(start, end, viewSize)
                            var x: Float = angle.first + newAngle.first
                            var y: Float = angle.second + newAngle.second

                            if (x > maxAngle) x = maxAngle
                            else if (x < -maxAngle) x = -maxAngle

                            if (y > maxAngle) y = maxAngle
                            else if (y < -maxAngle) y = -maxAngle

                            angle = Pair(x, y)
                            start = end
                        }
                    }
                }
                Log.d("TAGTAG", "Parallax: ${m.metaState}")
                true
            }
    ) {
        Box(
            contentAlignment = Alignment.TopCenter,
            modifier = Modifier
                .weight(1f)
                .size(350.dp)
                .padding(top = 32.dp)
        ) {
            Card(
                shape = RoundedCornerShape(18.dp),
                backgroundColor = Color.White,
                elevation = 6.dp,
                modifier = Modifier
                    .graphicsLayer(
                        transformOrigin = TransformOrigin(0.5f, 0.5f),
                        rotationY = animateFloatAsState(
                            -angle.first,
                            animationSpec = spring()
                        ).value,
                        rotationX = animateFloatAsState(
                            angle.second,
                            animationSpec = spring()
                        ).value,
                        cameraDistance = 16.dp.value
                    )
                    .width(400.dp)
                    .aspectRatio(1.6f)
            ) {
                Column {
                    Image(
                        painter = painterResource(id = R.drawable.my_app_logo),
                        contentDescription = "Parallax Background",
                        modifier = Modifier.size(96.dp).shadow(elevation = 16.dp, ambientColor = Color.Transparent, spotColor = Color.Transparent)
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = "4212 1212 3543 5431",
                        color = Color.Black,
                        fontSize = 26.sp,
                        fontFamily = FontFamily.Monospace,
                        modifier = Modifier
                            .alpha(1.6f)
                            .background(Color.Transparent).align(Alignment.CenterHorizontally)
                            .padding( horizontal = 16.dp, vertical = 8.dp).shadow(elevation = 16.dp, ambientColor = Color.Transparent, spotColor = Color.Transparent)
                    )
                    Text(
                        text = "</> Made with Jetpack Compose",
                        color = Color.Black,
                        fontSize = 9.sp,
                        fontFamily = FontFamily.Monospace,
                        modifier = Modifier
                            .alpha(1.6f)
                            .background(Color.Transparent)
                            .padding( 16.dp).shadow(elevation = 16.dp, ambientColor = Color.Transparent, spotColor = Color.Transparent)
                    )
                }

            }
        }

    }

}

/**
 * This method converts the current touch input to rotation values based on the original point
 * at which the touch event started.
 *
 * @param start : coordinates of first touch event
 * @param end : coordinates of final touch event
 */

fun getRotationAngles(
    start: Pair<Float, Float>,
    end: Pair<Float, Float>,
    size: Size
): Pair<Float, Float> {
    /**
     * 1. get the magnitude of drag event, based on screen's width & height & acceleration
     * 2. get the direction/angle of the drag event
     */
    val acceleration = 3
    val distances = getDistances(end, start)
    val rotationX = (distances.first / size.width) * maxAngle * acceleration
    val rotationY = (distances.second / size.height) * maxAngle * acceleration
    return Pair(rotationX, rotationY)
}

fun getDistances(p1: Pair<Float, Float>, p2: Pair<Float, Float>): Pair<Float, Float> {
    return Pair(
        p2.first - p1.first,
        p2.second - p1.second
    )
}

fun getTranslation(angle: Float, maxDistance: Float): Float {
    return (angle / 90f) * maxDistance
}