package com.naulian.composable.screens.shapes

import android.annotation.SuppressLint
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.asAndroidPath
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp

@SuppressLint("UnnecessaryComposedModifier")
fun Modifier.neumorphicShapeShadow(
    shape: Shape,
    lightShadowColor: Color,
    darkShadowColor: Color,
    lightOffset: Offset,
    darkOffset: Offset,
    elevation: Dp
): Modifier = composed {
    val density = LocalDensity.current
    val elevationPx = with(density) { elevation.toPx() }

    this.then(
        Modifier.drawBehind {
            // Ask the shape for its outline in current size/density/layoutDirection
            val outline = shape.createOutline(size, layoutDirection, this)

            val path = when (outline) {
                is Outline.Generic -> outline.path
                is Outline.Rounded -> Path().apply { addRoundRect(outline.roundRect) }
                is Outline.Rectangle -> Path().apply { addRect(outline.rect) }
            }

            drawIntoCanvas { canvas ->
                val lightPaint = android.graphics.Paint().apply {
                    isAntiAlias = true
                    color = lightShadowColor.toArgb()
                    setShadowLayer(
                        elevationPx,
                        lightOffset.x,
                        lightOffset.y,
                        lightShadowColor.copy(alpha = 0.5f).toArgb()
                    )
                }
                canvas.nativeCanvas.drawPath(path.asAndroidPath(), lightPaint)

                val darkPaint = android.graphics.Paint().apply {
                    isAntiAlias = true
                    color = darkShadowColor.toArgb()
                    setShadowLayer(
                        elevationPx,
                        darkOffset.x,
                        darkOffset.y,
                        darkShadowColor.copy(alpha = 0.5f).toArgb()
                    )
                }
                canvas.nativeCanvas.drawPath(path.asAndroidPath(), darkPaint)
            }
        }
    )
}
