package com.naulian.composable.neumorphism

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun NeumorphicHorizontalDivider(modifier: Modifier = Modifier, thickness: Dp = 4.dp) {

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(thickness)
            .neumorphicUp(
                shape = CircleShape,
                shadowPadding = if (thickness >= 4.dp) thickness / 4 else 1.dp
            )
    )
}

@Composable
fun NeumorphicVerticalDivider(
    modifier: Modifier = Modifier,
    thickness: Dp = 4.dp
) {

    Box(
        modifier = modifier
            .fillMaxHeight()
            .width(thickness)
            .neumorphicUp(
                shape = CircleShape,
                shadowPadding = if (thickness >= 4.dp) thickness / 4 else 1.dp
            )
    )
}

@Preview
@Composable
private fun NeumorphicDividerPreview() {
    NeumorphicPreviewSquare {
        NeumorphicHorizontalDivider(thickness = 8.dp)
        NeumorphicVerticalDivider(thickness = 8.dp)
    }
}