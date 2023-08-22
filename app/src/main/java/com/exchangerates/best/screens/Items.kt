@file:Suppress("EXPERIMENTAL_IS_NOT_ENABLED")

package com.exchangerates.best.screens

import android.util.Log
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
import me.bytebeats.views.charts.line.LineChart
import me.bytebeats.views.charts.line.LineChartData
import me.bytebeats.views.charts.line.render.line.ILineShader
import me.bytebeats.views.charts.line.render.line.SolidLineDrawer
import me.bytebeats.views.charts.line.render.point.FilledCircularPointDrawer
import me.bytebeats.views.charts.line.render.yaxis.SimpleYAxisDrawer
import com.exchangerates.best.R
import com.exchangerates.best.data.model.RateItemData
import com.exchangerates.best.navigation.Screen
import com.exchangerates.best.screens.home_screen.HomeViewModel
import com.exchangerates.best.ui.theme.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Ibragimov Yusufbek
 * Date: 24.02.2022
 * Project: ComposeNavigation
 **/

enum class BounceState { Pressed, Released }

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ItemExchange(
    modifier: Modifier,
    name: RateItemData,
    navController: NavHostController,
    openSheet: ModalBottomSheetState,
    rateItemCurrent: MutableState<RateItemData>,
    viewModel: HomeViewModel,
    progressShow: MutableState<Boolean>,
    dName: TextFieldValue
) {

    val currentState = remember { mutableStateOf(BounceState.Released) }
    val transition = updateTransition(targetState = currentState, label = "animation")

    val scale: Float by transition.animateFloat(
        transitionSpec = {
            spring(
                stiffness = 1100f,
                dampingRatio = Spring.DampingRatioMediumBouncy
            )
        }, label = ""
    ) { state ->
        if (state.value == BounceState.Pressed) {
            0.90f
        } else {
            1f
        }
    }

    Card(
        backgroundColor = MaterialTheme.colors.onBackground,
        modifier = modifier
            .graphicsLayer(scaleX = scale, scaleY = scale)
            .padding(vertical = 5.dp, horizontal = 16.dp)
            .background(Color.Transparent),
        elevation = 4.dp,
        shape = RoundedCornerShape(15.dp)
    ) {
        FirstView(
            name,
            navController,
            openSheet,
            rateItemCurrent,
            viewModel,
            progressShow,
            dName,
            currentState
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FirstView(
    n: RateItemData,
    navController: NavHostController,
    openSheet: ModalBottomSheetState,
    rateItemCurrent: MutableState<RateItemData>,
    viewModel: HomeViewModel,
    progressShow: MutableState<Boolean>,
    dName: TextFieldValue,
    currentState: MutableState<BounceState>
) {
    var rateItem by remember { mutableStateOf(n) }
    rateItem = n
    val coroutineScope = rememberCoroutineScope()
    var expandData by remember { mutableStateOf(false) }

    val rateItemState by viewModel.graphListLiveData.observeAsState(RateItemData())
    if (rateItemState.id == rateItem.id) {
        rateItem.exchangeDates = rateItemState.exchangeDates
    }

    Row(
        modifier = Modifier
            .pointerInput({}) {
                detectTapGestures(
                    onPress = {
                        currentState.value = BounceState.Pressed
                        tryAwaitRelease()
                        currentState.value = BounceState.Released
                    },
                    onTap = {
                        expandData = !expandData
                        if (rateItem.exchangeDates == null) {
                            var today = Calendar.getInstance().timeInMillis
                            val data = SimpleDateFormat("yyyy-MM-dd")
                            val r = data.format(Date(today))

                            today = (today - (86400 * 10000))
                            val r2 = data.format(Date(today))

                            if (rateItem.exchangeDates == null) {
                                rateItem.ccy?.let {
                                    rateItem.id?.let { it1 ->
                                        viewModel.getGraphList(r2, r, it, rateItem)
                                    }
                                }
                            }

                        }
                    })
            }
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(24.dp)
        ) {
            Row {
                Column(
                    Modifier.weight(1f)
                ) {
                    val text = "1 ${n.ccy} => ${n.rate} UZS | \uD83D\uDDD3 ${n.date}"
                    n.ccyNmUZ?.let {
                        val annotatedString = buildAnnotatedString {
                            val text = it
                            val text2 = text.toUpperCase(Locale.ENGLISH)
                            val startIndex =
                                text2.indexOf(dName.text.toUpperCase(Locale.ENGLISH))
                            val endIndex = startIndex + dName.text.length
                            append(text)
                            addStyle(
                                style = SpanStyle(
                                    color = Color(0xff64B5F6),
                                    textDecoration = TextDecoration.Underline,
                                ),
                                start = startIndex, end = endIndex,
                            )
                        }
                        Text(
                            text = annotatedString,
                            style = MaterialTheme.typography.h5.copy(
                                fontWeight = FontWeight.ExtraBold,
                                fontFamily = FontFamily(Font(R.font.my_bold))
                            ),
                            color = MaterialTheme.colors.onSurface,
                            maxLines = 1
                        )
                    }
                    Text(
                        text = text,
                        color = MaterialTheme.colors.background,
                        fontSize = 15.sp,
                        fontFamily = FontFamily(Font(R.font.my_regular)),
                        maxLines = 1
                    )
                }

                if (progressShow.value) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colors.onSurface,
                        modifier = Modifier
                            .size(18.dp)
                            .align(Alignment.CenterVertically),
                        strokeWidth = 2.5.dp
                    )
                }

                IconButton(
                    modifier = Modifier
                        .align(Alignment.CenterVertically),
                    onClick = {
                        expandData = !expandData
                        if (rateItem.exchangeDates == null) {
                            var today = Calendar.getInstance().timeInMillis
                            val data = SimpleDateFormat("yyyy-MM-dd")
                            val r = data.format(Date(today))

                            today = (today - (86400 * 10000))
                            val r2 = data.format(Date(today))

                            if (rateItem.exchangeDates == null) {
                                rateItem.ccy?.let {
                                    rateItem.id?.let { it1 ->
                                        viewModel.getGraphList(r2, r, it, rateItem)
                                    }
                                }
                            }

                        }
                    }
                ) {
                    Icon(
                        imageVector = if (expandData) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                        tint = MaterialTheme.colors.onSurface,
                        contentDescription = if (expandData) {
                            "show_less"
                        } else {
                            "show_more"
                        }
                    )
                }
            }

            if (expandData) {
                if (rateItem.exchangeDates == null) {
                    var today = Calendar.getInstance().timeInMillis
                    val data = SimpleDateFormat("yyyy-MM-dd")
                    val r = data.format(Date(today))

                    today = (today - (86400 * 10000))
                    val r2 = data.format(Date(today))

                    if (rateItem.exchangeDates == null) {
                        rateItem.ccy?.let {
                            rateItem.id?.let { it1 ->
                                viewModel.getGraphList(r2, r, it, rateItem)
                            }
                        }
                    }

                }
            }

            if (expandData) {

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.End
                ) {
                    ChartMy(rateItem, dName)
                    val currentState = remember { mutableStateOf(BounceState.Released) }
                    val transition =
                        updateTransition(targetState = currentState, label = "animation")

                    val scale: Float by transition.animateFloat(
                        transitionSpec = {
                            spring(
                                stiffness = 1100f,
                                dampingRatio = Spring.DampingRatioMediumBouncy
                            )
                        }, label = ""
                    ) { state ->
                        if (state.value == BounceState.Pressed) {
                            0.90f
                        } else {
                            1f
                        }
                    }
                    Row {

                        androidx.compose.material3.Button(
                            modifier = Modifier
                                .padding(top = 16.dp)
                                .graphicsLayer(scaleX = scale, scaleY = scale),
                            onClick = {}
                        ) {
                            Row(
                                modifier = Modifier
                                    .pointerInput({}) {
                                        detectTapGestures(
                                            onPress = {
                                                currentState.value = BounceState.Pressed
                                                tryAwaitRelease()
                                                currentState.value = BounceState.Released
                                            },
                                            onTap = {
                                                coroutineScope.launch {
                                                    rateItemCurrent.value = n
                                                    openSheet.animateTo(ModalBottomSheetValue.Expanded)
                                                }
                                            }
                                        )
                                    }
                                    .padding(4.dp)
                            ) {
                                Image(
                                    imageVector = Icons.Default.Calculate,
                                    contentDescription = "Calculate",
                                    colorFilter = ColorFilter.tint(Color.White),
                                    modifier = Modifier.padding(end = 8.dp)
                                )
                                Text(text = "Calculate", color = Color.White)
                            }
                        }
                    }

                }
            }
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ChartMy(nItem: RateItemData, dname: TextFieldValue) {
    val bool = remember { mutableStateOf(false) }
    bool.value = nItem.exchangeDates != null
    if (bool.value) {
        val color = MaterialTheme.colors.onSurface
        AnimatedContent(targetState = true) {
            LineChart(
                lineChartData = LineChartData(
                    points = getData(nItem)
                ),
                modifier = Modifier
                    .fillMaxSize()
                    .height(125.dp)
                    .padding(top = 24.dp, start = 16.dp, end = 16.dp),
                animation = simpleChartAnimation2(),
                pointDrawer = FilledCircularPointDrawer(color = GreenChart),
                lineDrawer = SolidLineDrawer(color = Color.Green),
                xAxisDrawer = me.bytebeats.views.charts.line.render.xaxis.SimpleXAxisDrawer(
                    labelTextColor = color,
                    axisLineColor = color
                ),
                yAxisDrawer = SimpleYAxisDrawer(
                    labelTextColor = color,
                    axisLineColor = color
                ),
                horizontalOffset = 5f,
                lineShader = GradientLineShader(listOf(chart1, chart2, Color.Transparent))
            )
        }
    }
}

fun getData(nItem: RateItemData): List<LineChartData.Point> {
    val listData = ArrayList<LineChartData.Point>()
    nItem.exchangeDates?.data?.forEach {
        listData.add(
            LineChartData.Point(
                it.currencies.uZS.value.toFloat(),
                "${it.datetime.substring(8, 10)}"
            )
        )
    }
    return listData
}

fun simpleChartAnimation2(): AnimationSpec<Float> =
    TweenSpec(durationMillis = 750, easing = FastOutSlowInEasing)

data class GradientLineShader(val colors: List<Color> = listOf(Color.Blue, Color.Transparent)) :
    ILineShader {

    private val mBrush = Brush.verticalGradient(colors)

    override fun fillLine(drawScope: DrawScope, canvas: Canvas, fillPath: Path) {
        drawScope.drawPath(path = fillPath, brush = mBrush)
    }
}