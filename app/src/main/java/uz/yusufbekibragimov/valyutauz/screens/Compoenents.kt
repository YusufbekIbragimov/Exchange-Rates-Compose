package uz.yusufbekibragimov.valyutauz.screens

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
import me.bytebeats.views.charts.line.LineChart
import me.bytebeats.views.charts.line.LineChartData
import me.bytebeats.views.charts.line.render.line.SolidLineDrawer
import me.bytebeats.views.charts.line.render.point.FilledCircularPointDrawer
import me.bytebeats.views.charts.line.render.yaxis.SimpleYAxisDrawer
import uz.yusufbekibragimov.valyutauz.R
import uz.yusufbekibragimov.valyutauz.data.model.ExchangeDates
import uz.yusufbekibragimov.valyutauz.data.model.ExchangeUiData
import uz.yusufbekibragimov.valyutauz.data.model.RateItemData
import uz.yusufbekibragimov.valyutauz.screens.home_screen.HomeViewModel
import uz.yusufbekibragimov.valyutauz.ui.theme.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.random.Random

/**
 * Created by Ibragimov Yusufbek
 * Date: 24.02.2022
 * Project: ComposeNavigation
 **/

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ItemExchange(
    name: RateItemData,
    navController: NavHostController,
    openSheet: ModalBottomSheetState,
    rateItemCurrent: MutableState<RateItemData>,
    viewModel: HomeViewModel
) {
    Card(
        backgroundColor = Color.Transparent,
        elevation = 0.dp,
        modifier = Modifier
            .padding(vertical = 5.dp, horizontal = 8.dp)
            .clip(RoundedCornerShape(15.dp))
            .background(
                brush = Brush.sweepGradient(
                    if (isSystemInDarkTheme()) listOf(color3, color5) else listOf(color03, color05),
                    center = Offset.VisibilityThreshold
                )
            )
    ) {
        FirstView(name, navController, openSheet, rateItemCurrent, viewModel)
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FirstView(
    n: RateItemData,
    navController: NavHostController,
    openSheet: ModalBottomSheetState,
    rateItemCurrent: MutableState<RateItemData>,
    viewModel: HomeViewModel
) {
    var expanded by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    val nItem = n

    Row(
        modifier = Modifier
            .clickable {
                expanded = !expanded
                var today = Calendar.getInstance().timeInMillis
                val data = SimpleDateFormat("yyyy-MM-dd")
                val r = data.format(Date(today))

                today = (today - (86400 * 7000))
                val data2 = SimpleDateFormat("yyyy-MM-dd")
                val r2 = data.format(Date(today))

                n.ccy?.let {
                    n.id?.let { it1 ->
                        nItem.id = it1
                        viewModel.getGraphList(r2, r, it, it1)
                    }
                }
            }
            .padding(12.dp)
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
                .padding(12.dp)
        ) {
            Row() {
                Column(
                    Modifier.weight(1f)
                ) {
                    val text = "1 ${n.ccy} => ${n.rate} UZS"
                    n.ccyNmUZ?.let {
                        Text(
                            text = it,
                            style = MaterialTheme.typography.h5.copy(
                                fontWeight = FontWeight.ExtraBold,
                                fontFamily = FontFamily(Font(R.font.my_bold))
                            ),
                            color = White100,
                            maxLines = 1
                        )
                    }
                    Text(
                        text = text,
                        color = White80,
                        fontSize = 13.sp,
                        fontFamily = FontFamily(Font(R.font.my_regular))
                    )
                }

                IconButton(onClick = {
                    expanded = !expanded
                    var today = Calendar.getInstance().timeInMillis
                    val data = SimpleDateFormat("yyyy-MM-dd")
                    val r = data.format(Date(today))

                    today = (today - (86400 * 7000))
                    val data2 = SimpleDateFormat("yyyy-MM-dd")
                    val r2 = data.format(Date(today))

                    n.ccy?.let {
                        n.id?.let { it1 ->
                            nItem.id = it1
                            viewModel.getGraphList(r2, r, it, it1)
                        }
                    }

                }) {
                    Icon(
                        imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                        tint = White100,
                        contentDescription = if (expanded) {
                            "stringResource(R.string.show_less)"
                        } else {
                            "stringResource(R.string.show_more)"
                        },
                    )
                }
            }
            if (expanded) {
                Column(horizontalAlignment = Alignment.End) {

                    ChartMy(n, viewModel, nItem)
                    androidx.compose.material3.Button(
                        modifier = Modifier
                            .padding(top = 16.dp),
                        onClick = {
                            coroutineScope.launch {
                                rateItemCurrent.value = n
                                openSheet.animateTo(ModalBottomSheetValue.Expanded)
                            }
                        },
                    ) {
                        Row(
                            modifier = Modifier
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

fun randomYValue(): Float {
    return Random.nextInt(0, 100).toFloat()
}

@Composable
fun ChartMy(
    n: RateItemData,
    viewModel: HomeViewModel,
    nItem: RateItemData
) {
    var dataState = remember { ExchangeUiData(ExchangeDates(), -1) }
    val loginState by viewModel.graphListLiveData.observeAsState()
    if (nItem.id == loginState?.codeUnique) {
        if (loginState != null) {
            dataState = loginState as ExchangeUiData
        }
    }

    val data = dataState.exchangeDates

    if (loginState != null) {
        LineChart(
            lineChartData = LineChartData(
                points = listOf(
                    LineChartData.Point(
                        data.data?.get(0)?.currencies?.uZS?.value?.toFloat()!!,
                        data.data[0].datetime.substring(8, 10)
                    ),
                    LineChartData.Point(
                        data.data[1].currencies.uZS.value.toFloat(),
                        data.data[1].datetime.substring(8, 10)
                    ),
                    LineChartData.Point(
                        data.data[2].currencies.uZS.value.toFloat(),
                        data.data[2].datetime.substring(8, 10)
                    ),
                    LineChartData.Point(
                        data.data[3].currencies.uZS.value.toFloat(),
                        data.data[3].datetime.substring(8, 10)
                    ),
                    LineChartData.Point(
                        data.data[4].currencies.uZS.value.toFloat(),
                        data.data[4].datetime.substring(8, 10)
                    ),
                    LineChartData.Point(
                        data.data[5].currencies.uZS.value.toFloat(),
                        data.data[5].datetime.substring(8, 10)
                    ),
                    LineChartData.Point(
                        data.data[6].currencies.uZS.value.toFloat(),
                        data.data[6].datetime.substring(8, 10)
                    ),
                )
            ),
            modifier = Modifier
                .wrapContentWidth()
                .height(125.dp)
                .padding(top = 24.dp, start = 8.dp, end = 8.dp),
            animation = simpleChartAnimation2(),
            pointDrawer = FilledCircularPointDrawer(color = White100),
            lineDrawer = SolidLineDrawer(color = Color.Green),
            xAxisDrawer = me.bytebeats.views.charts.line.render.xaxis.SimpleXAxisDrawer(
                labelTextColor = White100,
                axisLineColor = White100
            ),
            yAxisDrawer = SimpleYAxisDrawer(labelTextColor = White100, axisLineColor = White100),
            horizontalOffset = 5f
        )
    }

}

fun simpleChartAnimation2(): AnimationSpec<Float> =
    TweenSpec(durationMillis = 750, easing = FastOutSlowInEasing)