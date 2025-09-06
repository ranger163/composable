package com.naulian.composable.screens.box

import androidx.compose.runtime.Composable
import com.naulian.composable.LocalNavController

@Composable
fun CorneredBoxScreen() {
    val navController = LocalNavController.current

    CorneredBoxScreenUI(
        onBack = { navController.navigateUp() }
    )
}