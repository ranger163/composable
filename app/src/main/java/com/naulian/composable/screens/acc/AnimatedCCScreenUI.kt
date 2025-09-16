package com.naulian.composable.screens.acc

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
import com.naulian.modify.HugeIcons

sealed interface AccUIEvent {
    data object Back : AccUIEvent
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnimatedCCScreenUI(
    uiEvent: (AccUIEvent) -> Unit = {}
) {
    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { uiEvent(AccUIEvent.Back) }) {
                        Icon(
                            painter = painterResource(HugeIcons.Back),
                            contentDescription = "Back Icon"
                        )
                    }
                },
                title = { Text(text = "Animated Components") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        }
    ) { scaffoldPadding ->
        LazyColumn(
            modifier = Modifier.padding(scaffoldPadding)
        ) {

        }
    }
}