package com.naulian.composable.screens.scc

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.naulian.composable.component.ListItemText
import com.naulian.modify.ExperimentalModifyApi
import com.naulian.modify.HugeIcons
import com.naulian.modify.columnItem
import com.naulian.neumorphic.NeumorphicDownHorizontalDivider

sealed interface SccUIEvent {
    data object Back : SccUIEvent
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
                navigationIcon = {
                    IconButton(onClick = { uiEvent(SccUIEvent.Back) }) {
                        Icon(
                            painter = painterResource(HugeIcons.Back),
                            contentDescription = "Back Icon"
                        )
                    }
                },
                title = { Text(text = "Static Components") },
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
            }
        }
    }
}