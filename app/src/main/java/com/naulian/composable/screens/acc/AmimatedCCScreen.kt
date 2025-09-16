package com.naulian.composable.screens.acc

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.naulian.composable.LocalNavController

@Composable
fun AnimatedCCScreen(modifier: Modifier = Modifier) {
    val navController = LocalNavController.current

    AnimatedCCScreenUI()
}