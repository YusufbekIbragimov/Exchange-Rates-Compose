package uz.yusufbekibragimov.valyutauz.screens.components

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.PriceChange
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextRange
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
    var inputValueState by remember { mutableStateOf(value = TextFieldValue()) }
    val inputValue = inputValueState.copy(text = inputValueState.text)
    val focusRequester = remember { FocusRequester() }

    val coroutineScope = rememberCoroutineScope()

    val decimalFormat =
        DecimalFormat("###,###,###,###,###,###,###,###,###,###,###,###,###,###,###,###,###,###,###")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 100.dp)
            .clip(RoundedCornerShape(topEnd = 30.dp, topStart = 30.dp))
            .background(color = if (isSystemInDarkTheme()) BackSheet else Color.White)
    ) {
        Text(
            text = item.ccyNmUZ ?: "",
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            fontFamily = FontFamily(Font(R.font.my_bold)),
            fontSize = 24.sp,
            textAlign = TextAlign.Center,
            color = if (isSystemInDarkTheme()) Color.White else Color.Black
        )

        Text(
            text = "Input value (${if (isChange.value) "UZS" else item.ccy})",
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 6.dp, horizontal = 16.dp),
            fontFamily = FontFamily(Font(R.font.my_regular)),
            fontSize = 18.sp,
            textAlign = TextAlign.Start,
            color = if (isSystemInDarkTheme()) Color.White else Color.Black
        )
        MaterialTheme(
            colors = if (isSystemInDarkTheme()) darkColors(primary = Color.White) else lightColors(
                primary = Color.Black
            )
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(vertical = 8.dp, horizontal = 16.dp),
                value = if (inputValueState.text == "") {
                    ""
                } else {
                    decimalFormat.format(
                        inputValue.text.replace(("[^\\d.]").toRegex(), "").toDouble()
                    )
                },
                keyboardActions = KeyboardActions {
                    focusRequester.requestFocus()
                },
                label = {
                    Text(
                        text = "Input amount",
                        color = Black50,
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.my_regular))
                    )
                },
                onValueChange = { newtext ->
                    val text =
                        decimalFormat.format(newtext.replace(("[^\\d.]").toRegex(), "").toDouble())
                    Log.d("RRR", "SheetContent: newtext = $text")
                    inputValueState = TextFieldValue(text)

                    /* if (newtext.isEmpty()) {
                         inputValue.value = newtext
                     } else {
                         inputValue.value = when (newtext.toDoubleOrNull()) {
                             null -> inputValue.value //old value
                             else -> newtext //new value
                         }
                     }*/
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                visualTransformation = MoneyVisualTransformation()
            )
        }

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
                color = if (isSystemInDarkTheme()) Color.White else Color.Black,
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
                    tint = Color.Black,
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                )
            }
        }

        MaterialTheme(
            colors = if (isSystemInDarkTheme()) darkColors(primary = Color.White) else lightColors(
                primary = Color.Black
            )
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(vertical = 8.dp, horizontal = 16.dp),
                value = TextFieldValue(
                    "${
                        decimalFormat.format(
                            if (inputValue.text == "" || inputValue == null) {
                                "0".toDouble()
                            } else {
                                if (isChange.value) {
                                    inputValue.text.replace(("[^\\d.]").toRegex(), "")
                                        .toDouble() / (item.rate?.toDouble()!!)
                                } else {
                                    if (item.rate != null) {
                                        inputValue.text.replace(("[^\\d.]").toRegex(), "")
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
                        color = Black50,
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.my_regular))
                    )
                },
                onValueChange = { newtext ->
                    /*if (newtext.text.isEmpty()) {
                        outputValue.value = newtext
                    } else {
                        outputValue.value = when (newtext.text.toDoubleOrNull()) {
                            null -> outputValue.value //old value
                            else -> newtext //new value
                        }
                    }*/
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
        }

    }

    if (!openSheet.isVisible) {
        inputValueState = TextFieldValue("1")
    }

}

class MoneyVisualTransformation() : VisualTransformation {
    companion object {
        /**
         * The offset map used for identity mapping.
         */
        val Identity2 = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int = offset
            override fun transformedToOriginal(offset: Int): Int = offset+1
        }
    }
    override fun filter(text: AnnotatedString): TransformedText {
        val d = TransformedText(
            text,
            Identity2
        )
        Log.d("RRR", "filter: ${d.offsetMapping.originalToTransformed(text.length)} 2-> ${d.offsetMapping.transformedToOriginal(text.length)}  text = ${d.text}")
        return d
    }
}