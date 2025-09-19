package com.naulian.composable.icc.step_progress

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

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
 * @param stepLabels optional labels for each step
 */
@Composable
fun ProgressTick(
    totalSteps: Int,
    currentStep: Int,
    modifier: Modifier = Modifier,
    activeColor: Color = Color(0xFF4CAF50),
    inactiveColor: Color = Color(0xFFE0E0E0),
    onStepClick: (Int) -> Unit,
    stepLabels: List<String> = emptyList()
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Progress circles and connecting lines
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            repeat(totalSteps) { index ->
                val step = index + 1
                val isActive = step <= currentStep
                val isCurrent = step == currentStep

                // Animated properties
                val color by animateColorAsState(
                    targetValue = if (isActive) activeColor else inactiveColor,
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessMedium
                    )
                )

                val scale by animateFloatAsState(
                    targetValue = if (isCurrent) 1.1f else 1.0f,
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessMedium
                    )
                )

                val elevation by animateDpAsState(
                    targetValue = if (isCurrent) 8.dp else if (isActive) 4.dp else 0.dp,
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessMedium
                    )
                )

                // Step circle
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(40.dp)
                        .scale(scale)
                        .clip(CircleShape)
                        .shadow(
                            elevation = elevation,
                            shape = CircleShape,
                            ambientColor = if (isActive) activeColor.copy(alpha = 0.3f) else Color.Transparent,
                            spotColor = if (isActive) activeColor.copy(alpha = 0.3f) else Color.Transparent
                        )
                        .background(
                            color = color,
                            shape = CircleShape
                        )
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                            onClick = { onStepClick(step) }
                        )
                ) {
                    val textColor by animateColorAsState(
                        targetValue = if (isActive) Color.White else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                        animationSpec = tween(durationMillis = 300)
                    )

                    Text(
                        text = if (isActive) "✓" else step.toString(),
                        color = textColor,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                    )
                }

                // Connecting line (except for the last step)
                if (step != totalSteps) {
                    val lineColor by animateColorAsState(
                        targetValue = if (step < currentStep) activeColor else inactiveColor,
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioLowBouncy,
                            stiffness = Spring.StiffnessLow
                        )
                    )

                    Box(
                        modifier = Modifier
                            .height(3.dp)
                            .weight(1f)
                            .padding(horizontal = 8.dp)
                            .clip(RoundedCornerShape(2.dp))
                            .background(lineColor)
                    )
                }
            }
        }

        // Step labels (if provided)
        if (stepLabels.isNotEmpty()) {
            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                repeat(totalSteps) { index ->
                    val step = index + 1
                    val isActive = step <= currentStep
                    val label = stepLabels.getOrNull(index) ?: "Step $step"

                    val labelColor by animateColorAsState(
                        targetValue = if (isActive) {
                            MaterialTheme.colorScheme.onSurface
                        } else {
                            MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                        },
                        animationSpec = tween(durationMillis = 300)
                    )

                    Box(
                        modifier = Modifier.weight(1f),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = label,
                            color = labelColor,
                            style = MaterialTheme.typography.bodySmall.copy(
                                fontWeight = if (isActive) FontWeight.Medium else FontWeight.Normal,
                                fontSize = 11.sp
                            ),
                            textAlign = TextAlign.Center,
                            maxLines = 2
                        )
                    }
                }
            }
        }
    }
}