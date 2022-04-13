package uz.yusufbekibragimov.valyutauz.screens.analysis_Screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import uz.yusufbekibragimov.valyutauz.R

@Composable
fun AnalysisScreen(navController: NavHostController) {

    ContentAnalysis()

}

@Composable
fun ContentAnalysis() {

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
    }

}
