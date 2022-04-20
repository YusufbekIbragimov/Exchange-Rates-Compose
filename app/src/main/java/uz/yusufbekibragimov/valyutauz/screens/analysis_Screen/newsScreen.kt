package uz.yusufbekibragimov.valyutauz.screens.analysis_Screen

import android.graphics.Paint
import android.graphics.Rect
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.center
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import uz.yusufbekibragimov.valyutauz.screens.ChangeableBack
import java.text.DecimalFormat
import kotlin.math.abs
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun AnalysisScreen(navController: NavHostController) {

    Column(Modifier.fillMaxSize()
    ) {
        Row(Modifier.weight(1f)) {
            ChangeableBack(list = arrayListOf(Color.Green,Color.Blue,Color.Cyan,Color.Magenta,Color.Red,Color.Yellow,Color.Gray))
        }
    }

/*var state by remember { mutableStateOf(false) }
    var angle by remember { mutableStateOf(0f) }
    var oldAngle by remember { mutableStateOf(0f) }
    var dragStartedAngle by remember { mutableStateOf(0f) }
    var offsetAngleDegree = 2f
    var volume by remember { mutableStateOf(0f) }

    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(true) {
                detectDragGestures(
                    onDragStart = { offset ->
                        dragStartedAngle = (atan2(
                            y = size.center.x - offset.x,
                            x = size.center.y - offset.y
                        ) * (180f / Math.PI.toFloat()) * -1)
                    },
                    onDragEnd = {
                        oldAngle = angle
                    }
                ) { change, _ ->

                    val touchAngle = atan2(
                        y = size.center.x - change.position.x,
                        x = size.center.y - change.position.y
                    ) * (180f / Math.PI.toFloat()) * -1

                    angle = oldAngle + (touchAngle - dragStartedAngle)

                    //we want to work with positive angles
                    if (angle > 360) {
                        angle -= 360
                    } else if (angle < 0) {
                        angle = 360 - abs(angle)
                    }

                    if (angle > 360f - (offsetAngleDegree * .8f))
                        angle = 0f
                    else if (angle > 0f && angle < offsetAngleDegree)
                        angle = offsetAngleDegree
                    //determinants the state of the nob. OFF or ON
                    state =
                        angle >= offsetAngleDegree && angle <= (360f - offsetAngleDegree / 2)

                    val newVolume = if (angle < offsetAngleDegree)
                        0f
                    else
                        (angle) / (360f - offsetAngleDegree)

                    volume = newVolume.coerceIn(
                        minimumValue = 0f,
                        maximumValue = 1f
                    )

//                    onVolumeChanged(newVolume)
                }
            }
    ) {
        val halfScreenWidth = size.width * .5f
        val radius = halfScreenWidth - (halfScreenWidth * 0.25f)

        drawCircle(
            color = Color.Gray,
            style = Stroke(
                width = (radius * 0.1f)
            ),
            radius = radius,
            center = size.center
        )

        drawContext.canvas.nativeCanvas.apply {
            val text = if (state) "ON" else "OFF"
            val paint = Paint()
            paint.textSize = radius * 0.15f
            val textRect = Rect()
            paint.getTextBounds(text, 0, text.length, textRect)

            val positionX = size.center.x - (textRect.width() / 2)
            val positionY = size.center.y - radius - (0.15f * radius) + (textRect.height() / 2)

            drawText(
                text,
                positionX,
                positionY,
                paint
            )
        }

        val lineDegree = (360f - 40 * 2) / 40
        for (dotNumber in 0..40) {

            val angleInDegrees = lineDegree * 40 - 90f + offsetAngleDegree
            val angleRad = Math.toRadians(angleInDegrees.toDouble()).toFloat()

            val isDotEmphasised =
                angle >= angleInDegrees + 90f && angle < (360f - offsetAngleDegree / 2)

            val normalDotRad = radius * .015f
            val emphasisedDotRad = normalDotRad + (40 * (radius * .001f))

            val dotRadius = if (isDotEmphasised)
                emphasisedDotRad
            else normalDotRad

            val dotCircleNormalColor = Color(0xff00ff00)
            val dotCircleEmphasisedColor = Color(0xff9737bf)

            val dotColor = if (isDotEmphasised)
                dotCircleEmphasisedColor
            else dotCircleNormalColor

            //basically we are shifting the dot in both X and Y directions by this amount
            val dotDistanceFromMainCircle = radius * .15f

            drawCircle(
                center = Offset(
                    x = (radius + dotDistanceFromMainCircle) * cos(angleRad) + size.center.x,
                    y = (radius + dotDistanceFromMainCircle) * sin(angleRad) + size.center.y
                ),
                color = dotColor,
                radius = dotRadius
            )
        }

        val spacing = radius * 0.225f
        val knobOffset = Offset(
            x = (radius - spacing) * cos((angle - 90f).toRad()) + size.center.x,
            y = (radius - spacing) * sin((angle - 90f).toRad()) + size.center.y
        )

        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(Color.Transparent, Color.Green),
                center = knobOffset,
                radius = radius * (1f / 9f)
            ),
            radius = radius * (1f / 9f),
            center = knobOffset
        )

    }*/

}

