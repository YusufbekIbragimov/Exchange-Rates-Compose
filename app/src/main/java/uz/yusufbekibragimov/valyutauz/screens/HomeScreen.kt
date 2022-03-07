package uz.yusufbekibragimov.valyutauz.screens

import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
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
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
import uz.yusufbekibragimov.valyutauz.R
import uz.yusufbekibragimov.valyutauz.data.model.RateItemData
import uz.yusufbekibragimov.valyutauz.screens.components.DrawerLayoutAtHome
import uz.yusufbekibragimov.valyutauz.screens.components.SheetContent
import uz.yusufbekibragimov.valyutauz.screens.home_screen.HomeViewModel
import uz.yusufbekibragimov.valyutauz.ui.theme.*

/**
 * Created by Ibragimov Yusufbek
 * Date: 16.02.2022
 * Project: ComposeNavigation
 **/

@OptIn(
    ExperimentalAnimationApi::class, androidx.compose.material.ExperimentalMaterialApi::class,
    androidx.compose.ui.ExperimentalComposeUiApi::class
)
@Composable
fun HomeScreen(
    navController: NavHostController,
    viewModel: HomeViewModel = viewModel()
) {
    val coroutineScope = rememberCoroutineScope()
    val openSearch = remember { mutableStateOf(false) }
    val dName = remember { mutableStateOf(TextFieldValue("")) }
    val rateItemCurrent = remember { mutableStateOf<RateItemData>(value = RateItemData()) }
    val openSheet = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)

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

    val loginState by viewModel.listDataLiveData.observeAsState(emptyList())
    val scaffoldState = rememberScaffoldState()

    viewModel.getList()
    presentDialog(viewModel, openSheet)

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
            }
            SheetContent(rateItemCurrent.value, openSheet)
        },
        sheetElevation = 0.dp,
    ) {
        Scaffold(
            drawerContent = {
                DrawerLayoutAtHome()
            },
            scaffoldState = scaffoldState,
            drawerBackgroundColor = Color.Transparent,
            backgroundColor = if (isSystemInDarkTheme()) Black80 else White80,
            drawerElevation = 0.dp
        ) {
            ConstraintLayout(
                modifier = Modifier.fillMaxSize()
            ) {
                val (topAppBar, button, list_view) = createRefs()
                TopAppBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(70.dp)
                        .background(
                            color = if (isSystemInDarkTheme()) Black80 else Purple500
                        )
                        .constrainAs(topAppBar) {
                            top.linkTo(parent.top)
                        },
                    title = {
                        AnimatedContent(
                            targetState = openSearch.value
                        ) {
                            if (it) {
                                MaterialTheme(
                                    colors = lightColors(
                                        primary = Color.White
                                    )
                                ) {
                                    Box(
                                        modifier = Modifier.fillMaxWidth(),
                                        contentAlignment = Alignment.CenterStart
                                    ) {
                                        OutlinedTextField(
                                            value = dName.value,
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(end = 12.dp)
                                                .clip(RoundedCornerShape(30.dp))
                                                .background(if (isSystemInDarkTheme()) BackSearchNight else BackSearch)
                                                .padding(start = 8.dp),
                                            onValueChange = { newText ->
                                                dName.value = newText
                                                viewModel.getListSearch(dName.value.text)
                                            },
                                            singleLine = false,
                                            colors = TextFieldDefaults.textFieldColors(
                                                backgroundColor = if (isSystemInDarkTheme()) BackSearchNight else BackSearch,
                                                cursorColor = White100,
                                                focusedIndicatorColor = Color.Transparent,
                                                unfocusedIndicatorColor = Color.Transparent,
                                                disabledIndicatorColor = Color.Transparent,
                                                leadingIconColor = Color.White
                                            ),
                                            placeholder = {
                                                Text(
                                                    text = "Search",
                                                    color = Black50,
                                                    fontSize = 16.sp,
                                                    fontFamily = FontFamily(Font(R.font.my_regular))
                                                )
                                            },
                                            textStyle = TextStyle(fontSize = 14.sp),
                                            trailingIcon = {
                                                if (dName.value.text != "") {
                                                    Image(
                                                        imageVector = Icons.Default.Close,
                                                        contentDescription = "close search",
                                                        colorFilter = ColorFilter.tint(White100),
                                                        modifier = Modifier.clickable {
                                                            dName.value = TextFieldValue("")
                                                            viewModel.getListSearch(dName.value.text)
                                                        }
                                                    )
                                                }
                                            }
                                        )
                                    }
                                }
                            } else {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    IconButton(
                                        onClick = {
                                            coroutineScope.launch {
                                                scaffoldState.drawerState.open()
                                            }
                                        },
                                        modifier = Modifier.fillMaxHeight()
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Menu,
                                            contentDescription = "Menu",
                                            tint = Color.White,
                                            modifier = Modifier
                                                .align(Alignment.CenterVertically)
                                                .clickable {
                                                    coroutineScope.launch {
                                                        scaffoldState.drawerState.open()
                                                    }
                                                }
                                        )
                                    }
                                    Text(
                                        "Exchange Rate",
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
                            openSearch.value = !openSearch.value
                            dName.value = TextFieldValue("")
                            viewModel.getListSearch("")
                        }) {
                            AnimatedContent(targetState = openSearch.value) { isOpen ->
                                if (isOpen) {
                                    Icon(
                                        imageVector = Icons.Default.Close,
                                        contentDescription = "Share",
                                        tint = Color.White
                                    )
                                } else {
                                    Icon(
                                        imageVector = Icons.Default.Search,
                                        contentDescription = "Share",
                                        tint = Color.White
                                    )
                                }
                            }

                        }
                    },
                    backgroundColor = Color.Transparent,
                    elevation = 0.dp
                )

                Scaffold(
                    modifier = Modifier
                        .constrainAs(list_view) {
                            top.linkTo(topAppBar.bottom)
                        }
                        .fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(bottom = 70.dp)
                    ) {
                        ListScreen(loginState, navController, openSheet, rateItemCurrent)
                    }
                }
            }
        }
    }

}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ListScreen(
    names: List<RateItemData>,
    navController: NavHostController,
    openSheet: ModalBottomSheetState,
    rateItemCurrent: MutableState<RateItemData>
) {
    LazyColumn(modifier = Modifier.padding()) {
        items(names) { names ->
            ItemExchange(name = names, navController, openSheet, rateItemCurrent)
        }
    }
}

