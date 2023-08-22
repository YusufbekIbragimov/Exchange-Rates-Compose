package com.exchangerates.best.screens.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.unit.dp
import com.exchangerates.best.ui.theme.color1
import com.exchangerates.best.ui.theme.color2

@Composable
fun DrawerLayoutAtHome() {
    Column(
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