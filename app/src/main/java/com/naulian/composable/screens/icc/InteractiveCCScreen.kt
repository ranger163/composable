package com.naulian.composable.screens.icc

import androidx.compose.runtime.Composable
import com.naulian.composable.LocalNavController
import com.naulian.composable.Screen

@Composable
fun InteractiveCCScreen() {
    val navController = LocalNavController.current

    InteractiveCCScreenUI{
        when(it){
            IccUIEvent.ParallaxCardStack -> navController.navigate(Screen.ParallaxCardStack)
            IccUIEvent.RatingStars -> navController.navigate(Screen.RatingStars)
            IccUIEvent.CarouselCard -> navController.navigate(Screen.Carousel3DStack)
            IccUIEvent.Progress->navController.navigate(Screen.Progress)
            IccUIEvent.BottomBar -> navController.navigate(Screen.BottomBar)
            IccUIEvent.CalenderTopBar -> navController.navigate(Screen.CalenderTopBar)
            IccUIEvent.AnimatedInteractionScreen -> navController.navigate(Screen.AnimatedInteraction)
            IccUIEvent.GlassDashboardScreen -> navController.navigate(Screen.GlassDashboard)
            IccUIEvent.Back -> navController.navigateUp()
            IccUIEvent.Cards -> navController.navigate(Screen.Cards)
        }
    }
}