package com.naulian.composable.acc

import androidx.compose.runtime.Composable
import com.naulian.composable.core.LocalNavController
import com.naulian.composable.core.Screen

@Composable
fun AnimatedCCScreen() {
    val navController = LocalNavController.current

    AnimatedCCScreenUI{
        when(it){
            AccUIEvent.Back -> navController.navigateUp()
            AccUIEvent.TypingText -> navController.navigate(Screen.TypingText)
        }
    }
}