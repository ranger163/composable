package com.naulian.composable.screens.parallelCards


import androidx.compose.runtime.Composable
import com.naulian.composable.LocalNavController

@Composable
fun ParallaxCardStackScreen() {
    val navController = LocalNavController.current

    ParallaxCardStackScreenUI(
        onBack = { navController.navigateUp() }
    )
}
