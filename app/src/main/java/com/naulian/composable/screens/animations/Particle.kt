package com.naulian.composable.screens.animations

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color

data class Particle(
    val position: Offset,
    val velocity: Offset,
    val size: Float,
    val color: Color
)