private fun Float.toRad(): Float {
    return this * (Math.PI / 180f).toFloat()
}

@Composable
fun VolumeKnob(
    modifier: Modifier,
    numberOfCircles: Int = 40,
    minSize: Dp = 64.dp,
    onVolumeChanged: (Float) -> Unit = {}
) {

    var angle by remember { mutableStateOf(0f) }
    var dragStartedAngle by remember { mutableStateOf(0f) }
    var oldAngle by remember { mutableStateOf(angle) }
    var volume by remember { mutableStateOf(0f) }

    //represents the nob state ON or OFF
    var state by remember { mutableStateOf(false) }
    val colourTransitionDurationMs = 1000

    val volumeFormatter = DecimalFormat("#.#")
    val ringColorOff = Color.LightGray
    val ringColorON = Color.Green
    val dotCircleNormalColor = Color(0xff00ff00)
    val dotCircleEmphasisedColor = Color(0xff9737bf)
    val offsetAngleDegree = 20f

    val ringColor by animateColorAsState(
        targetValue = if (state) ringColorON else ringColorOff,
        animationSpec = tween(
            durationMillis = colourTransitionDurationMs,
        )
    )

    BoxWithConstraints() {

        val width = if (minWidth < 1.dp)
            minSize else minWidth

        val height = if (minHeight < 1.dp)
            minSize else minHeight

        Canvas(
            modifier = modifier
                .fillMaxSize()
                .pointerInput(true) {
                    detectDragGestures(
                        onDragStart = { offset ->
                            dragStartedAngle = atan2(
                                y = size.center.x - offset.x,
                                x = size.center.y - offset.y
                            ) * (180f / Math.PI.toFloat()) * -1
                        },
                        onDragEnd = {
                            oldAngle = angle
                        }
                    ) { change, _ ->

                        val touchAngle = atan2(
                            y = size.center.x - change.position.x,
                            x = size.center.y - change.position.y
                        ) * (180f / Math.PI.toFloat()) * -1

                        angle = oldAngle + (touchAngle - dragStartedAngle)

                        //we want to work with positive angles
                        if (angle > 360) {
                            angle -= 360
                        } else if (angle < 0) {
                            angle = 360 - abs(angle)
                        }

                        if (angle > 360f - (offsetAngleDegree * .8f))
                            angle = 0f
                        else if (angle > 0f && angle < offsetAngleDegree)
                            angle = offsetAngleDegree
                        //determinants the state of the nob. OFF or ON
                        state =
                            angle >= offsetAngleDegree && angle <= (360f - offsetAngleDegree / 2)

                        val newVolume = if (angle < offsetAngleDegree)
                            0f
                        else
                            (angle) / (360f - offsetAngleDegree)

                        volume = newVolume.coerceIn(
                            minimumValue = 0f,
                            maximumValue = 1f
                        )

                        onVolumeChanged(newVolume)
                    }
                }
        ) {
            //just calculating the radius so that the circle will fill the parent
            //also adding 25% padding of the radius as a padding to the parent..
            val radius = (size.width * .5f) - (.25f * (size.width * .5f))

            drawCircle(
                color = ringColor,
                style = Stroke(
                    width = (radius * .1f)
                ),
                radius = radius,
                center = size.center
            )

            // Represents the angle difference between each dot.
            // We are taking into consideration the offsetAngleDegree as wall.
            val lineDegree = (360f - offsetAngleDegree * 2) / numberOfCircles

            for (circleNumber in 0..numberOfCircles) {


                val angleInDegrees = lineDegree * circleNumber - 90f + offsetAngleDegree
                val angleRad = Math.toRadians(angleInDegrees.toDouble()).toFloat()

                val isDotEmphasised =
                    angle >= angleInDegrees + 90f && angle < (360f - offsetAngleDegree / 2)

                val normalDotRad = radius * .015f
                val emphasisedDotRad = normalDotRad + (circleNumber * (radius * .001f))

                val dotRadius = if (isDotEmphasised)
                    emphasisedDotRad
                else normalDotRad

                val dotColor = if (isDotEmphasised)
                    dotCircleEmphasisedColor
                else dotCircleNormalColor

                //basically we are shifting the dot in both X and Y directions by this amount
                val dotDistanceFromMainCircle = radius * .15f

                drawCircle(
                    center = Offset(
                        x = (radius + dotDistanceFromMainCircle) * cos(angleRad) + size.center.x,
                        y = (radius + dotDistanceFromMainCircle) * sin(angleRad) + size.center.y
                    ),
                    color = dotColor,
                    radius = dotRadius
                )
            }

            val spacing = radius * .225f // ~22% of the radius

            val knobCenter = Offset(
                x = (radius - spacing) * cos((angle - 90f).toRad()) + size.center.x,
                y = (radius - spacing) * sin((angle - 90f).toRad()) + size.center.y
            )

            //the nob itself
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(Color.Transparent, Color.Gray),
                    center = knobCenter,
                    radius = radius * (1f / 9f)
                ),
                radius = radius * (1f / 9f),
                center = knobCenter
            )

            //state text.
            drawContext.canvas.nativeCanvas.apply {
                val text = if (state) "ON" else "OFF"
                val paint = Paint()
                paint.textSize = radius * .15f
                val textRect = Rect()
                paint.getTextBounds(text, 0, text.length, textRect)

                val positionX = size.center.x - (textRect.width() / 2)
                val positionY = size.center.y - radius - (.15f * radius) + (textRect.height() / 2)

                drawText(
                    text,
                    positionX,
                    positionY,
                    paint
                )

                if (state) {
                    val volumeText = if (state) {
                        volumeFormatter.format((volume * 100))
                    } else "OFF"

                    val volumePaint = Paint()
                    volumePaint.color = android.graphics.Color.parseColor("#9737bf")
                    volumePaint.textSize = (radius / 2)
                    val volumeTextRect = Rect()
                    volumePaint.getTextBounds(volumeText, 0, volumeText.length, volumeTextRect)

                    val volumeTextPositionX = size.center.x - (volumeTextRect.width() / 2)
                    val volumeTextPositionY = size.center.y + (volumeTextRect.height() / 2)

                    drawText(
                        volumeText,
                        volumeTextPositionX,
                        volumeTextPositionY,
                        volumePaint
                    )
                }
            }
        }

    }
}
/*
TabRow(selectedTabIndex = 0) {
    Text(
        text = "Year",
        modifier = Modifier
            .wrapContentSize()
            .padding(horizontal = 12.dp, vertical = 12.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(color = MaterialTheme.colors.secondaryVariant)
            .padding(horizontal = 12.dp, vertical = 12.dp),
        fontFamily = FontFamily(Font(R.font.my_regular)),
        fontSize = 18.sp
    )
    Text(
        text = "Year",
        modifier = Modifier
            .wrapContentSize()
            .padding(horizontal = 12.dp, vertical = 12.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(color = MaterialTheme.colors.secondaryVariant)
            .padding(horizontal = 12.dp, vertical = 12.dp),
        fontFamily = FontFamily(Font(R.font.my_regular)),
        fontSize = 18.sp
    )
    Text(
        text = "Year",
        modifier = Modifier
            .wrapContentSize()
            .padding(horizontal = 12.dp, vertical = 12.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(color = MaterialTheme.colors.secondaryVariant)
            .padding(horizontal = 12.dp, vertical = 12.dp),
        fontFamily = FontFamily(Font(R.font.my_regular)),
        fontSize = 18.sp
    )
}*/
