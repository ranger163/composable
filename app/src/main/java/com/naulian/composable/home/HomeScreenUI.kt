package com.naulian.composable.home

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialShapes
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.toShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.naulian.composable.core.Screen
import com.naulian.composable.core.component.ComposableTopAppBar
import com.naulian.composable.core.theme.ComposableTheme
import com.naulian.modify.ExperimentalModifyApi
import com.naulian.modify.HugeIcons
import com.naulian.neumorphic.NeumorphicDownHorizontalDivider
import com.naulian.neumorphic.NeumorphicIconButton
import com.naulian.neumorphic.NeumorphicSwitch
import com.naulian.neumorphic.neumorphicUp
import kotlinx.coroutines.delay

sealed interface HomeUIEvent {
    data class Navigate(val route: Screen) : HomeUIEvent
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
private val homeScreenList = listOf(
    HomeScreenItem(
        text = "Static Composable\nComponents",
        route = Screen.StaticCC,
        component = {
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
    ),
    HomeScreenItem(
        text = "Interactive Composable\nComponents",
        route = Screen.InteractiveCC,
        component = {
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
    ),
    HomeScreenItem(
        text = "Animated Composable\nComponents",
        route = Screen.AnimatedCC,
        component = {
            val infiniteTransition = rememberInfiniteTransition()
            val pulse by infiniteTransition.animateFloat(
                initialValue = 1f,
                targetValue = 1f,
                animationSpec = infiniteRepeatable(
                    animation = keyframes {
                        1.0f at 0 using LinearEasing
                        1.2f at 400 using LinearEasing
                        1.0f at 800 using LinearEasing
                        durationMillis = 800
                    },
                    repeatMode = RepeatMode.Restart,
                )
            )

            val rotation by infiniteTransition.animateFloat(
                initialValue = 0f,
                targetValue = 360f,
                animationSpec = infiniteRepeatable(
                    animation = tween(
                        durationMillis = 800,
                        easing = LinearEasing,
                        delayMillis = 800
                    ),
                    repeatMode = RepeatMode.Restart
                )
            )

            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .size(48.dp)
                    .scale(pulse)
                    .rotate(rotation)
                    .neumorphicUp(
                        shape = MaterialShapes.Clover4Leaf.toShape(),
                        shadowPadding = 4.dp
                    )
            )
        }
    )
)

@OptIn(ExperimentalMaterial3Api::class, ExperimentalModifyApi::class)
@Composable
fun HomeScreenUI(
    uiState: HomeUIState = HomeUIState(),
    uiEvent: (HomeUIEvent) -> Unit = {}
) {
    val listState = rememberLazyListState()

    Scaffold(
        topBar = {
            ComposableTopAppBar(
                title = "Composable",
                enableBack = false
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
            item {
                NeumorphicDownHorizontalDivider()
            }

            items(items = homeScreenList) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { uiEvent(HomeUIEvent.Navigate(it.route)) }
                        .padding(20.dp)
                ) {
                    Text(
                        text = it.text,
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                        overflow = TextOverflow.Ellipsis
                    )

                    it.component(this)
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

private data class HomeScreenItem(
    val text: String,
    val route: Screen,
    val component: @Composable BoxScope.() -> Unit = {}
)