sealed class MenuAction(
    @StringRes val label: Int,
    @DrawableRes val icon: Int
) {
    object Share : MenuAction(R.string.share, R.drawable.ic_share)
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun presentDialog(openDialog: HomeViewModel, openSheet: ModalBottomSheetState) {
    val context = LocalContext.current
    if (openDialog.openDialog.value) {
        AlertDialog(
            onDismissRequest = {
                openDialog.hideDialog()
            },
            title = {
                Text(
                    text = "Dialog",
                    fontFamily = FontFamily(Font(R.font.my_bold))
                )
            },
            text = {
                Text(
                    "Dasturdan chiqmoqchimisz",
                    fontFamily = FontFamily(Font(R.font.my_regular))
                )
            },
            shape = RoundedCornerShape(28.dp),
            confirmButton = {
                Text(
                    text = "Ok", modifier = Modifier
                        .padding(bottom = 16.dp, end = 16.dp)
                        .clickable {
                            (context as ComponentActivity).finish()
                        },
                    fontFamily = FontFamily(Font(R.font.my_regular))
                )
            },
            dismissButton = {
                Text(
                    text = "Cancel", modifier = Modifier
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

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BottomSheet(bottomSheetScaffoldState: ModalBottomSheetState) {
    val coroutineScope = rememberCoroutineScope()
    ModalBottomSheetLayout(
        modifier = Modifier.fillMaxSize(),
        sheetState = bottomSheetScaffoldState,
        sheetShape = Shapes.large,
        sheetContent = {
            Button(
                onClick = {
                    coroutineScope.launch {
                        bottomSheetScaffoldState.hide()
                    }
                }
            ) {

            }
        },
    ) {
        Text(text = "Hello")
    }
}