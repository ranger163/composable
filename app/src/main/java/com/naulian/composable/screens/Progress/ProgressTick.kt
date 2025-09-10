package com.naulian.composable.screens.Progress

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

/**
 * A visually enhanced, clickable progress stepper.
 * Features animations, elevation, and a modern design.
 *
 * @param totalSteps total number of steps
 * @param currentStep currently active step (1-based index)
 * @param modifier Modifier for custom styling
 * @param activeColor color for completed/active steps
 * @param inactiveColor color for upcoming steps
 * @param onStepClick callback when a step is clicked
 */
@Composable
fun ProgressTick(
    totalSteps: Int,
    currentStep: Int,
    modifier: Modifier = Modifier,
    activeColor: Color = Color(0xFF4CAF50),
    inactiveColor: Color = Color.LightGray,
    onStepClick: (Int) -> Unit
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        repeat(totalSteps) { index ->
            val step = index + 1
            val isActive = step <= currentStep

            // Animated properties for a dynamic feel
            val color by animateColorAsState(
                targetValue = if (isActive) activeColor else inactiveColor,
                animationSpec = tween(durationMillis = 300)
            )
            val elevation by animateDpAsState(
                targetValue = if (step == currentStep) 8.dp else 0.dp,
                animationSpec = tween(durationMillis = 300)
            )

            // Circle tick with animation and elevation
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .shadow(elevation, CircleShape) // Adds a shadow to the active/current step
                    .background(color)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null, // Removes the default ripple to create a cleaner look
                        onClick = { onStepClick(step) }
                    )
            ) {
                val textColor by animateColorAsState(
                    targetValue = if (isActive) Color.White else Color.Black,
                    animationSpec = tween(durationMillis = 300)
                )

                Text(
                    text = if (isActive) "✓" else step.toString(),
                    color = textColor,
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                )
            }

            // Connector line (except the last one)
            if (step != totalSteps) {
                val lineColor by animateColorAsState(
                    targetValue = if (step < currentStep) activeColor else inactiveColor,
                    animationSpec = tween(durationMillis = 300)
                )

                Box(
                    modifier = Modifier
                        .height(3.dp) // Slightly thicker line
                        .weight(1f)
                        .background(lineColor)
                )
            }
        }
    }
}

