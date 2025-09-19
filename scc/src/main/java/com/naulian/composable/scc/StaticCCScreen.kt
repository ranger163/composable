package com.naulian.composable.scc

import androidx.compose.runtime.Composable
import com.naulian.composable.core.LocalNavController
import com.naulian.composable.core.Screen

@Composable
fun StaticCCScreen() {
    val navController = LocalNavController.current

    StaticCCScreenUI{
        when(it){
            SccUIEvent.CorneredBox -> navController.navigate(Screen.CorneredBox)
            SccUIEvent.GridBackground -> navController.navigate(Screen.GridBackground)
            SccUIEvent.Neumorphism -> navController.navigate(Screen.Neumorphism)
            SccUIEvent.Back -> navController.navigateUp()
            SccUIEvent.MovieTicket -> navController.navigate(Screen.MovieTicket)
            SccUIEvent.GlassCard -> navController.navigate(Screen.GlassCard)
        }
    }
}