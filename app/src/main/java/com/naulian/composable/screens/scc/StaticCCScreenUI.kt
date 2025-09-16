package com.naulian.composable.screens.scc

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.naulian.composable.component.ListItemText
import com.naulian.composable.neumorphism.NeumorphicDownHorizontalDivider
import com.naulian.modify.ExperimentalModifyApi
import com.naulian.modify.columnItem

sealed interface SccUIEvent {
    data object Neumorphism : SccUIEvent
    data object GridBackground : SccUIEvent
    data object CorneredBox : SccUIEvent
}

@OptIn(ExperimentalModifyApi::class, ExperimentalMaterial3Api::class)
@Composable
fun StaticCCScreenUI(
    uiEvent: (SccUIEvent) -> Unit = {}
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Static Composable Components") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
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
                    ListItemText(title = "Neumorphism", createdBy = "Naulian")
                }
                NeumorphicDownHorizontalDivider()

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { uiEvent(SccUIEvent.GridBackground) }
                        .padding(20.dp)
                ) {
                    ListItemText(title = "Grid Background", createdBy = "Naulian")
                }
                NeumorphicDownHorizontalDivider()

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { uiEvent(SccUIEvent.CorneredBox) }
                        .padding(20.dp)
                ) {
                    ListItemText(title = "Cornered Box", createdBy = "Naulian")
                }
                NeumorphicDownHorizontalDivider()
            }
        }
    }
}