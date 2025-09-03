package com.naulian.composable.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.naulian.composable.screens.background.gridBackground
import com.naulian.composable.screens.neumorphic.NeuMorphicDown
import com.naulian.composable.screens.neumorphic.NeuMorphicUP
import com.naulian.modify.ExperimentalModifyApi

sealed interface HomeUIEvent {
    data object Neumorphic : HomeUIEvent
    data object GridBackground : HomeUIEvent
    data object Back : HomeUIEvent
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalModifyApi::class)
@Composable
fun HomeScreenUI(
    uiState: HomeUIState = HomeUIState(),
    uiEvent: (HomeUIEvent) -> Unit = {}
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = uiState.title) },
            )
        }
    ) { screenPadding ->
        LazyColumn(
            modifier = Modifier.padding(screenPadding)
        ) {
            item {
                Column(
                    modifier = Modifier
                        .clickable {
                            uiEvent(HomeUIEvent.Neumorphic)
                        }
                        .padding(20.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        NeuMorphicUP(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxWidth()
                                .aspectRatio(1f),
                            contentAlignment = Alignment.Center,
                            lightColor = MaterialTheme.colorScheme.surfaceBright,
                            shadowColor = MaterialTheme.colorScheme.surfaceDim
                        )

                        NeuMorphicDown(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxWidth()
                                .aspectRatio(1f),
                            contentAlignment = Alignment.Center,
                            lightColor = MaterialTheme.colorScheme.surfaceBright,
                            shadowColor = MaterialTheme.colorScheme.surfaceDim
                        )
                    }

                    ListItemText(title = "Neumorphism", createdBy = "Naulian")
                }
            }

            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .gridBackground(
                            color = MaterialTheme.colorScheme.tertiary,
                            lineColor = MaterialTheme.colorScheme.surfaceDim
                        )
                        .clickable {
                            uiEvent(HomeUIEvent.GridBackground)
                        }
                        .padding(20.dp)
                ) {
                    ListItemText(title = "Grid Background", createdBy = "Naulian")
                }
            }
        }
    }
}

@Composable
fun ColumnScope.ListItemText(
    title: String,
    createdBy: String,
) {
    Text(
        text = title,
        style = MaterialTheme.typography.headlineMedium,
        color = MaterialTheme.colorScheme.onBackground
    )
    Text(
        text = "Created by $createdBy",
        style = MaterialTheme.typography.bodyLarge,
        color = MaterialTheme.colorScheme.onBackground
    )
}