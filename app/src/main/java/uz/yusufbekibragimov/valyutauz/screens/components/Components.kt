package uz.yusufbekibragimov.valyutauz.screens.components

import androidx.compose.animation.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import uz.yusufbekibragimov.valyutauz.R
import uz.yusufbekibragimov.valyutauz.navigation.nav_graph.EnterAnimation
import uz.yusufbekibragimov.valyutauz.ui.theme.color1
import uz.yusufbekibragimov.valyutauz.ui.theme.color2
import uz.yusufbekibragimov.valyutauz.ui.theme.color3

/**
 * Created by Ibragimov Yusufbek
 * Date: 01.03.2022
 * Project: ValyutaUz
 **/

@Composable
fun DrawerLayoutAtHome() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(end = 50.dp)
            .clip(RoundedCornerShape(topEnd = 30.dp, bottomEnd = 30.dp))
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        color1,
                        color2,
                    ),
                    tileMode = TileMode.Repeated
                )
            )
            .padding(16.dp),
    ) {

    }
}