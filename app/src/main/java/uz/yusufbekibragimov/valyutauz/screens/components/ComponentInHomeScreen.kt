package uz.yusufbekibragimov.valyutauz.screens.components

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PriceChange
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import uz.yusufbekibragimov.valyutauz.R
import uz.yusufbekibragimov.valyutauz.data.model.RateItemData
import uz.yusufbekibragimov.valyutauz.ui.theme.BackSheet
import uz.yusufbekibragimov.valyutauz.ui.theme.Black50
import uz.yusufbekibragimov.valyutauz.ui.theme.Black80
import uz.yusufbekibragimov.valyutauz.ui.theme.White80
import java.text.DecimalFormat

/**
 * Created by Ibragimov Yusufbek
 * Date: 04.03.2022
 * Project: ValyutaUz
 **/


@OptIn(ExperimentalMaterialApi::class, androidx.compose.ui.ExperimentalComposeUiApi::class)
@Composable
fun SheetContent(item: RateItemData, openSheet: ModalBottomSheetState) {

    val isChange = remember { mutableStateOf(false) }
    var inputValueState by remember { mutableStateOf(TextFieldValue()) }

    val decimalFormat = DecimalFormat("###,###,###,###,###,###,###,###,###,###")
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 100.dp)
            .clip(RoundedCornerShape(topEnd = 30.dp, topStart = 30.dp))
            .background(color = colors.onBackground)
    ) {
        Text(
            text = item.ccyNmUZ ?: "",
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            fontFamily = FontFamily(Font(R.font.my_bold)),
            fontSize = 24.sp,
            textAlign = TextAlign.Center,
            color = colors.onSurface
        )

        Text(
            text = "Input value (${if (isChange.value) "UZS" else item.ccy})",
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 6.dp, horizontal = 16.dp),
            fontFamily = FontFamily(Font(R.font.my_regular)),
            fontSize = 18.sp,
            textAlign = TextAlign.Start,
            color = colors.onSurface
        )

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(vertical = 8.dp, horizontal = 16.dp),
            value = inputValueState,
            label = {
                Text(
                    text = "Input amount",
                    color = colors.background,
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.my_regular))
                )
            },
            onValueChange = { newtext ->
                inputValueState = newtext
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            visualTransformation = DecimalVisualTransformation(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                textColor = colors.onSurface,
                focusedBorderColor = colors.onSurface
            )
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Result (${if (isChange.value) item.ccy else "UZS"})",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp, horizontal = 16.dp)
                    .weight(1f),
                fontFamily = FontFamily(Font(R.font.my_regular)),
                fontSize = 18.sp,
                textAlign = TextAlign.Start,
                color = colors.onSurface,
            )

            IconButton(
                onClick = {
                    coroutineScope.launch {
                        isChange.value = !isChange.value
                    }
                },
                modifier = Modifier
                    .wrapContentHeight()
                    .padding(end = 16.dp, top = 12.dp, bottom = 12.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.PriceChange,
                    contentDescription = "Price changer icon",
                    tint = colors.onSurface,
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                )
            }
        }
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(vertical = 8.dp, horizontal = 16.dp),
            value = TextFieldValue(
                "${
                    decimalFormat.format(
                        if (inputValueState.text == "" || inputValueState == null) {
                            "0".toDouble()
                        } else {
                            if (isChange.value) {
                                inputValueState.text.replace(("[^\\d.]").toRegex(), "")
                                    .toDouble() / (item.rate?.toDouble()!!)
                            } else {
                                if (item.rate != null) {
                                    inputValueState.text.replace(("[^\\d.]").toRegex(), "")
                                        .toDouble() * (item.rate.toDouble())
                                } else {
                                    0.0
                                }
                            }
                        }
                    )
                } ${if (isChange.value) item.ccy else "UZS"}"
            ),
            label = {
                Text(
                    text = "Output amount",
                    color = colors.background,
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.my_regular))
                )
            },
            onValueChange = {},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                textColor = colors.onSurface,
                focusedBorderColor = colors.onSurface
            )
        )
    }

    if (!openSheet.isVisible) {
        inputValueState = TextFieldValue("1")
    }

}

class DecimalVisualTransformation : VisualTransformation {
    private val df = DecimalFormat("###,###,###,###,###,###,###,###,###,###")
    override fun filter(text: AnnotatedString): TransformedText {
        return try {
            val numberValue = text.text.toLong()
            val formattedText = df.format(numberValue)
            TransformedText(text = AnnotatedString(formattedText), calcOffsetMapping(formattedText))
        } catch (error: Throwable) {
            TransformedText(text, OffsetMapping.Identity)
        }
    }

    private fun calcOffsetMapping(formattedText: String): OffsetMapping {
        return object : OffsetMapping {
            private val fromOriginal = mutableMapOf<Int, Int>()
            private val toOriginal = mutableMapOf<Int, Int>()

            init {
                var diff = 0
                formattedText.forEachIndexed { idx, ch ->
                    val pos = idx + 1
                    if (!ch.isDigit())
                        diff++
                    fromOriginal[pos] = pos + diff
                    toOriginal[pos + diff] = pos
                }
            }

            override fun originalToTransformed(offset: Int): Int = fromOriginal[offset] ?: 0

            override fun transformedToOriginal(offset: Int): Int = toOriginal[offset] ?: 0
        }
    }
}