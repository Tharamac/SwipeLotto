package com.example.swipelotto

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
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
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.swipelotto.dataclass.NumberWithScore
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

import swipelotto.composeapp.generated.resources.Res
import swipelotto.composeapp.generated.resources.compose_multiplatform
import kotlin.math.roundToInt

@Composable
@Preview
fun App() {
    MaterialTheme {

        Box(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.primaryContainer)
                .safeContentPadding()
                .fillMaxSize(),
//            horizontalAlignment = Alignment.CenterHorizontally,
//            contentAlignment = TODO(),
//            propagateMinConstraints = TODO(),
        ) {
            LottoNumberCompose()
//            DraggableTextLowLevel()
        }
    }
}

@Composable
private fun LottoNumberCompose(

){
    var lottoNumber = 123456.toString();

    // make data class
    val lottoNumberWithAlpha = lottoNumber.toCharArray().map {
        it  ->
        NumberWithScore(digit = it, alpha = 255)
    }
Box(modifier = Modifier.fillMaxSize()

    .wrapContentSize(Alignment.Center, true
    )


)
{
    Text(buildAnnotatedString {
        lottoNumberWithAlpha.forEach { value ->
            withStyle(
                style = SpanStyle(
                    color = Color(0, 0, 0, value.alpha),
                    fontSize = 80.sp,
                    fontFamily = FontFamily.Monospace,

                )

            ) {
                append(value.digit)

            }
        }

    },
        overflow = TextOverflow.StartEllipsis,
        textAlign = TextAlign.Center, maxLines = 1)


}

}

@Composable
private fun DraggableTextLowLevel() {
    var offsetX by remember { mutableFloatStateOf(0f) }
    var offsetY by remember { mutableFloatStateOf(0f) }
    Box(modifier = Modifier.fillMaxSize()
        .pointerInput(
            Unit
        ){
            detectTapGestures (
                onTap = {
                    offset ->
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
                        println(dragAmount.x)
                        offsetX += dragAmount.x
                        offsetY += dragAmount.y
                    }
                }
        )
    }
}

