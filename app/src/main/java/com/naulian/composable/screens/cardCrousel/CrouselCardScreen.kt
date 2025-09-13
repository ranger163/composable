package com.naulian.composable.screens.cardCrousel

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.naulian.composable.LocalNavController
import com.naulian.composable.R
import com.naulian.composable.component.CodeBlock
import com.naulian.modify.HugeIcons

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CarouselCard3DScreen() {
    val code = remember { carouselCardCode }

    val navController = LocalNavController.current
    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            painter = painterResource(id = HugeIcons.Back),
                            contentDescription = "Back icon"
                        )
                    }
                },
                title = { Text(text = "Carousel Card") },
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
        ) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CarouselCard3D(
                        items = listOf(
                            CarouselItem("Mountains", painterResource(id = R.drawable.ic_launcher_background)),
                            CarouselItem("Beach", painterResource(id = R.drawable.ic_launcher_foreground)),
                            CarouselItem("Forest", painterResource(id = R.drawable.ic_launcher_background)),
                            CarouselItem("Cityscape", painterResource(id = R.drawable.ic_launcher_foreground)),
                        ),
                        modifier = Modifier,
                        autoScroll = false
                    )
                }
            }
            item {
                CodeBlock(
                    source = code,
                    language = "kotlin",
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}

private val carouselCardCode by lazy {
    """
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
    """.trimIndent()
}