package com.naulian.composable.core

import kotlinx.serialization.Serializable

sealed interface Screen {

    @Serializable
    data object Home : Screen

    @Serializable
    data object StaticCC : Screen

    @Serializable
    data object InteractiveCC : Screen

    @Serializable
    data object AnimatedCC : Screen

    @Serializable
    data object Neumorphism : Screen

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

    @Serializable
    data object Carousel3DStack : Screen

    @Serializable
    data object Progress : Screen

    @Serializable
    data object BottomBar : Screen

    @Serializable
    data object CalenderTopBar : Screen

    @Serializable
    data object AnimatedInteraction : Screen

    @Serializable
    data object GlassDashboard : Screen

    @Serializable
    data object MovieTicket : Screen
}