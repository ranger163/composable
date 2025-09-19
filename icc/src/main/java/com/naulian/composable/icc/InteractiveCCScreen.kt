package com.naulian.composable.icc

import androidx.compose.runtime.Composable
import com.naulian.composable.core.LocalNavController

@Composable
fun InteractiveCCScreen() {
    val navController = LocalNavController.current

    InteractiveCCScreenUI {
        when (it) {
            IccUIEvent.Back -> navController.navigateUp()
            is IccUIEvent.Navigate -> navController.navigate(it.route)
        }
    }
}