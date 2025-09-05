package com.naulian.composable.screens.planets

import androidx.compose.ui.graphics.BlendMode
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.naulian.composable.theme.ComposeTheme
import com.naulian.modify.Black
import com.naulian.modify.White
import com.naulian.modify.Yellow
import com.naulian.modify.rememberIntState
import kotlin.math.abs

@Composable
fun Planets(modifier: Modifier = Modifier) {
    val slowInOutEasing = remember {
        CubicBezierEasing(0.4f, 0f, 0.6f, 1f)
    }

    var target by rememberIntState(0)

    val animation by animateFloatAsState(
        targetValue = target.toFloat(),
        animationSpec = tween(
            durationMillis = 4000,
            easing = slowInOutEasing
        ),
        finishedListener = {
            target = 1 - target //this flip 1 and 0
        }
    )

    LaunchedEffect(Unit) {
        target = 1
    }

    val planetRadius = remember { 12.dp }

    Box(
        modifier = modifier
            .size(320.dp)
            .drawWithContent {

                val radius = planetRadius.toPx()
                val planetSize = radius * 2
                val centerY = size.height / 2f

                val biggerScale = 1f + 0.4f * (1f - abs(animation - 0.5f) * 2f)
                val smallerScale = 1f - 0.4f * (1f - abs(animation - 0.5f) * 2f)
                val planetScale = if (target == 1) smallerScale else biggerScale


                val drawPlanet = {
                    with(drawContext.canvas.nativeCanvas) {
                        drawOval(
                            color = Color(0xFFFF6D00),
                            topLeft = Offset(
                                x = (size.width - planetSize) * animation,
                                y = centerY - radius
                            ),
                            size = Size(
                                width = planetSize * planetScale,
                                height = planetSize * planetScale
                            )
                        )

                        val checkPoint = saveLayer(null, null)
                        drawOval(
                            color = Color(0xFF632B00),
                            topLeft = Offset(
                                x = (size.width - planetSize) * animation,
                                y = centerY - radius
                            ),
                            size = Size(
                                width = planetSize * planetScale,
                                height = planetSize * planetScale
                            )
                        )

                        val shaderSize = planetSize * planetScale
                        drawOval(
                            color = Color(0xFFD50000),
                            topLeft = Offset(
                                x = (size.width - planetSize) * animation + radius,
                                y = centerY - radius
                            ),
                            size = Size(width = shaderSize, height = shaderSize),
                            blendMode = BlendMode.DstOut
                        )

                        restoreToCount(checkPoint)
                    }
                }

                if (target == 1) {
                    drawPlanet()
                }

                //draw sun
                drawContent()

                if (target == 0) {
                    drawPlanet()
                }
            }
    ) {
        //Sun
        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .zIndex(10f)
                .size(100.dp)
                .background(Yellow, CircleShape)
                .dropShadow(
                    shape = CircleShape,
                    shadow = Shadow(
                        radius = 20.dp,
                        color = White
                    )
                )

        )
    }
}

@Preview
@Composable
private fun PlanetsPreview() {
    ComposeTheme {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .background(Black),
            contentAlignment = Alignment.Center
        ) {
            Planets()
        }
    }
}