package com.naulian.composable.screens.neumorphic

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.innerShadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.naulian.composable.theme.ComposeTheme
import com.naulian.modify.Transparent


@Composable
internal fun NeumorphicBox(
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(20),
    contentPadding: Dp = 20.dp,
    firstColor: Color = Color.White,
    secondColor: Color = Color.LightGray,
    contentAlignment: Alignment = Alignment.TopStart,
    content: @Composable BoxScope.() -> Unit,
) {
    Box(
        modifier = modifier
            .background(Transparent, shape)
            .innerShadow(
                shape = shape,
                shadow = Shadow(
                    radius = contentPadding,
                    color = firstColor,
                    offset = DpOffset(x = contentPadding, y = contentPadding)
                )
            )
            .innerShadow(
                shape = shape,
                shadow = Shadow(
                    radius = contentPadding,
                    color = secondColor,
                    offset = DpOffset(x = -contentPadding, y = -contentPadding)
                )
            ),
        content = content,
        contentAlignment = contentAlignment
    )
}

@Composable
fun NeuMorphicUP(
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(20),
    contentPadding: Dp = 20.dp,
    lightColor: Color = Color.White,
    shadowColor: Color = Color.LightGray,
    contentAlignment: Alignment = Alignment.TopStart,
    content: @Composable BoxScope.() -> Unit = {},
) {
    NeumorphicBox(
        modifier = modifier,
        shape = shape,
        contentPadding = contentPadding,
        firstColor = lightColor,
        secondColor = shadowColor,
        contentAlignment = contentAlignment,
        content = content
    )
}

@Composable
fun NeuMorphicDown(
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(20),
    contentPadding: Dp = 20.dp,
    contentAlignment: Alignment = Alignment.TopStart,
    lightColor: Color = Color.White,
    shadowColor: Color = Color.LightGray,
    content: @Composable BoxScope.() -> Unit = {},
) {
    NeumorphicBox(
        modifier = modifier,
        shape = shape,
        contentPadding = contentPadding,
        firstColor = shadowColor,
        secondColor = lightColor,
        contentAlignment = contentAlignment,
        content = content
    )
}

@Preview
@Composable
private fun NeumorphicBoxPreview() {
    ComposeTheme {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background)
                .padding(20.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            NeuMorphicUP(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .aspectRatio(1f),
                contentAlignment = Alignment.Center
            )

            NeuMorphicDown(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .aspectRatio(1f),
                contentAlignment = Alignment.Center
            )
        }
    }
}

val neumorphicCode by lazy {
    """
            @Composable
            fun NeumorphicBox(
                modifier: Modifier = Modifier,
                shape: Shape = RoundedCornerShape(20),
                contentPadding: Dp = 20.dp,
                firstColor: Color = Color.White,
                secondColor: Color = Color.LightGray,
                contentAlignment: Alignment = Alignment.TopStart,
                content: @Composable BoxScope.() -> Unit,
            ) {
                Box(
                    modifier = modifier
                        .background(Transparent, shape)
                        .innerShadow(
                            shape = shape,
                            shadow = Shadow(
                                radius = contentPadding,
                                color = firstColor,
                                offset = DpOffset(x = contentPadding, y = contentPadding)
                            )
                        )
                        .innerShadow(
                            shape = shape,
                            shadow = Shadow(
                                radius = contentPadding,
                                color = secondColor,
                                offset = DpOffset(x = -contentPadding, y = -contentPadding)
                            )
                        ),
                    content = content,
                    contentAlignment = contentAlignment
                )
            }

            @Composable
            fun NeuMorphicUP(
                modifier: Modifier = Modifier,
                shape: Shape = RoundedCornerShape(20),
                contentPadding: Dp = 20.dp,
                lightColor: Color = Color.White,
                shadowColor: Color = Color.LightGray,
                contentAlignment: Alignment = Alignment.TopStart,
                content: @Composable BoxScope.() -> Unit = {},
            ) {
                NeumorphicBox(
                    modifier = modifier,
                    shape = shape,
                    contentPadding = contentPadding,
                    firstColor = lightColor,
                    secondColor = shadowColor,
                    contentAlignment = contentAlignment,
                    content = content
                )
            }

            @Composable
            fun NeuMorphicDown(
                modifier: Modifier = Modifier,
                shape: Shape = RoundedCornerShape(20),
                contentPadding: Dp = 20.dp,
                contentAlignment: Alignment = Alignment.TopStart,
                lightColor: Color = Color.White,
                shadowColor: Color = Color.LightGray,
                content: @Composable BoxScope.() -> Unit = {},
            ) {
                NeumorphicBox(
                    modifier = modifier,
                    shape = shape,
                    contentPadding = contentPadding,
                    firstColor = shadowColor,
                    secondColor = lightColor,
                    contentAlignment = contentAlignment,
                    content = content
                )
            }
        """.trimIndent()
}