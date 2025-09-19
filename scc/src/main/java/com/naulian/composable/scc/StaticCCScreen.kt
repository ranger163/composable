package com.naulian.composable.scc

import androidx.compose.runtime.Composable
import com.naulian.composable.core.LocalNavController
import com.naulian.composable.core.Screen

@Composable
fun StaticCCScreen() {
    val navController = LocalNavController.current

    StaticCCScreenUI{
        when(it){
            SccUIEvent.Back -> navController.navigateUp()
            is SccUIEvent.Navigate -> navController.navigate(it.route)
        }
    }
}