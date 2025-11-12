package com.example.swipelotto.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.key.Key.Companion.R
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import swipelotto.composeapp.generated.resources.Res
import kotlin.math.roundToInt
import swipelotto.composeapp.generated.resources.oak_bark
import swipelotto.composeapp.generated.resources.paw

@Preview
@Composable
fun MainScreen(
    lottoViewModel: LottoViewModel = viewModel { LottoViewModel() }
){
    val lottoUIState by lottoViewModel.uiState.collectAsState()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    ModalNavigationDrawer(

        drawerState = drawerState,
        gesturesEnabled = true,
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier.width(250.dp),
            ) {
                NavigationDrawerItem(
                    label = { Text(text = "Reset") },
                    selected = true,

                    onClick = {
                        lottoViewModel.reset()
                        scope.launch {
                            launch {
                                drawerState.apply {
                                close()
                            }
                            }
                            launch {
                                snackbarHostState.showSnackbar("Number reset!")
                            }


                        }
                    }
                )
                HorizontalDivider()

                Column(
                    modifier = Modifier.padding(16.dp)
                )
                {  Text("About แอพขูดหวย", fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(bottom = 16.dp))
                    Text("- จัดทำโดย Tomoh", modifier = Modifier.padding(bottom = 8.dp))
                    Text("- เครื่องมือ Kotlin Multiplatform", modifier = Modifier.padding(bottom = 8.dp))
                    Text("- Use in BangkokBeasts 2025", modifier = Modifier.padding(bottom = 8.dp))
                    Text("- For Entertainment purpose only. แต่ถ้าถูกหวยก็บอกกันด้วย", modifier = Modifier.padding(bottom = 8.dp))
                }

                // ...other drawer items
            }
        }
    ) {

        Box(
            modifier = Modifier
                .paint(
                    painter = painterResource(Res.drawable.oak_bark),
                    contentScale = ContentScale.Crop
                )
                .safeContentPadding()
                .fillMaxSize(),
        ) {
//           Column(modifier = Modifier.align(Alignment.TopStart)){
//                Button(
//                    onClick = {
//                        scope.launch {
//                            drawerState.apply {
//                                if (isClosed) open() else close()
//                            }
//                        }
//
//                    }
//                ) {
//                    Icon(
//                        Icons.Default.Home,
//                        contentDescription = "",
//                        tint = Color.White,
//                        )
//                }
//            }
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

//            Column(modifier = Modifier.align(Alignment.BottomCenter))
//            {
//
//                Text("*For Entertainment purpose only.")
//                Text("แต่ถ้าถูกหวยก็บอกกันด้วย")
//            }

        }
    }



//    val painter: Painter = painterResource()

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
                            fontSize = 90.sp,
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
    var offsetX by remember { mutableFloatStateOf(150f) }
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
        Image(
            painter = painterResource(Res.drawable.paw),
            modifier = Modifier
                .offset { IntOffset(offsetX.roundToInt(), offsetY.roundToInt()) }

                .size(200.dp)
                .pointerInput(Unit) {

                    detectDragGestures { change, dragAmount ->
                        change.consume()

                        onDrag(dragAmount.x, dragAmount.y)
                        offsetX += dragAmount.x
                        offsetY += dragAmount.y
                    }
                },
            contentDescription = "",
        )
    }
}
