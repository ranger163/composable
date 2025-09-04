package com.naulian.composable.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.naulian.composable.R
import com.naulian.composable.screens.background.gridBackground
import com.naulian.composable.screens.box.CorneredBox
import com.naulian.composable.screens.neumorphic.NeuMorphicDown
import com.naulian.composable.screens.neumorphic.NeuMorphicUP
import com.naulian.composable.screens.rating.RatingStars
import com.naulian.modify.ExperimentalModifyApi
import com.naulian.modify.Gray
import com.naulian.modify.White
import com.naulian.modify.columnItem

sealed interface HomeUIEvent {
    data object Neumorphic : HomeUIEvent
    data object GridBackground : HomeUIEvent
    data object CorneredBox : HomeUIEvent
    data object RatingStars : HomeUIEvent
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
            columnItem(
                modifier = Modifier
                    .clickable {
                        uiEvent(HomeUIEvent.Neumorphic)
                    }
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
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

            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .gridBackground(
                            color = MaterialTheme.colorScheme.tertiary,
                            lineColor = MaterialTheme.colorScheme.surfaceDim
                        )
                        .clickable {
                            uiEvent(HomeUIEvent.GridBackground)
                        }
                        .padding(20.dp),
                ) {
                    ListItemText(title = "Grid Background", createdBy = "Naulian")
                }
            }

            item {
                CorneredBox(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.surface)
                        .padding(20.dp)
                        .fillMaxWidth(),
                    cornerColor = Gray,
                    contentPadding = PaddingValues(12.dp),
                    onClick = {
                        uiEvent(HomeUIEvent.CorneredBox)
                    }
                ) {
                    ListItemText(title = "Cornered Box", createdBy = "Naulian")
                }
            }

            columnItem(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable{
                        uiEvent(HomeUIEvent.RatingStars)
                    }
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                var ratingValue by remember { mutableIntStateOf(2) }

                RatingStars(
                    rating = ratingValue,
                    ratedStarIcon = painterResource(R.drawable.ic_star_filled),
                    unRatedStarIcon = painterResource(R.drawable.ic_star_outlined),
                    onRatingChange = {
                        ratingValue = it
                    },
                    iconSize = 48.dp
                )

                ListItemText(title = "Rating Stars", createdBy = "Naulian")
            }
        }
    }
}

@Composable
fun ListItemText(
    title: String,
    createdBy: String,
    modifier: Modifier = Modifier
) {
    Column(modifier) {
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
}