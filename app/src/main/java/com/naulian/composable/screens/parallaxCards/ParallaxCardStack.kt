package com.naulian.composable.screens.parallaxCards

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import com.naulian.modify.Bold

data class ParallaxCard(
    val title: String,
    val subtitle: String,
    val cardColor: Color
)

val dummyCards by lazy {
    listOf(
        ParallaxCard(
            title = "Nature's Echo",
            subtitle = "A journey through the greenest forests.",
            cardColor = Color(0xFF4CAF50)
        ),
        ParallaxCard(
            title = "Urban Escape",
            subtitle = "Discover the city's hidden gems.",
            cardColor = Color(0xFF2196F3)
        ),
        ParallaxCard(
            title = "Cosmic Wonders",
            subtitle = "Stargazing and nebula hunting.",
            cardColor = Color(0xFF673AB7)
        ),
        ParallaxCard(
            title = "Desert Solitude",
            subtitle = "Vast, silent, and full of mystery.",
            cardColor = Color(0xFFFF9800)
        )
    )
}

fun LazyListScope.ParallaxCardStack(
    cardItems: List<ParallaxCard>,
    state: LazyListState
) {
    itemsIndexed(cardItems) { index, card ->
        val isCurrentItem by remember {
            derivedStateOf { index == state.firstVisibleItemIndex }
        }

        val isUnderStack by remember {
            derivedStateOf { index < state.firstVisibleItemIndex }
        }

        val offset by remember {
            derivedStateOf {
                if (isCurrentItem) {
                    state.firstVisibleItemScrollOffset.toFloat()
                } else {
                    0f
                }
            }
        }

        val scale by remember {
            derivedStateOf {
                val progress = state.layoutInfo.visibleItemsInfo.firstOrNull()?.let {
                    offset / it.size.toFloat()
                } ?: 0f

                when {
                    isUnderStack -> 0f
                    isCurrentItem -> 1f - (progress * 0.1f)
                    else -> 1f
                }
            }
        }

        ParallaxCardItem(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1.5f)
                .graphicsLayer {
                    translationY = offset
                    scaleX = scale
                    scaleY = scale
                },
            card = card
        )
        Spacer(modifier = Modifier.height(10.dp))
    }
}

@Composable
private fun ParallaxCardItem(
    modifier: Modifier = Modifier,
    card: ParallaxCard
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = card.cardColor)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Black.copy(alpha = 0.5f))
                .padding(20.dp)
        ) {
            Text(
                text = card.title,
                style = MaterialTheme.typography.headlineMedium.Bold,
                color = Color.White
            )
            Text(
                text = card.subtitle,
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White
            )
        }
    }
}

val parallaxCardStackCode by lazy {
    """
        data class ParallaxCard(
            val title: String,
            val subtitle: String,
            val cardColor: Color
        )
        
        fun LazyListScope.ParallaxCardStack(
            cardItems: List<ParallaxCard>,
            state: LazyListState
        ) {
            itemsIndexed(cardItems) { index, card ->
                val isCurrentItem by remember {
                    derivedStateOf { index == state.firstVisibleItemIndex }
                }

                val isUnderStack by remember {
                    derivedStateOf { index < state.firstVisibleItemIndex }
                }

                val offset by remember {
                    derivedStateOf {
                        if (isCurrentItem) {
                            state.firstVisibleItemScrollOffset.toFloat()
                        } else {
                            0f
                        }
                    }
                }

                val scale by remember {
                    derivedStateOf {
                        val progress = state.layoutInfo.visibleItemsInfo.firstOrNull()?.let {
                            offset / it.size.toFloat()
                        } ?: 0f

                        when {
                            isUnderStack -> 0f
                            isCurrentItem -> 1f - (progress * 0.1f)
                            else -> 1f
                        }
                    }
                }

                ParallaxCardItem(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1.5f)
                        .graphicsLayer {
                            translationY = offset
                            scaleX = scale
                            scaleY = scale
                        },
                    card = card
                )
                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    """.trimIndent()
}
