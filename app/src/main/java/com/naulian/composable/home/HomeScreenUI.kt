package com.naulian.composable.home

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.naulian.composable.core.theme.ComposableTheme
import com.naulian.modify.ExperimentalModifyApi
import com.naulian.modify.HugeIcons
import com.naulian.modify.columnItem
import com.naulian.neumorphic.NeumorphicDownHorizontalDivider
import com.naulian.neumorphic.NeumorphicIconButton
import com.naulian.neumorphic.NeumorphicSwitch
import com.naulian.neumorphic.neumorphicUp
import kotlinx.coroutines.delay

sealed interface HomeUIEvent {
    data object StaticCC : HomeUIEvent
    data object InteractiveCC : HomeUIEvent
    data object AnimatedCC : HomeUIEvent
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalModifyApi::class)
@Composable
fun HomeScreenUI(
    uiState: HomeUIState = HomeUIState(),
    uiEvent: (HomeUIEvent) -> Unit = {}
) {
    val listState = rememberLazyListState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = uiState.title) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        }
    ) { scaffoldPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(scaffoldPadding),
            state = listState,
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {

            columnItem {
                NeumorphicDownHorizontalDivider()

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable{ uiEvent(HomeUIEvent.StaticCC) }
                        .padding(20.dp)
                ) {
                    Text(
                        text = "Static Composable\nComponents",
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                        overflow = TextOverflow.Ellipsis
                    )

                    NeumorphicIconButton(
                        onClick = {},
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .background(
                                color = MaterialTheme.colorScheme.background,
                                shape = CircleShape
                            )
                    ) {
                        Icon(
                            painter = painterResource(HugeIcons.Favourite),
                            contentDescription = "Fav"
                        )
                    }
                }

                NeumorphicDownHorizontalDivider()

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable{ uiEvent(HomeUIEvent.InteractiveCC) }
                        .padding(20.dp),
                ) {
                    Text(
                        text = "Interactive Composable\nComponents",
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                        overflow = TextOverflow.Ellipsis
                    )

                    var checked by remember { mutableStateOf(false) }

                    LaunchedEffect(Unit) {
                        while (true) {
                            delay(1000)
                            checked = !checked
                        }
                    }

                    NeumorphicSwitch(
                        modifier = Modifier.align(Alignment.TopEnd),
                        checked = checked,
                        onCheckedChange = { checked = it }
                    )
                }

                NeumorphicDownHorizontalDivider()

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable{ uiEvent(HomeUIEvent.AnimatedCC) }
                        .padding(20.dp),
                ) {
                    Text(
                        text = "Animated Composable\nComponents",
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.onBackground,
                        overflow = TextOverflow.Ellipsis
                    )

                    var checked by remember { mutableStateOf(false) }

                    LaunchedEffect(Unit) {
                        while (true) {
                            delay(1000)
                            checked = !checked
                        }
                    }

                    val shadowPadding by animateDpAsState(
                        targetValue = if (checked) 4.dp else 10.dp,
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioMediumBouncy,
                            stiffness = Spring.StiffnessLow
                        )
                    )

                    Box(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .size(48.dp)
                            .neumorphicUp(
                                shape = RoundedCornerShape(10.dp),
                                shadowPadding = shadowPadding
                            )
                    ) {}
                }

                NeumorphicDownHorizontalDivider()
            }
        }
    }
}


@Preview
@Composable
private fun HomeScreenPreview() {
    ComposableTheme {
        HomeScreenUI { }
    }
}
