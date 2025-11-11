package com.example.swipelotto

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.ui.tooling.preview.Preview

import kotlin.math.roundToInt
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.swipelotto.viewmodel.LottoViewModel

@Composable
@Preview
fun App() {

    MaterialTheme {
        MainScreen()
    }

}

@Composable
fun MainScreen(
    lottoViewModel: LottoViewModel = viewModel { LottoViewModel() }
){

    Box(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.primaryContainer)
            .safeContentPadding()
            .fillMaxSize(),
    ) {

        val lottoUIState by lottoViewModel.uiState.collectAsState()
        println(lottoUIState)
        LottoNumberCompose(
            lottoUIState.lottoNumber.toCharArray(),
            lottoUIState.digitsWithScore
        )
        DraggableTextLowLevel(
            onDrag = {
                    x, y ->
                lottoViewModel.onDrag(x, y)
            }
        )
        Column(modifier = Modifier.align(Alignment.BottomCenter))
        {
            Text("*For Entertainment purpose only.")
        }

    }
}

@Composable
private fun LottoNumberCompose(
    digit: CharArray,
    lottoNumberWithAlpha: List<Int>,
)
{
    println(lottoNumberWithAlpha)

    Box(
        modifier = Modifier.fillMaxSize()
            .wrapContentSize(
                Alignment.Center, true
            )
    )
    {
        Text(
            buildAnnotatedString {

                lottoNumberWithAlpha.forEachIndexed { index, value ->
                    withStyle(
                        style = SpanStyle(
                            color = Color(0, 0, 0, value),
                            fontSize = 80.sp,
                            fontFamily = FontFamily.Monospace,
                            )
                    ) {
                        append(digit[index])
                    }
                }
            },
            overflow = TextOverflow.StartEllipsis,
            textAlign = TextAlign.Center, maxLines = 1
        )
    }
}

@Composable
private fun DraggableTextLowLevel(
    onDrag: (x:Float, y: Float) -> Unit
) {
    var offsetX by remember { mutableFloatStateOf(0f) }
    var offsetY by remember { mutableFloatStateOf(0f) }
    Box(
        modifier = Modifier.fillMaxSize()
            .pointerInput(
                Unit
            ) {
                detectTapGestures(
                    onTap = { offset ->
                        offsetX = offset.x
                        offsetY = offset.y
                    }
                )

            }
    ) {
        Box(
            Modifier
                .offset { IntOffset(offsetX.roundToInt(), offsetY.roundToInt()) }
                .background(Color.Blue)
                .size(50.dp)
                .pointerInput(Unit) {

                    detectDragGestures { change, dragAmount ->
                        change.consume()

                        onDrag(dragAmount.x, dragAmount.y)
                        offsetX += dragAmount.x
                        offsetY += dragAmount.y
                    }
                }
        )
    }
}

