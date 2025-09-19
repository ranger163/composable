package com.naulian.composable.acc

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
import com.naulian.composable.acc.typing.TypingText
import com.naulian.composable.core.component.ListItemText
import com.naulian.modify.HugeIcons
import com.naulian.modify.columnItem
import com.naulian.neumorphic.NeumorphicDownHorizontalDivider

sealed interface AccUIEvent {
    data object Back : AccUIEvent
    data object TypingText : AccUIEvent
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
            columnItem {
                NeumorphicDownHorizontalDivider()
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { uiEvent(AccUIEvent.TypingText) }
                        .padding(20.dp)
                ) {
                    ListItemText(title = "Typing Text", contributor = "Shree Bhargav R K")
                }
                NeumorphicDownHorizontalDivider()
            }
        }
    }
}