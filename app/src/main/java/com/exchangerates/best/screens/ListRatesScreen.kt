@file:Suppress("EXPERIMENTAL_IS_NOT_ENABLED")

package com.exchangerates.best.screens

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.EditCalendar
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
import com.exchangerates.best.R
import com.exchangerates.best.data.model.RateItemData
import com.exchangerates.best.screens.components.SheetContent
import com.exchangerates.best.screens.home_screen.HomeViewModel
import com.exchangerates.best.ui.theme.*
import java.util.*

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(
    ExperimentalAnimationApi::class,
    ExperimentalMaterialApi::class,
    ExperimentalComposeUiApi::class
)
@Composable
fun HomeScreen(
    navController: NavHostController,
    viewModel: HomeViewModel = viewModel()
) {
    val datePickerDialog: DatePickerDialog

    val coroutineScope = rememberCoroutineScope()
    val openSheet = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    val progressShow = remember { mutableStateOf(false) }

    val context = LocalContext.current as ComponentActivity
    val c = Calendar.getInstance()
    val mYear = c.get(Calendar.YEAR)
    val mMonth = c.get(Calendar.MONTH) + 1
    val mDay = c.get( Calendar.DAY_OF_MONTH )

    datePickerDialog = DatePickerDialog(context, R.style.DatePickerMy, { _, p1, p2, p3 ->
        val date = "$p1-$p2-$p3"
        viewModel.getList( date )
        progressShow.value = true
    }, mYear, mMonth - 1, mDay)
    datePickerDialog.datePicker.maxDate = System.currentTimeMillis()

    val rateItemCurrent = remember { mutableStateOf(value = RateItemData()) }

    BackHandler(onBack = {
        when (openSheet.currentValue) {
            ModalBottomSheetValue.Hidden -> {
                viewModel.showDialog()
            }
            ModalBottomSheetValue.Expanded -> {
                coroutineScope.launch {
                    openSheet.animateTo(
                        ModalBottomSheetValue.HalfExpanded
                    )
                }
            }
            ModalBottomSheetValue.HalfExpanded -> {
                coroutineScope.launch {
                    openSheet.animateTo(
                        ModalBottomSheetValue.Hidden
                    )
                }
            }
        }
    })

    PresentDialog(viewModel, openSheet)

    ModalBottomSheetLayout(
        modifier = Modifier.fillMaxSize(),
        sheetState = openSheet,
        sheetShape = Shapes.large,
        sheetBackgroundColor = Color.Transparent,
        sheetContent = {
            val keyboardController = LocalSoftwareKeyboardController.current
            when (openSheet.currentValue) {
                ModalBottomSheetValue.HalfExpanded -> {
                    keyboardController?.hide()
                }
                ModalBottomSheetValue.Hidden -> {
                    keyboardController?.hide()
                }
                else -> {}
            }
            SheetContent(rateItemCurrent.value, openSheet)
        },
        sheetElevation = 0.dp,
    ) {
        Scaffold(
            backgroundColor = MaterialTheme.colors.onSecondary,
            drawerElevation = 0.dp,
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                ListScreen(
                    navController,
                    openSheet,
                    rateItemCurrent,
                    viewModel = viewModel,
                    progressShow,
                    datePickerDialog
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class, ExperimentalAnimationApi::class,
    androidx.compose.foundation.ExperimentalFoundationApi::class
)
@Composable
fun ListScreen(
    navController: NavHostController,
    openSheet: ModalBottomSheetState,
    rateItemCurrent: MutableState<RateItemData>,
    viewModel: HomeViewModel,
    progressShow: MutableState<Boolean>,
    datePickerDialog: DatePickerDialog,
) {

    var dName by remember { mutableStateOf(TextFieldValue("")) }
    val openSearch = remember { mutableStateOf(false) }
    val darkMode = remember { mutableStateOf(false) }
    val loginState by viewModel.listDataLiveData.observeAsState(emptyList())

    if (loginState.isNotEmpty()) {
        progressShow.value = false
    }

    if (darkMode.value) {
        viewModel.onThemeChanged(true)
    } else {
        viewModel.onThemeChanged(false)
    }

    LazyColumn(modifier = Modifier.padding()) {
        item {
            Column {

                AnimatedContent(targetState = true) {
                    var enabled by remember { mutableStateOf(false) }
                    val focusRequester = remember { FocusRequester() }

                    DisposableEffect(enabled) {
                        if (enabled) {
                            focusRequester.requestFocus()
                        }
                        onDispose {}
                    }

                    TopAppBar(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(70.dp)
                            .clip(RoundedCornerShape(bottomEnd = 15.dp, bottomStart = 15.dp))
                            .background(
                                color = MaterialTheme.colors.secondaryVariant
                            )
                            .blur(radius = 0.dp),
                        title = {
                            AnimatedContent(
                                targetState = openSearch.value
                            ) {
                                if (it) {
                                    Box(
                                        modifier = Modifier.fillMaxWidth(),
                                        contentAlignment = Alignment.CenterStart
                                    ) {
                                        OutlinedTextField(
                                            value = dName,
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(end = 12.dp)
                                                .clip(RoundedCornerShape(30.dp))
                                                .background(color = MaterialTheme.colors.primary)
                                                .padding(start = 8.dp)
                                                .focusRequester(focusRequester),
                                            onValueChange = { newText ->
                                                dName = newText
                                                viewModel.getListSearch(dName.text)
                                            },
                                            singleLine = false,
                                            colors = TextFieldDefaults.textFieldColors(
                                                backgroundColor = if (isSystemInDarkTheme()) BackSearchNight else BackSearch,
                                                cursorColor = White100,
                                                focusedIndicatorColor = Color.Transparent,
                                                unfocusedIndicatorColor = Color.Transparent,
                                                disabledIndicatorColor = Color.Transparent,
                                                leadingIconColor = Color.White,
                                                textColor = Color.White
                                            ),
                                            placeholder = {
                                                Text(
                                                    text = stringResource(R.string.search),
                                                    color = Black50,
                                                    fontSize = 16.sp,
                                                    fontFamily = FontFamily(Font(R.font.my_regular))
                                                )
                                            },
                                            textStyle = TextStyle(fontSize = 14.sp),
                                            trailingIcon = {
                                                if (dName.text != "") {
                                                    Image(
                                                        imageVector = Icons.Default.Close,
                                                        contentDescription = "close search",
                                                        colorFilter = ColorFilter.tint(White100),
                                                        modifier = Modifier.clickable {
                                                            dName = TextFieldValue("")
                                                            viewModel.getListSearch(dName.text)
                                                        }
                                                    )
                                                }
                                            }
                                        )
                                    }
                                } else {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.Center
                                    ) {
                                        IconButton(onClick = {
                                            openSearch.value = !openSearch.value
                                            enabled = openSearch.value
                                            dName = TextFieldValue("")
                                            viewModel.getListSearch("")
                                        }) {
                                            AnimatedContent(targetState = openSearch.value) { isOpen ->
                                                if (isOpen) {
                                                    Icon(
                                                        imageVector = Icons.Default.Close,
                                                        contentDescription = stringResource(R.string.close),
                                                        tint = Color.White
                                                    )
                                                } else {
                                                    Icon(
                                                        imageVector = Icons.Default.Search,
                                                        contentDescription = stringResource(R.string.share),
                                                        tint = Color.White
                                                    )
                                                }
                                            }
                                        }
                                        Text(
                                            stringResource(id = R.string.app_name),
                                            fontFamily = FontFamily(Font(R.font.my_bold)),
                                            modifier = Modifier.padding(bottom = 4.dp, start = 8.dp),
                                            color = Color.White
                                        )
                                    }
                                }
                            }
                        },
                        actions = {
                            IconButton(onClick = {
                                if (openSearch.value) {
                                    openSearch.value = !openSearch.value
                                } else {
                                    darkMode.value = !darkMode.value
                                }
                            }) {
                                AnimatedContent(targetState = darkMode.value) { isMode ->
                                    if (openSearch.value) {
                                        Icon(
                                            imageVector = Icons.Default.Close,
                                            contentDescription = stringResource(R.string.close),
                                            tint = Color.White
                                        )
                                    } else {
                                        if (isMode) {
                                            Icon(
                                                painter = painterResource(id = R.drawable.ic_moon),
                                                contentDescription = stringResource(R.string.close),
                                                tint = Color.White
                                            )
                                        } else {
                                            Icon(
                                                painter = painterResource(id = R.drawable.ic_sun),
                                                contentDescription = stringResource(R.string.share),
                                                tint = Color.White
                                            )
                                        }
                                    }

                                }
                            }
                            IconButton(onClick = {
                                datePickerDialog.show()
                            }) {
                                Icon(
                                    imageVector = Icons.Default.EditCalendar,
                                    contentDescription = stringResource(R.string.calendar),
                                    tint = Color.White
                                )
                            }
                        },
                        backgroundColor = Color.Transparent,
                        elevation = 0.dp
                    )
                }
                Spacer(modifier = Modifier.height(5.dp))
            }
        }
        items(loginState) { names ->
            ItemOfExchange(
                modifier = Modifier.animateItemPlacement(
                    tween(600)
                ),
                name = names,
                navController,
                openSheet,
                rateItemCurrent,
                viewModel,
                progressShow,
                dName
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PresentDialog(openDialog: HomeViewModel, openSheet: ModalBottomSheetState) {
    val context = LocalContext.current
    if (openDialog.openDialog.value) {
        AlertDialog(
            onDismissRequest = {
                openDialog.hideDialog()
            },
            title = {
                Text(
                    text = stringResource(R.string.dialog),
                    fontFamily = FontFamily(Font(R.font.my_bold))
                )
            },
            text = {
                Text(
                    stringResource(R.string.out_pragram),
                    fontFamily = FontFamily(Font(R.font.my_regular))
                )
            },
            shape = RoundedCornerShape(28.dp),
            confirmButton = {
                Text(
                    text = stringResource(R.string.ok), modifier = Modifier
                        .padding(bottom = 16.dp, end = 16.dp)
                        .clickable {
                            (context as ComponentActivity).finish()
                        },
                    fontFamily = FontFamily(Font(R.font.my_regular))
                )
            },
            dismissButton = {
                Text(
                    text = stringResource(R.string.cancel), modifier = Modifier
                        .padding(bottom = 16.dp, end = 16.dp)
                        .clickable {
                            openDialog.hideDialog()
                        },
                    fontFamily = FontFamily(Font(R.font.my_regular))
                )
            },
            backgroundColor = if (isSystemInDarkTheme()) BackSearchNight else BackSearch,
            contentColor = Color.White
        )
    }
}