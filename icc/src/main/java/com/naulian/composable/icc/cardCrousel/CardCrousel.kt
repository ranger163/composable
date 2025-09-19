package com.naulian.composable.icc.cardCrousel

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlin.math.absoluteValue
import com.naulian.composable.core.R

@Composable
fun CarouselCard3D(
    items: List<CarouselItem>,
    modifier: Modifier = Modifier,
    autoScroll: Boolean = false
) {
    val listState = rememberLazyListState()
    val density = LocalDensity.current

    val cardWidth = 280.dp
    val cardHeight = 400.dp
    val cardSpacing = 20.dp

    val screenWidth = 360.dp
    val screenWidthPx = with(density) { screenWidth.toPx() }
    val cardWidthPx = with(density) { cardWidth.toPx() }

    val sidePadding = (screenWidth - cardWidth) / 2

    LaunchedEffect(autoScroll) {
        if (autoScroll) {
            while (true) {
                delay(3000)
                val nextIndex = (listState.firstVisibleItemIndex + 1) % items.size
                listState.animateScrollToItem(nextIndex)
            }
        }
    }

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(cardHeight + 50.dp),
            contentAlignment = Alignment.Center
        ) {
            LazyRow(
                state = listState,
                horizontalArrangement = Arrangement.spacedBy(cardSpacing),
                contentPadding = PaddingValues(horizontal = sidePadding),
                modifier = Modifier.fillMaxWidth()
            ) {
                itemsIndexed(items) { index, item ->
                    val itemInfo = listState.layoutInfo.visibleItemsInfo
                        .firstOrNull { it.index == index }

                    val cardCenterX = itemInfo?.let { info ->
                        info.offset + info.size / 2f
                    } ?: 0f

                    val viewportCenter = screenWidthPx / 2f
                    val distanceFromCenter = (cardCenterX - viewportCenter).absoluteValue

                    val isFocused = distanceFromCenter < (cardWidthPx / 2)

                    val scale by animateFloatAsState(
                        targetValue = if (isFocused) 1f else 0.85f,
                        animationSpec = tween(300),
                        label = "scale"
                    )

                    val alpha by animateFloatAsState(
                        targetValue = if (isFocused) 1f else 0.5f,
                        animationSpec = tween(300),
                        label = "alpha"
                    )

                    val elevation by animateDpAsState(
                        targetValue = if (isFocused) 12.dp else 4.dp,
                        animationSpec = tween(300)
                    )

                    Card(
                        modifier = Modifier
                            .size(cardWidth, cardHeight)
                            .graphicsLayer {
                                scaleX = scale
                                scaleY = scale
                                this.alpha = alpha
                            },
                        elevation = CardDefaults.cardElevation(defaultElevation = elevation),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Column {
                            Image(
                                painter = item.image,
                                contentDescription = item.title,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(3f)
                            )
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f)
                                    .border(1.dp, Color.White, RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp))
                                    .background(Color(0xFF333333)),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = item.title,
                                    color = Color.White,
                                    fontSize = 18.sp
                                )
                            }
                        }
                    }
                }
            }
        }

        Row(
            modifier = Modifier.padding(top = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            repeat(items.size) { index ->
                val isSelected by remember {
                    derivedStateOf {
                        val visibleItems = listState.layoutInfo.visibleItemsInfo
                        if (visibleItems.isEmpty()) return@derivedStateOf index == 0

                        val screenCenter = screenWidthPx / 2f
                        var closestIndex = 0
                        var closestDistance = Float.MAX_VALUE

                        visibleItems.forEach { itemInfo ->
                            val itemCenter = itemInfo.offset + itemInfo.size / 2f
                            val distance = (itemCenter - screenCenter).absoluteValue
                            if (distance < closestDistance) {
                                closestDistance = distance
                                closestIndex = itemInfo.index
                            }
                        }

                        index == closestIndex
                    }
                }

                Box(
                    modifier = Modifier
                        .width(if (isSelected) 30.dp else 20.dp)
                        .height(4.dp)
                        .background(
                            if (isSelected) Color.White else Color.White.copy(alpha = 0.5f),
                            RoundedCornerShape(2.dp)
                        )
                )
            }
        }
    }
}

data class CarouselItem(
    val title: String,
    val image: Painter
)

@Preview(showBackground = true)
@Composable
fun CarouselPreview() {
    val sampleItems = listOf(
        CarouselItem("Trees", painterResource(id = R.drawable.a)),
        CarouselItem("Forest", painterResource(id = R.drawable.b)),
        CarouselItem("Flower", painterResource(id = R.drawable.c)),
        CarouselItem("Deep", painterResource(id = R.drawable.d))
    )

    MaterialTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color(0xFF1a1a1a)
        ) {
            Column {
                Text(
                    text = "3D Card Carousel Preview",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.padding(16.dp)
                )
                CarouselCard3D(
                    items = sampleItems,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    autoScroll = false
                )
            }
        }
    }
}