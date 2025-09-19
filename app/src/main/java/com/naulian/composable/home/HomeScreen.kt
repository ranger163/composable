package com.naulian.composable.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.naulian.composable.core.LocalNavController
import com.naulian.composable.core.Screen

@Composable
fun HomeScreen() {

    val navController = LocalNavController.current

    val viewModel: HomeViewModel = hiltViewModel()
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.event.collect {
            when (it) {
                HomeEvent.ToSecond -> navController.navigate(Screen.Second)
            }
        }
    }

    HomeScreenUI(uiState = state) {
        when (it) {
            HomeUIEvent.AnimatedCC -> navController.navigate(Screen.AnimatedCC)
            HomeUIEvent.InteractiveCC ->  navController.navigate(Screen.InteractiveCC)
            HomeUIEvent.Continue -> navController.navigate(Screen.StaticCC)
        }
    }
}