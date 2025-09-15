package com.naulian.composable.screens.cards

import androidx.compose.runtime.Composable
import com.naulian.composable.LocalNavController

@Composable
fun SwipeableCardsScreen() {
    val navController = LocalNavController.current

    SwipeableCardsScreenUI(
        onBack = { navController.navigateUp() }
    )
}