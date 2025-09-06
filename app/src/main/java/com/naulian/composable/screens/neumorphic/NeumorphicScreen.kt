package com.naulian.composable.screens.neumorphic

import androidx.compose.runtime.Composable
import com.naulian.composable.LocalNavController

@Composable
fun NeumorphicScreen() {
    val navController = LocalNavController.current

    NeumorphicScreenUI(
        onBack = { navController.navigateUp() }
    )
}