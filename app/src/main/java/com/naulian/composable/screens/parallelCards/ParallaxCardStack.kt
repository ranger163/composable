package com.naulian.composable.screens.parallelCards

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.naulian.composable.R

data class ParallaxCard(
    val title: String,
    val subtitle: String,
    val imageResId: Int,
    val cardColor: Color
)

@Composable
fun ParallaxCardStack(
    modifier: Modifier = Modifier,
    scrollState: LazyListState
) {
    val cards = remember {
        listOf(
            ParallaxCard(
                title = "Nature's Echo",
                subtitle = "A journey through the greenest forests.",
                imageResId = R.drawable.ic_launcher_background, // Replace with actual images
                cardColor = Color(0xFF4CAF50)
            ),
            ParallaxCard(
                title = "Urban Escape",
                subtitle = "Discover the city's hidden gems.",
                imageResId = R.drawable.ic_launcher_background,
                cardColor = Color(0xFF2196F3)
            ),
            ParallaxCard(
                title = "Cosmic Wonders",
                subtitle = "Stargazing and nebula hunting.",
                imageResId = R.drawable.ic_launcher_background,
                cardColor = Color(0xFF673AB7)
            ),
            ParallaxCard(
                title = "Desert Solitude",
                subtitle = "Vast, silent, and full of mystery.",
                imageResId = R.drawable.ic_launcher_background,
                cardColor = Color(0xFFFF9800)
            )
        )
    }

     val firstVisibleItemIndex by remember {
        derivedStateOf { scrollState.firstVisibleItemIndex }
    }

    Column(modifier = modifier) {
        cards.forEachIndexed { index, card ->
            val isCurrentItem by remember {
                derivedStateOf { index == firstVisibleItemIndex }
            }

            val offset by remember {
                derivedStateOf {
                    if (isCurrentItem) {
                        scrollState.firstVisibleItemScrollOffset.toFloat()
                    } else {
                        0f
                    }
                }
            }
            val scale by remember {
                derivedStateOf {
                    val progress = scrollState.layoutInfo.visibleItemsInfo.firstOrNull()?.size?.toFloat()?.let {
                        offset / it
                    } ?: 0f

                    if (isCurrentItem) {
                        1f - (progress * 0.1f)
                    } else {
                        1f
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
        Box(modifier = Modifier.fillMaxWidth()) {
            Image(
                painter = painterResource(id = card.imageResId),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxWidth()
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Black.copy(alpha = 0.5f))
                    .padding(20.dp)
            ) {
                Text(
                    text = card.title,
                    style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
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
}

val parallaxCardStackCode by lazy {
    """
@Composable
fun ParallaxCardStack(
    modifier: Modifier = Modifier,
    scrollState: LazyListState
) {
    // Dummy data for demonstration
    val cards = remember {
        listOf(
            ParallaxCard(
                title = "Nature's Echo",
                subtitle = "A journey through the greenest forests.",
                imageResId = R.drawable.ic_launcher_background,
                cardColor = Color(0xFF4CAF50)
            ),
            ParallaxCard(
                title = "Urban Escape",
                subtitle = "Discover the city's hidden gems.",
                imageResId = R.drawable.ic_launcher_background,
                cardColor = Color(0xFF2196F3)
            ),
            ParallaxCard(
                title = "Cosmic Wonders",
                subtitle = "Stargazing and nebula hunting.",
                imageResId = R.drawable.ic_launcher_background,
                cardColor = Color(0xFF673AB7)
            ),
            ParallaxCard(
                title = "Desert Solitude",
                subtitle = "Vast, silent, and full of mystery.",
                imageResId = R.drawable.ic_launcher_background,
                cardColor = Color(0xFFFF9800)
            )
        )
    }

    // This is the core logic for the parallax effect.
    // It finds the first visible item and uses its scroll progress
    // to determine the scale and offset of the items below it.
    val firstVisibleItemIndex by remember {
        derivedStateOf { scrollState.firstVisibleItemIndex }
    }

    Column(modifier = modifier) {
        cards.forEachIndexed { index, card ->
            val isCurrentItem by remember {
                derivedStateOf { index == firstVisibleItemIndex }
            }

            // Calculate the parallax offset and scale
            val offset by remember {
                derivedStateOf {
                    if (isCurrentItem) {
                        scrollState.firstVisibleItemScrollOffset.toFloat()
                    } else {
                        0f
                    }
                }
            }
            val scale by remember {
                derivedStateOf {
                    val progress = scrollState.layoutInfo.visibleItemsInfo.firstOrNull()?.size?.toFloat()?.let {
                        offset / it
                    } ?: 0f

                    if (isCurrentItem) {
                        1f - (progress * 0.1f)
                    } else {
                        1f
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
}
    """.trimIndent()
}
