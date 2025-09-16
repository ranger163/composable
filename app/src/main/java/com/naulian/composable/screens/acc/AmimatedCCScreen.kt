package com.naulian.composable.screens.acc

import androidx.compose.runtime.Composable
import com.naulian.composable.LocalNavController

@Composable
fun AnimatedCCScreen() {
    val navController = LocalNavController.current

    AnimatedCCScreenUI{
        when(it){
            AccUIEvent.Back -> navController.navigateUp()
        }
    }
}