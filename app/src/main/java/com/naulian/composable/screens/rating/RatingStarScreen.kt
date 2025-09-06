package com.naulian.composable.screens.rating

import androidx.compose.runtime.Composable
import com.naulian.composable.LocalNavController

@Composable
fun RatingStarsScreen() {
    val navController = LocalNavController.current

    RatingStarScreenUI(
        onBack = { navController.navigateUp() }
    )
}