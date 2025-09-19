package com.naulian.composable.icc.parallaxCards


import androidx.compose.runtime.Composable
import com.naulian.composable.core.LocalNavController

@Composable
fun ParallaxCardStackScreen() {
    val navController = LocalNavController.current

    ParallaxCardStackScreenUI(
        onBack = { navController.navigateUp() }
    )
}
