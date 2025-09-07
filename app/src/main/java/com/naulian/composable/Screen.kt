package com.naulian.composable

import kotlinx.serialization.Serializable

sealed interface Screen {

    @Serializable
    data object Home : Screen

    @Serializable
    data object Neumorphic : Screen

    @Serializable
    data object GridBackground : Screen

    @Serializable
    data object CorneredBox : Screen

    @Serializable
    data object RatingStars : Screen

    @Serializable
    data object Second : Screen

    @Serializable
    data object ParallaxCardStack : Screen
}