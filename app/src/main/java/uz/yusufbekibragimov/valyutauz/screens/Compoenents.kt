package uz.yusufbekibragimov.valyutauz.screens

import android.util.Log
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
import uz.yusufbekibragimov.valyutauz.R
import uz.yusufbekibragimov.valyutauz.data.model.RateItemData
import uz.yusufbekibragimov.valyutauz.screens.home_screen.HomeViewModel
import uz.yusufbekibragimov.valyutauz.ui.theme.*
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
    name: RateItemData,
    navController: NavHostController,
    openSheet: ModalBottomSheetState,
    rateItemCurrent: MutableState<RateItemData>,
    viewModel: HomeViewModel,
    progressShow: MutableState<Boolean>,
    dName: MutableState<TextFieldValue>
) {

    var currentState = remember { mutableStateOf(BounceState.Released) }
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
        backgroundColor = if (isSystemInDarkTheme()) Black80 else White100,
        modifier = Modifier
            .graphicsLayer(scaleX = scale, scaleY = scale)
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .background(Color.Transparent),
        elevation = 6.dp,
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
    dName: MutableState<TextFieldValue>,
    currentState: MutableState<BounceState>
) {
    var expanded by remember { mutableStateOf(false) }
    val rateItem = remember { mutableStateOf(n) }
    val coroutineScope = rememberCoroutineScope()

    val rateItemState by viewModel.graphListLiveData.observeAsState(RateItemData())
    if (rateItemState.id == rateItem.value.id) {
        rateItem.value.exchangeDates = rateItemState.exchangeDates
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
                        expanded = !expanded
                        if (expanded) {
                            var today = Calendar.getInstance().timeInMillis
                            val data = SimpleDateFormat("yyyy-MM-dd")
                            val r = data.format(Date(today))

                            today = (today - (86400 * 10000))
                            val r2 = data.format(Date(today))

                            if (rateItem.value.exchangeDates == null) {
                                rateItem.value.ccy?.let {
                                    rateItem.value.id?.let { it1 ->
                                        viewModel.getGraphList(r2, r, it, rateItem.value)
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
                                text2.indexOf(dName.value.text.toUpperCase(Locale.ENGLISH))
                            val endIndex = startIndex + dName.value.text.length
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
                            color = if (isSystemInDarkTheme()) White100 else Black100,
                            maxLines = 1
                        )
                    }
                    Text(
                        text = text,
                        color = if (isSystemInDarkTheme()) White80 else Black80,
                        fontSize = 15.sp,
                        fontFamily = FontFamily(Font(R.font.my_regular)),
                        maxLines = 1
                    )
                }

                if (progressShow.value) {
                    CircularProgressIndicator(
                        color = if (isSystemInDarkTheme()) White100 else Black100,
                        modifier = Modifier
                            .size(18.dp)
                            .align(Alignment.CenterVertically),
                        strokeWidth = 2.5.dp
                    )
                }

                IconButton(
                    onClick = {
                        expanded = !expanded
                        if (expanded) {
                            var today = Calendar.getInstance().timeInMillis
                            val data = SimpleDateFormat("yyyy-MM-dd")
                            val r = data.format(Date(today))

                            today = (today - (86400 * 10000))
                            val r2 = data.format(Date(today))

                            rateItem.value.ccy?.let {
                                rateItem.value.id?.let { it1 ->
                                    viewModel.getGraphList(r2, r, it, rateItem.value)
                                }
                            }
                        }
                    },
                    Modifier.align(Alignment.CenterVertically)
                ) {
                    Icon(
                        imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                        tint = if (isSystemInDarkTheme()) White100 else Black100,
                        contentDescription = if (expanded) {
                            "show_less"
                        } else {
                            "show_more"
                        },
                    )
                }
            }
            if (expanded) {
                Column(horizontalAlignment = Alignment.End) {
                    ChartMy(rateItem, dName)
                    var currentState = remember { mutableStateOf(BounceState.Released) }
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

@Composable
fun ChartMy(nItem: MutableState<RateItemData>, dname: MutableState<TextFieldValue>) {
    val bool = remember { mutableStateOf(false) }
    bool.value = nItem.value.exchangeDates != null
    Log.d("TAGTAG", "ChartMy: ${nItem.value.ccy}")
    if (bool.value) {
        val i = LineChart(
            lineChartData = LineChartData(
                points = getData(nItem.value)
            ),
            modifier = Modifier
                .wrapContentWidth()
                .height(125.dp)
                .padding(top = 24.dp, start = 16.dp, end = 16.dp),
            animation = simpleChartAnimation2(),
            pointDrawer = FilledCircularPointDrawer(color = GreenChart),
            lineDrawer = SolidLineDrawer(color = Color.Green),
            xAxisDrawer = me.bytebeats.views.charts.line.render.xaxis.SimpleXAxisDrawer(
                labelTextColor = if (isSystemInDarkTheme()) White100 else Black100,
                axisLineColor = if (isSystemInDarkTheme()) White100 else Black100
            ),
            yAxisDrawer = SimpleYAxisDrawer(
                labelTextColor = if (isSystemInDarkTheme()) White100 else Black100,
                axisLineColor = if (isSystemInDarkTheme()) White100 else Black100
            ),
            horizontalOffset = 5f,
            lineShader = GradientLineShader(listOf(chart1, chart2, Color.Transparent))
        )
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