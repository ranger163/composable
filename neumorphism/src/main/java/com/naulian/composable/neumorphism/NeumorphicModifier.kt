package com.naulian.composable.neumorphism

import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.draw.innerShadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset


@Composable
fun Modifier.neumorphicUp(
    shape: Shape,
    shadowPadding: Dp,
    color: Color = MaterialTheme.colorScheme.background,
    light: Color = MaterialTheme.colorScheme.surfaceBright,
    shadow: Color = MaterialTheme.colorScheme.surfaceDim
): Modifier {
    return background(
        color = color,
        shape = shape
    )
        .innerShadow(
            shape = shape,
            shadow = Shadow(
                radius = shadowPadding,
                color = light,
                offset = DpOffset(x = shadowPadding, y = shadowPadding)
            )
        )
        .innerShadow(
            shape = shape,
            shadow = Shadow(
                radius = shadowPadding,
                color = shadow,
                offset = DpOffset(x = -shadowPadding, y = -shadowPadding)
            )
        )
}

@Composable
fun Modifier.neumorphicDown(
    shape: Shape,
    shadowPadding: Dp,
    light: Color = MaterialTheme.colorScheme.surfaceBright,
    shadow: Color = MaterialTheme.colorScheme.surfaceDim
) = innerShadow(
    shape = shape,
    shadow = Shadow(
        radius = shadowPadding,
        color = light,
        offset = DpOffset(x = -shadowPadding, y = -shadowPadding)
    )
).innerShadow(
    shape = shape,
    shadow = Shadow(
        radius = shadowPadding,
        color = shadow,
        offset = DpOffset(x = shadowPadding, y = shadowPadding)
    )
)

@Composable
fun Modifier.neumorphicUp2(
    shape: Shape,
    shadowPadding: Dp,
    light: Color = MaterialTheme.colorScheme.surfaceBright,
    shadow: Color = MaterialTheme.colorScheme.surfaceDim,
    backgroundColor: Color = MaterialTheme.colorScheme.surfaceContainer,
) = dropShadow(
    shape = shape,
    shadow = Shadow(
        radius = shadowPadding,
        color = light,
        offset = DpOffset(x = -shadowPadding, y = -shadowPadding)
    )
)
    .dropShadow(
        shape = shape,
        shadow = Shadow(
            radius = shadowPadding,
            color = shadow,
            offset = DpOffset(x = shadowPadding, y = shadowPadding)
        )
    )
    .background(backgroundColor, shape)


@Preview
@Composable
private fun NeumorphicPreview() {

}