package com.naulian.composable.screens.Progress

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

/**
 * ProgressTick Composable
 * A clickable progress tracker with ticks (✓ for completed, number for pending).
 * Visually enhanced with gradients, animations, and elevation.
 *
 * @param totalSteps total number of steps
 * @param currentStep currently active step (1-based index)
 * @param modifier Modifier for custom styling
 * @param activeStartColor start color for active gradient
 * @param activeEndColor end color for active gradient
 * @param inactiveColor color for upcoming steps
 * @param onStepClick callback when a step is clicked
 */
@Composable
fun ProgressTick(
    totalSteps: Int,
    currentStep: Int,
    modifier: Modifier = Modifier,
    activeStartColor: Color = Color(0xFF6dd5ed), // Bhopal Breeze - light blue
    activeEndColor: Color = Color(0xFF2193b0),   // Bhopal Breeze - darker blue
    inactiveColor: Color = Color(0xFFe0e0e0),    // Soft gray for inactive
    onStepClick: (Int) -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp), // Added padding to the row
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        repeat(totalSteps) { index ->
            val step = index + 1
            val isActive = step <= currentStep

            // Animated colors and size
            val animatedBackgroundColor by animateColorAsState(
                targetValue = if (isActive) activeStartColor else inactiveColor,
                animationSpec = tween(durationMillis = 300)
            )
            val animatedTextColor by animateColorAsState(
                targetValue = if (isActive) Color.White else Color.Black.copy(alpha = 0.7f),
                animationSpec = tween(durationMillis = 300)
            )
            val animatedElevation by animateDpAsState(
                targetValue = if (step == currentStep) 8.dp else 0.dp, // Elevate current step
                animationSpec = tween(durationMillis = 300)
            )
            val animatedSize by animateDpAsState(
                targetValue = if (step == currentStep) 40.dp else 36.dp, // Slightly larger current step
                animationSpec = tween(durationMillis = 300)
            )

            // Circle tick
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(animatedSize)
                    .clip(CircleShape)
                    .shadow(animatedElevation, CircleShape) // Apply shadow for elevation
                    .background(
                        brush = if (isActive) {
                            Brush.linearGradient(colors = listOf(activeStartColor, activeEndColor))
                        } else {
                            Brush.linearGradient(colors = listOf(inactiveColor, inactiveColor))
                        }
                    )
                    .clickable { onStepClick(step) }
            ) {
                Text(
                    text = "✓".takeIf { isActive } ?: step.toString(),
                    color = animatedTextColor,
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold) // Bolder text
                )
            }

            // Connector line (except last)
            if (step != totalSteps) {
                val animatedLineColor by animateColorAsState(
                    targetValue = if (isActive) activeEndColor else inactiveColor, // Line color matches end of active gradient
                    animationSpec = tween(durationMillis = 300)
                )
                Box(
                    modifier = Modifier
                        .height(3.dp) // Slightly thicker line
                        .weight(1f)
                        .background(animatedLineColor)
                )
            }
        }
    }
}

/**
 * Example demo preview (you can remove in production).
 */
@Composable
fun ProgressTickDemo() {
    var currentStep by remember { mutableStateOf(2) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5)) // Light background for contrast
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Progress Tracker",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        ProgressTick(
            totalSteps = 5,
            currentStep = currentStep,
            onStepClick = { step -> currentStep = step }
        )

        Spacer(modifier = Modifier.height(32.dp)) // Increased spacing
        Text(
            text = "Current Progress: Step $currentStep",
            style = MaterialTheme.typography.titleMedium,
            color = Color.DarkGray
        )
    }
}