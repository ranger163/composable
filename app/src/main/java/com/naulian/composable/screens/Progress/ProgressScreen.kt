package com.naulian.composable.screens.Progress

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Composable
fun ProgressScreen() {
    var currentStep by remember { mutableStateOf(1) }
    val totalSteps = 5

    // A list of example content for each step
    val stepContent = remember {
        listOf(
            "Welcome! This is the first step of your journey.",
            "Great job! You've completed step one. Let's move on to the next task.",
            "You're halfway there! This step involves some important decisions.",
            "Final stretch! You're almost at the end.",
            "Congratulations! You have successfully completed all steps."
        )
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background // Using Material Theme background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Header
            Text(
                text = "Multi-Step Process",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(top = 16.dp, bottom = 48.dp)
            )

            // Progress bar with ticks
            ProgressTick(
                totalSteps = totalSteps,
                currentStep = currentStep,
                onStepClick = { step ->
                    currentStep = step
                }
            )

            Spacer(modifier = Modifier.height(48.dp))

            // Animated content based on the current step
            Crossfade(targetState = currentStep, animationSpec = tween(500)) { targetStep ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp), // Fixed height for consistent layout
                    contentAlignment = Alignment.Center
                ) {
                    // Display different content for each step
                    Text(
                        text = stepContent.getOrElse(targetStep - 1) { "" },
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(16.dp),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f)) // Pushes button to the bottom

            // Navigation button
            Button(
                onClick = {
                    if (currentStep < totalSteps) {
                        currentStep++
                    } else {
                        // Reset to the start or show a completion message
                        currentStep = 1
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = if (currentStep < totalSteps) "Next Step" else "Start Over")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewProgressScreen() {
    ProgressScreen()
}