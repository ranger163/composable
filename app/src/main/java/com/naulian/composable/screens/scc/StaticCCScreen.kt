package com.naulian.composable.screens.scc

import androidx.compose.runtime.Composable
import com.naulian.composable.LocalNavController
import com.naulian.composable.Screen

@Composable
fun StaticCCScreen() {
    val navController = LocalNavController.current

    StaticCCScreenUI{
        when(it){
            SccUIEvent.CorneredBox -> navController.navigate(Screen.CorneredBox)
            SccUIEvent.GridBackground -> navController.navigate(Screen.GridBackground)
            SccUIEvent.Neumorphism -> navController.navigate(Screen.Neumorphism)
        }
    }
}