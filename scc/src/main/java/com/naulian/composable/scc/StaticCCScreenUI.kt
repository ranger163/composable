package com.naulian.composable.scc

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.naulian.composable.core.component.ComposableTopAppBar
import com.naulian.composable.core.component.ListItemText
import com.naulian.modify.ExperimentalModifyApi
import com.naulian.modify.columnItem
import com.naulian.neumorphic.NeumorphicDownHorizontalDivider

sealed interface SccUIEvent {
    data object Back : SccUIEvent
    data object Neumorphism : SccUIEvent
    data object GridBackground : SccUIEvent
    data object CorneredBox : SccUIEvent
    data object MovieTicket: SccUIEvent
    data object GlassCard: SccUIEvent
}

@OptIn(ExperimentalModifyApi::class, ExperimentalMaterial3Api::class)
@Composable
fun StaticCCScreenUI(
    uiEvent: (SccUIEvent) -> Unit = {}
) {
    Scaffold(
        topBar = {
            ComposableTopAppBar(
                title = "Static Components",
                onBack = { uiEvent(SccUIEvent.Back) }
            )
        }
    ) { scaffoldPadding ->
        LazyColumn(
            modifier = Modifier.padding(scaffoldPadding)
        ) {
            columnItem {
                NeumorphicDownHorizontalDivider()
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { uiEvent(SccUIEvent.Neumorphism) }
                        .padding(20.dp)
                ) {
                    ListItemText(title = "Neumorphism", contributor = "Naulian")
                }
                NeumorphicDownHorizontalDivider()

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { uiEvent(SccUIEvent.GridBackground) }
                        .padding(20.dp)
                ) {
                    ListItemText(title = "Grid Background", contributor = "Naulian")
                }
                NeumorphicDownHorizontalDivider()

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { uiEvent(SccUIEvent.CorneredBox) }
                        .padding(20.dp)
                ) {
                    ListItemText(title = "Cornered Box", contributor = "Naulian")
                }
                NeumorphicDownHorizontalDivider()

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { uiEvent(SccUIEvent.MovieTicket) }
                        .padding(20.dp)
                ) {
                    ListItemText(title = "Movie Ticket", contributor = "Prashant Panwar")
                }
                NeumorphicDownHorizontalDivider()

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { uiEvent(SccUIEvent.GlassCard) }
                        .padding(20.dp)
                ) {
                    ListItemText(title = "Glass Card", contributor = "Shree Bhargav R K")
                }
                NeumorphicDownHorizontalDivider()
            }
        }
    }
}