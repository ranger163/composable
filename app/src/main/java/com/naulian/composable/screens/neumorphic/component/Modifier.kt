package com.naulian.composable.screens.neumorphic.component

import androidx.compose.foundation.background
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.draw.innerShadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import com.naulian.modify.White


fun Modifier.neumorphicRound(
    shape: Shape,
    shadowPadding: Dp,
    light: Color = NeumorphicLight,
    shadow: Color = NeumorphicDark
) = innerShadow(
    shape = shape,
    shadow = Shadow(
        radius = shadowPadding,
        color = light,
        offset = DpOffset(x = shadowPadding, y = shadowPadding)
    )
).innerShadow(
    shape = shape,
    shadow = Shadow(
        radius = shadowPadding,
        color = shadow,
        offset = DpOffset(x = -shadowPadding, y = -shadowPadding)
    )
)

fun Modifier.neumorphicDown(
    shape: Shape,
    shadowPadding: Dp,
    light: Color = NeumorphicLight,
    shadow: Color = NeumorphicDark
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

fun Modifier.neumorphicUp(
    shape: Shape,
    shadowPadding: Dp,
    light: Color = NeumorphicLight,
    shadow: Color = NeumorphicDark
) = dropShadow(
    shape = shape,
    shadow = Shadow(
        radius = shadowPadding,
        color = light,
        offset = DpOffset(x = -shadowPadding, y = -shadowPadding)
    )
).dropShadow(
    shape = shape,
    shadow = Shadow(
        radius = shadowPadding,
        color = shadow,
        offset = DpOffset(x = shadowPadding, y = shadowPadding)
    )
).background(White, shape)