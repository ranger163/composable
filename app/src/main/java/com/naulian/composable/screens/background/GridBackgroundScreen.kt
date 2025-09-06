package com.naulian.composable.screens.background

import androidx.compose.runtime.Composable
import com.naulian.composable.LocalNavController

@Composable
fun GridBackgroundScreen() {
    val navController = LocalNavController.current

    GridBackgroundScreenUI(
        onBack = { navController.navigateUp() }
    )
}