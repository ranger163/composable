package com.naulian.composable.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.naulian.anhance.Lorem
import com.naulian.composable.screens.background.gridBackground
import com.naulian.composable.screens.neumorphic.NeuMorphicDown
import com.naulian.composable.screens.neumorphic.NeuMorphicUP
import com.naulian.modify.ExperimentalModifyApi
import com.naulian.modify.Gray
import com.naulian.modify.HugeIcons
import com.naulian.modify.White
import com.naulian.modify.columnItem

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
                        .background(Color(0xFFEEEEEE))
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
                            contentAlignment = Alignment.Center
                        )

                        NeuMorphicDown(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxWidth()
                                .aspectRatio(1f),
                            contentAlignment = Alignment.Center
                        )
                    }
                    Text(text = "Neumorphism", style = MaterialTheme.typography.headlineMedium)
                    Text(text = "Created by Naulian", style = MaterialTheme.typography.bodyLarge)
                }
            }

            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .gridBackground(White, shape = RoundedCornerShape(20.dp))
                        .clickable {
                            uiEvent(HomeUIEvent.GridBackground)
                        }
                        .padding(20.dp)
                ){
                    Text(text = "Grid Background", style = MaterialTheme.typography.headlineMedium)
                    Text(text = "Created by Naulian", style = MaterialTheme.typography.bodyLarge)
                }

            }
        }
    }
}