package com.naulian.composable.acc.pulse

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun PulseAnimation(modifier: Modifier = Modifier) {
    val infiniteTransition = rememberInfiniteTransition()
    val pulse by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = 800
                1.0f at 0 using LinearEasing
                1.2f at 400 using LinearEasing
                1.0f at 800 using LinearEasing
            },
            repeatMode = RepeatMode.Restart
        )
    )

    Icon(
        imageVector = Icons.Filled.Favorite,
        contentDescription = "Heart",
        tint = Color(0xFFFF0000),
        modifier = modifier
            .size(64.dp)
            .scale(pulse)
    )
}