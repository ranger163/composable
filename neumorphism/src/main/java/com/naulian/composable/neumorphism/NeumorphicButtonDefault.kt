package com.naulian.composable.neumorphism

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp

object NeumorphicButtonDefaults {

    private val ButtonHorizontalPadding = 24.dp
    private val ButtonVerticalPadding = 8.dp


    val ContentPadding =
        PaddingValues(
            start = ButtonHorizontalPadding,
            top = ButtonVerticalPadding,
            end = ButtonHorizontalPadding,
            bottom = ButtonVerticalPadding
        )

    private val ButtonWithIconHorizontalStartPadding = 16.dp

    val ButtonWithIconContentPadding =
        PaddingValues(
            start = ButtonWithIconHorizontalStartPadding,
            top = ButtonVerticalPadding,
            end = ButtonHorizontalPadding,
            bottom = ButtonVerticalPadding
        )

    val MinWidth = 58.dp

    val MinHeight = 48.dp

    val IconSize = 18.dp

    val IconSpacing = 8.dp

    val shape: Shape
        @Composable get() = CircleShape

    @Composable
    fun buttonColors() = ButtonColors(
        containerColor = MaterialTheme.colorScheme.surfaceContainer,
        contentColor = MaterialTheme.colorScheme.onSurface,
        disabledContainerColor = MaterialTheme.colorScheme.surfaceContainer.copy(alpha = 0.12f),
        disabledContentColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
    )
}