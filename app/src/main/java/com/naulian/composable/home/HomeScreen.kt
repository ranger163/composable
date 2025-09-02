package com.naulian.composable.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.naulian.composable.LocalNavController
import com.naulian.composable.Screen

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
            HomeUIEvent.Back -> navController.navigateUp()
            HomeUIEvent.Neumorphic -> navController.navigate(Screen.Neumorphic)
        }
    }
}