package com.naulian.composable.screens.animations


import android.R.attr.progress
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.StartOffset
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.animate
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.naulian.composable.LocalNavController
import com.naulian.composable.component.CodeBlock
import com.naulian.composable.theme.ComposableTheme
import com.naulian.modify.HugeIcons
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.abs
import kotlin.math.absoluteValue
import kotlin.math.sin
import kotlin.random.Random


@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun AnimationsInteractionsScreenUI(onBack: () -> Unit = {}) {
    val code = remember { animationCode }

    val navController = LocalNavController.current
    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(
                        onClick = { navController.popBackStack() }
                    )  {
                        Icon(
                            painter = painterResource(HugeIcons.Back),
                            contentDescription = "Back icon"
                        )
                    }
                },
                title = {
                    Text(
                        "Extraordinary Animations",
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.animateContentSize()
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(40.dp)
        ) {


            item {
                var pressed by remember { mutableStateOf(false) }
                val scale by animateFloatAsState(if (pressed) 0.9f else 1f)
                val colors by animateColorAsState(
                    targetValue = if (pressed) Color(0xFFff758c) else Color(0xFF43e97b)
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(70.dp)
                        .scale(scale)
                        .background(
                            brush = Brush.horizontalGradient(listOf(colors, colors.copy(alpha = 0.7f))),
                            shape = RoundedCornerShape(if (pressed) 35.dp else 20.dp)
                        )
                        .clickable { pressed = !pressed },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "Fluid Gradient Button",
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        fontSize = 18.sp
                    )
                }
            }


            item {
                var flipped by remember { mutableStateOf(false) }
                val rotation by animateFloatAsState(targetValue = if (flipped) 180f else 0f, tween(600))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp)
                        .graphicsLayer {
                            rotationY = rotation
                            cameraDistance = 12f * density
                        }
                        .background(
                            if (rotation < 90f) Color(0xFF6200EE) else Color(0xFFBB86FC),
                            RoundedCornerShape(16.dp)
                        )
                        .clickable { flipped = !flipped },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        if (rotation < 90f) "Front Side" else "Back Side",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }


            item {
                var pressed by remember { mutableStateOf(false) }
                val scale by animateFloatAsState(
                    targetValue = if (pressed) 1.3f else 1f,
                    animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy)
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                        .scale(scale)
                        .background(Color(0xFFFFC107), RoundedCornerShape(20.dp))
                        .clickable { pressed = !pressed },
                    contentAlignment = Alignment.Center
                ) {
                    Text("Springy Button", fontSize = 18.sp, color = Color.White, fontWeight = FontWeight.Bold)
                }
            }


            item {
                var confetti by remember { mutableStateOf(listOf<Offset>()) }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp)
                        .background(Color.DarkGray, RoundedCornerShape(20.dp))
                        .clickable {
                            val newParticles = List(20) { Offset(Random.nextFloat() * 300, 0f) }
                            confetti = confetti + newParticles
                        }
                ) {
                    confetti.forEachIndexed { index, offset ->
                        var animY by remember { mutableStateOf(offset.y) }
                        LaunchedEffect(animY) {
                            animate(animY, targetValue = 120f, animationSpec = tween(800)) { value, _ ->
                                animY = value
                            }
                        }
                        Box(
                            modifier = Modifier
                                .offset(x = offset.x.dp, y = animY.dp)
                                .size(8.dp)
                                .background(
                                    Color(Random.nextInt(0, 255), Random.nextInt(0, 255), Random.nextInt(0, 255)),
                                    CircleShape
                                )
                        )
                    }
                    Text("Tap for Confetti!", color = Color.White, modifier = Modifier.align(Alignment.Center))
                }
            }


            item {
                var offsetX by remember { mutableStateOf(0f) }
                var offsetY by remember { mutableStateOf(0f) }
                var rotationX by remember { mutableStateOf(0f) }
                var rotationY by remember { mutableStateOf(0f) }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp)
                        .background(Color(0xFF03DAC5), RoundedCornerShape(16.dp))
                        .pointerInput(Unit) {
                            detectDragGestures { change, dragAmount ->
                                change.consume()
                                offsetX += dragAmount.x
                                offsetY += dragAmount.y
                                rotationX = offsetY / 10
                                rotationY = -offsetX / 10
                            }
                        }
                        .graphicsLayer {
                            translationX = offsetX
                            translationY = offsetY
                            rotationX = rotationX
                            rotationY = rotationY
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text("Drag & Tilt Me!", color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                }
            }


            item {
                val infiniteTransition = rememberInfiniteTransition()
                val shimmer by infiniteTransition.animateFloat(
                    initialValue = 0f, targetValue = 1f,
                    animationSpec = infiniteRepeatable(tween(1000), RepeatMode.Restart)
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(
                            Brush.horizontalGradient(
                                listOf(Color.LightGray, Color.White.copy(alpha = 0.5f), Color.LightGray),
                                startX = shimmer * 300
                            )
                        )
                )
            }


            item {
                var textIndex by remember { mutableStateOf(0) }
                val message = "This is a typing animation demo!"
                LaunchedEffect(Unit) {
                    while (textIndex < message.length) {
                        textIndex++
                        delay(80)
                    }
                }
                Text(message.take(textIndex), fontSize = 20.sp, fontWeight = FontWeight.Bold)
            }


            item {
                val cards = remember { mutableStateListOf("Card 1", "Card 2", "Card 3", "Card 4") }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    contentAlignment = Alignment.Center
                ) {
                    cards.reversed().forEachIndexed { index, card ->
                        var offsetX by remember { mutableStateOf(0f) }
                        var offsetY by remember { mutableStateOf(0f) }
                        var rotation by remember { mutableStateOf(0f) }

                        if (index == cards.lastIndex) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth(0.8f)
                                    .height(160.dp)
                                    .graphicsLayer {
                                        translationX = offsetX
                                        translationY = offsetY
                                        rotationZ = rotation
                                        scaleX = 1f - index * 0.05f
                                        scaleY = 1f - index * 0.05f
                                    }
                                    .background(Color(0xFF6200EE + index * 0x00111111), RoundedCornerShape(16.dp))
                                    .pointerInput(Unit) {
                                        detectDragGestures(
                                            onDragEnd = {
                                                if (offsetX.absoluteValue > 150) {
                                                    cards.removeAt(cards.lastIndex)
                                                } else {
                                                    offsetX = 0f
                                                    offsetY = 0f
                                                    rotation = 0f
                                                }
                                            }
                                        ) { change, dragAmount ->
                                            change.consume()
                                            offsetX += dragAmount.x
                                            offsetY += dragAmount.y
                                            rotation = offsetX / 10
                                        }
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                Text(card, color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                            }
                        } else {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth(0.8f)
                                    .height(160.dp)
                                    .graphicsLayer {
                                        scaleX = 1f - index * 0.05f
                                        scaleY = 1f - index * 0.05f
                                        translationY = index * 16.dp.toPx()
                                    }
                                    .background(Color(0xFF6200EE + index * 0x00111111), RoundedCornerShape(16.dp)),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(card, color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }


            item {
                var expanded by remember { mutableStateOf(false) }
                val rotation by animateFloatAsState(
                    targetValue = if (expanded) 45f else 0f,
                    animationSpec = tween(durationMillis = 300)
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        AnimatedVisibility(
                            visible = expanded,
                            enter = fadeIn() + expandHorizontally(),
                            exit = fadeOut() + shrinkHorizontally()
                        ) {
                            Box(
                                modifier = Modifier
                                    .background(Color(0xFF03A9F4), RoundedCornerShape(16.dp))
                                    .padding(horizontal = 16.dp, vertical = 8.dp)
                            ) {
                                Text("Options Expanded!", color = Color.White)
                            }
                        }

                        Box(
                            modifier = Modifier
                                .size(56.dp)
                                .background(Color(0xFFFF4081), CircleShape)
                                .clickable { expanded = !expanded },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                painter = painterResource(HugeIcons.Add),
                                contentDescription = "Add",
                                tint = Color.White,
                                modifier = Modifier.rotate(rotation)
                            )
                        }
                    }
                }
            }

            item {
                var liked by remember { mutableStateOf(false) }
                val infiniteTransition = rememberInfiniteTransition()
                val pulse by infiniteTransition.animateFloat(
                    initialValue = 1f,
                    targetValue = if (liked) 1.2f else 1f,
                    animationSpec = infiniteRepeatable(
                        animation = keyframes {
                            durationMillis = 800
                            1.0f at 0 with LinearEasing
                            1.2f at 400 with LinearEasing
                            1.0f at 800 with LinearEasing
                        },
                        repeatMode = RepeatMode.Restart
                    )
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                        .background(Color(0x22FF0000), RoundedCornerShape(16.dp))
                        .clickable { liked = !liked },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Filled.Favorite,
                        contentDescription = "Heart",
                        tint = if (liked) Color(0xFFFF0000) else Color(0x88AAAAAA),
                        modifier = Modifier
                            .size(40.dp)
                            .scale(pulse)
                    )
                }
            }


            item {
                var waves by remember { mutableStateOf(listOf<WaveParticle>()) }

                LaunchedEffect(Unit) {
                    while (true) {
                        if (waves.size < 15) {
                            waves = waves + WaveParticle(
                                offset = Offset(Random.nextFloat() * 300, 120f),
                                progress = 0f,
                                speed = Random.nextFloat() * 2 + 1f,
                                size = Random.nextFloat() * 10 + 5f
                            )
                        }
                        delay(200)
                    }
                }

                LaunchedEffect(waves) {
                    waves.forEachIndexed { index, wave ->
                        animate(
                            initialValue = 0f,
                            targetValue = 1f,
                            animationSpec = tween(durationMillis = (2000 / wave.speed).toInt())
                        ) { value, _ ->
                            waves = waves.toMutableList().apply {
                                this[index] = wave.copy(progress = value)
                            }
                        }
                    }
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .background(Color(0xFF3F51B5), RoundedCornerShape(16.dp))
                        .pointerInput(Unit) {
                            detectTapGestures {
                                waves = waves + WaveParticle(
                                    offset = Offset(it.x, it.y),
                                    progress = 0f,
                                    speed = Random.nextFloat() * 2 + 1f,
                                    size = Random.nextFloat() * 10 + 5f
                                )
                            }
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text("Tap for Ripple Effect!", color = Color.White)

                    Canvas(modifier = Modifier.fillMaxSize()) {
                        waves.forEach { wave ->
                            drawCircle(
                                color = Color.White.copy(alpha = 1 - wave.progress),
                                center = Offset(wave.offset.x, wave.offset.y),
                                radius = wave.size * wave.progress * 10
                            )
                        }

                        waves = waves.filter { it.progress < 0.99f }
                    }
                }
            }


            item {
                var tilt by remember { mutableStateOf(Offset(0f, 0f)) }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .pointerInput(Unit) {
                            detectDragGestures { change, dragAmount ->
                                tilt = Offset(
                                    x = (tilt.x + dragAmount.x / 10).coerceIn(-10f, 10f),
                                    y = (tilt.y + dragAmount.y / 10).coerceIn(-10f, 10f)
                                )
                            }
                        }
                ) {

                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .offset(x = tilt.x.dp * 0.5f, y = tilt.y.dp * 0.5f)
                            .background(
                                Brush.radialGradient(
                                    colors = listOf(Color(0xFFE91E63), Color(0xFF9C27B0)),
                                    center = Offset(0.3f, 0.3f),
                                    radius = 300f
                                ),
                                RoundedCornerShape(16.dp)
                            )
                    )


                    Box(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .size(120.dp)
                            .offset(x = tilt.x.dp * 1f, y = tilt.y.dp * 1f)
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(Color(0x99FFFFFF), Color(0x33FFFFFF))
                                ),
                                CircleShape
                            )
                    )

                    Text(
                        "Parallax Effect",
                        color = Color.White,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .offset(x = tilt.x.dp * 1.5f, y = tilt.y.dp * 1.5f)
                    )
                }
            }


            item {
                val infiniteTransition = rememberInfiniteTransition()
                val bounce by infiniteTransition.animateFloat(
                    initialValue = 0f,
                    targetValue = 1f,
                    animationSpec = infiniteRepeatable(
                        animation = keyframes {
                            durationMillis = 1000
                            0.0f at 0 with LinearEasing
                            1.0f at 500 with LinearEasing
                            0.0f at 1000 with LinearEasing
                        }
                    )
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .background(Color(0xFF212121), RoundedCornerShape(16.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {

                        listOf(0, 150, 300).forEach { delayMs ->
                            val ballBounce by infiniteTransition.animateFloat(
                                initialValue = 0f,
                                targetValue = 1f,
                                animationSpec = infiniteRepeatable(
                                    animation = keyframes {
                                        durationMillis = 1000
                                        0.0f at 0 with LinearEasing
                                        1.0f at 500 with LinearEasing
                                        0.0f at 1000 with LinearEasing
                                    },
                                    initialStartOffset = StartOffset(delayMs)
                                )
                            )

                            Box(
                                modifier = Modifier
                                    .size(20.dp)
                                    .offset(y = -(ballBounce * 30).dp)
                                    .background(Color(0xFF00BCD4), CircleShape)
                            )
                        }
                    }
                }
            }


            item {
                val infiniteTransition = rememberInfiniteTransition()
                val colorProgress by infiniteTransition.animateFloat(
                    initialValue = 0f,
                    targetValue = 1f,
                    animationSpec = infiniteRepeatable(
                        tween(4000, easing = LinearEasing),
                        RepeatMode.Reverse
                    )
                )

                val colors = listOf(
                    Color(0xFFFF5252),
                    Color(0xFFFF4081),
                    Color(0xFFE040FB),
                    Color(0xFF7C4DFF),
                    Color(0xFF536DFE),
                    Color(0xFF448AFF),
                    Color(0xFF40C4FF),
                    Color(0xFF18FFFF)
                )

                val currentColor = lerpColor(colors, colorProgress)

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .background(
                            Brush.linearGradient(
                                listOf(currentColor, currentColor.copy(alpha = 0.8f))
                            ),
                            RoundedCornerShape(16.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "Color Morphing",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }


            item {
                var rating by remember { mutableStateOf(0) }
                val stars = listOf(1, 2, 3, 4, 5)

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                        .background(Color(0xFFEEEEEE), RoundedCornerShape(16.dp)),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    stars.forEach { star ->
                        val scale by animateFloatAsState(
                            targetValue = if (rating >= star) 1.2f else 1f,
                            animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy)
                        )

                        Icon(
                            Icons.Filled.Star,
                            contentDescription = "Star",
                            tint = if (rating >= star) Color(0xFFFFC107) else Color(0xFF9E9E9E),
                            modifier = Modifier
                                .size(40.dp)
                                .scale(scale)
                                .clickable { rating = star }
                        )
                    }
                }
            }

            item {
                var offset by remember { mutableStateOf(Offset.Zero) }
                var isStuck by remember { mutableStateOf(false) }
                val animatableOffset = remember { Animatable(Offset.Zero, Offset.VectorConverter) }
                val coroutineScope = rememberCoroutineScope()

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .background(Color(0xFFF5F5F5), RoundedCornerShape(16.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .offset { IntOffset(offset.x.toInt(), offset.y.toInt()) }
                            .size(60.dp)
                            .pointerInput(Unit) {
                                detectDragGestures(
                                    onDragEnd = {
                                        val target = if (offset.x.absoluteValue < 100f && offset.y.absoluteValue < 100f) {
                                            isStuck = false
                                            Offset.Zero
                                        } else {
                                            isStuck = true
                                            Offset(
                                                x = if (offset.x > 0) 150f else -150f,
                                                y = if (offset.y > 0) 150f else -150f
                                            )
                                        }

                                        coroutineScope.launch {
                                            animatableOffset.animateTo(target, spring(dampingRatio = Spring.DampingRatioMediumBouncy))
                                            offset = animatableOffset.value
                                        }
                                    }
                                ) { change, dragAmount ->
                                    change.consume()
                                    offset += dragAmount
                                }
                            }
                            .background(
                                if (isStuck) Color(0xFFFF5722) else Color(0xFF3F51B5),
                                CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Drag", color = Color.White)
                    }


                    Box(
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(16.dp)
                            .size(8.dp)
                            .background(Color.Red, CircleShape)
                    )
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(16.dp)
                            .size(8.dp)
                            .background(Color.Red, CircleShape)
                    )
                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(16.dp)
                            .size(8.dp)
                            .background(Color.Red, CircleShape)
                    )
                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(16.dp)
                            .size(8.dp)
                            .background(Color.Red, CircleShape)
                    )
                }
            }



            item {
                var progress by remember { mutableStateOf(0f) }
                val infiniteTransition = rememberInfiniteTransition()
                val waveOffset by infiniteTransition.animateFloat(
                    initialValue = 0f,
                    targetValue = 1f,
                    animationSpec = infiniteRepeatable(
                        tween(2000, easing = LinearEasing),
                        RepeatMode.Restart
                    )
                )
                val coroutineScope = rememberCoroutineScope()

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .background(Color(0xFFE0F7FA), RoundedCornerShape(16.dp))
                        .clickable {
                            coroutineScope.launch {
                                animate(
                                    initialValue = progress,
                                    targetValue = if (progress == 0f) 1f else 0f,
                                    animationSpec = tween(1000)
                                ) { value, _ ->
                                    progress = value
                                }
                            }
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Canvas(modifier = Modifier.fillMaxSize()) {
                        val width = size.width
                        val height = size.height
                        val waveHeight = 20f
                        val adjustedProgress = progress * width

                        drawRect(Color(0xFF0097A7))


                        drawPath(
                            Path().apply {
                                moveTo(0f, height)
                                lineTo(0f, height * 0.7f)

                                for (x in 0 until width.toInt() step 5) {
                                    val y = height * 0.7f + sin((x / width * 20 + waveOffset * 10)) * waveHeight
                                    lineTo(x.toFloat(), y)
                                }

                                lineTo(width, height)
                                close()
                            },
                            color = Color(0xFF00BCD4)
                        )

                        drawCircle(
                            color = Color(0xFFB2EBF2),
                            center = Offset(adjustedProgress, height * 0.7f),
                            radius = 30f * progress
                        )
                    }

                    Text(
                        "Liquid Swipe →",
                        color = Color.White,
                        fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                        modifier = Modifier.padding(start = 30.dp)
                    )
                }
            }



            item {
                var rotationAngle by remember { mutableStateOf(0f) }
                val infiniteTransition = rememberInfiniteTransition()
                val rotation by infiniteTransition.animateFloat(
                    initialValue = 0f,
                    targetValue = 360f,
                    animationSpec = infiniteRepeatable(tween(10000, easing = LinearEasing))
                )

                LaunchedEffect(Unit) {
                    while (true) {
                        rotationAngle += 0.5f
                        if (rotationAngle > 360f) rotationAngle = 0f
                        delay(16)
                    }
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .graphicsLayer {
                            rotationZ = rotation / 10
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Canvas(modifier = Modifier.fillMaxSize()) {
                        val width = size.width
                        val height = size.height

                        rotate(rotationAngle) {
                            drawRect(
                                brush = Brush.sweepGradient(
                                    center = Offset(width / 2, height / 2),
                                    colors = listOf(
                                        Color(0xFFFF00FF),
                                        Color(0xFF00FFFF),
                                        Color(0xFFFFFF00),
                                        Color(0xFFFF00FF)
                                    )
                                ),
                                alpha = 0.7f
                            )
                        }


                        drawRect(
                            color = Color.Black.copy(alpha = 0.7f),
                            topLeft = Offset(20f, 20f),
                            size = Size(width - 40f, height - 40f)
                        )
                    }

                    Text(
                        "HOLO CARD",
                        color = Color.White,
                        fontSize = 24.sp,
                        fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                        letterSpacing = 4.sp
                    )
                }
            }



            item {
                var isCircle by remember { mutableStateOf(true) }
                val cornerRadius by animateFloatAsState(
                    targetValue = if (isCircle) 100f else 16f,
                    animationSpec = tween(1000, easing = FastOutSlowInEasing)
                )
                val rotation by animateFloatAsState(
                    targetValue = if (isCircle) 0f else 45f,
                    animationSpec = tween(1000, easing = FastOutSlowInEasing)
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp)
                        .clickable { isCircle = !isCircle },
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .graphicsLayer {
                                rotationZ = rotation
                            }
                            .background(
                                color = Color(0xFFFF9800),
                                shape = RoundedCornerShape(cornerRadius.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            if (isCircle) "○" else "□",
                            color = Color.White,
                            fontSize = 30.sp
                        )
                    }
                }
            }


            item {
                val density = LocalDensity.current
                var particles by remember { mutableStateOf(listOf<com.naulian.composable.screens.animations.Particle>()) }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .background(Color(0xFF263238), RoundedCornerShape(16.dp))
                        .clickable {

                            particles = List(15) {
                                com.naulian.composable.screens.animations.Particle(
                                    position = Offset(
                                        x = Random.nextFloat() * with(density) { 300.dp.toPx() },
                                        y = 0f
                                    ),
                                    velocity = Offset(0f, 0f),
                                    size = Random.nextFloat() * 10 + 5f,
                                    color = Color(
                                        red = Random.nextInt(180, 255),
                                        green = Random.nextInt(180, 255),
                                        blue = Random.nextInt(180, 255)
                                    )
                                )
                            }
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text("Tap for Gravity", color = Color.White)

                    Canvas(modifier = Modifier.fillMaxSize()) {
                        particles = particles.map { particle ->
                            var newVelocity: Offset = particle.velocity + Offset(0f, 0.2f)
                            var newPosition: Offset = particle.position + newVelocity


                            if (newPosition.y > size.height - particle.size) {
                                newPosition = Offset(newPosition.x, size.height - particle.size)
                                newVelocity = Offset(newVelocity.x, -newVelocity.y * 0.8f)
                            }


                            if (newPosition.x < particle.size || newPosition.x > size.width - particle.size) {
                                newVelocity = Offset(-newVelocity.x * 0.8f, newVelocity.y)
                            }

                            drawCircle(
                                color = particle.color,
                                center = newPosition,
                                radius = particle.size
                            )

                            particle.copy(position = newPosition, velocity = newVelocity)
                        }
                    }

                    LaunchedEffect(particles) {
                        while (true) {
                            delay(16)
                            particles = particles
                        }
                    }
                }
            }



            item {
                val infiniteTransition = rememberInfiniteTransition()
                val pulse by infiniteTransition.animateFloat(
                    initialValue = 0f,
                    targetValue = 1f,
                    animationSpec = infiniteRepeatable(
                        tween(2000, easing = LinearEasing),
                        RepeatMode.Reverse
                    )
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                        .background(Color.Black, RoundedCornerShape(16.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Canvas(modifier = Modifier.fillMaxSize()) {
                        val width = size.width
                        val height = size.height
                        val nodes = 5
                        val layers = 3


                        for (layer in 0 until layers - 1) {
                            for (i in 0 until nodes) {
                                for (j in 0 until nodes) {
                                    val startX = (layer + 1) * width / (layers + 1)
                                    val endX = (layer + 2) * width / (layers + 1)
                                    val startY = (i + 1) * height / (nodes + 1)
                                    val endY = (j + 1) * height / (nodes + 1)

                                    val alpha = 0.3f + 0.7f * (1 - (abs(i - j) / nodes.toFloat()))

                                    drawLine(
                                        color = Color.Green.copy(alpha = alpha * pulse),
                                        start = Offset(startX, startY),
                                        end = Offset(endX, endY),
                                        strokeWidth = 1f
                                    )
                                }
                            }
                        }


                        for (layer in 0 until layers) {
                            for (i in 0 until nodes) {
                                val x = (layer + 1) * width / (layers + 1)
                                val y = (i + 1) * height / (nodes + 1)
                                val size = 12f + 8f * sin(pulse * 6.28f + layer + i)

                                drawCircle(
                                    color = Color.Cyan,
                                    center = Offset(x, y),
                                    radius = size
                                )
                            }
                        }
                    }

                    Text("Neural Network", color = Color.White, modifier = Modifier.padding(8.dp))
                }
            }

            item {
                var glitch by remember { mutableStateOf(false) }
                val infiniteTransition = rememberInfiniteTransition()
                val offset by infiniteTransition.animateFloat(
                    initialValue = 0f,
                    targetValue = 1f,
                    animationSpec = infiniteRepeatable(
                        tween(100, easing = LinearEasing),
                        RepeatMode.Restart
                    )
                )

                LaunchedEffect(glitch) {
                    if (glitch) {
                        delay(500)
                        glitch = false
                    }
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                        .background(Color(0xFF121212), RoundedCornerShape(16.dp))
                        .clickable { glitch = true },
                    contentAlignment = Alignment.Center
                ) {
                    if (glitch) {
                        Text(
                            "GL!TCH 3FF3CT",
                            color = Color.Red,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.offset(x = (-2 + offset * 4).dp, y = (-2 + offset * 4).dp)
                        )
                        Text(
                            "GL!TCH 3FF3CT",
                            color = Color.Blue,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.offset(x = (2 - offset * 4).dp, y = (2 - offset * 4).dp)
                        )
                    }

                    Text(
                        "GLITCH EFFECT",
                        color = Color.White,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            item {
                val infiniteTransition = rememberInfiniteTransition()
                val auroraShift by infiniteTransition.animateFloat(
                    initialValue = 0f,
                    targetValue = 1f,
                    animationSpec = infiniteRepeatable(
                        tween(8000, easing = LinearEasing),
                        RepeatMode.Restart
                    )
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp)
                        .clip(RoundedCornerShape(16.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Canvas(modifier = Modifier.fillMaxSize()) {
                        val width = size.width
                        val height = size.height

                        for (i in 0 until 5) {
                            drawRect(
                                brush = Brush.verticalGradient(
                                    colors = listOf(
                                        Color.Transparent,
                                        Color.hsl((auroraShift * 360 + i * 30) % 360, 0.8f, 0.5f, 0.7f),
                                        Color.Transparent
                                    ),
                                    startY = height * (i / 5f) - auroraShift * height / 2,
                                    endY = height * ((i + 1) / 5f) - auroraShift * height / 2
                                )
                            )
                        }
                    }

                    Text(
                        "Aurora Effect",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.background(Color.Black.copy(alpha = 0.5f), RoundedCornerShape(8.dp))
                            .padding(8.dp)
                    )
                }
            }

            item {
                val infiniteTransition = rememberInfiniteTransition()
                val progress by infiniteTransition.animateFloat(
                    initialValue = 0f,
                    targetValue = 1f,
                    animationSpec = infiniteRepeatable(
                        tween(2000, easing = LinearEasing),
                        RepeatMode.Restart
                    )
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                        .background(Color(0xFFE0E0E0), RoundedCornerShape(16.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .height(8.dp)
                            .background(Color(0xFFBDBDBD), RoundedCornerShape(4.dp))
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(progress)
                                .height(8.dp)
                                .background(
                                    Brush.horizontalGradient(
                                        listOf(Color(0xFF00BCD4), Color(0xFF3F51B5))
                                    ),
                                    RoundedCornerShape(4.dp)
                                )
                        )
                    }

                    Text(
                        "Loading... ${(progress * 100).toInt()}%",
                        modifier = Modifier.padding(top = 32.dp),
                        fontWeight = FontWeight.Bold
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



data class WaveParticle(
    val offset: Offset,
    val progress: Float,
    val speed: Float,
    val size: Float
)


fun lerpColor(colors: List<Color>, progress: Float): Color {
    val segment = 1f / (colors.size - 1)
    val index = (progress / segment).toInt().coerceIn(0, colors.size - 2)
    val segmentProgress = (progress - index * segment) / segment

    return lerp(
        colors[index],
        colors[index + 1],
        segmentProgress
    )
}

@Preview(showBackground = true)
@Composable
fun AnimationsInteractionsScreenUIPreview() {
    ComposableTheme {
        AnimationsInteractionsScreenUI()
    }
}



private val animationCode by lazy {
    """
        @OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
        @Composable
        fun AnimationsInteractionsScreenUI(onBack: () -> Unit = {}) {

            val navController = LocalNavController.current
            Scaffold(
                topBar = {
                    TopAppBar(
                        navigationIcon = {
                            IconButton(
                                onClick = { navController.popBackStack() }
                            )  {
                                Icon(
                                    painter = painterResource(HugeIcons.Back),
                                    contentDescription = "Back icon"
                                )
                            }
                        },
                        title = {
                            Text(
                                "Extraordinary Animations",
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.animateContentSize()
                            )
                        },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = MaterialTheme.colorScheme.background
                        )
                    )
                }
            ) { paddingValues ->
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(40.dp)
                ) {


                    item {
                        var pressed by remember { mutableStateOf(false) }
                        val scale by animateFloatAsState(if (pressed) 0.9f else 1f)
                        val colors by animateColorAsState(
                            targetValue = if (pressed) Color(0xFFff758c) else Color(0xFF43e97b)
                        )

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(70.dp)
                                .scale(scale)
                                .background(
                                    brush = Brush.horizontalGradient(listOf(colors, colors.copy(alpha = 0.7f))),
                                    shape = RoundedCornerShape(if (pressed) 35.dp else 20.dp)
                                )
                                .clickable { pressed = !pressed },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                "Fluid Gradient Button",
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                fontSize = 18.sp
                            )
                        }
                    }


                    item {
                        var flipped by remember { mutableStateOf(false) }
                        val rotation by animateFloatAsState(targetValue = if (flipped) 180f else 0f, tween(600))

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(120.dp)
                                .graphicsLayer {
                                    rotationY = rotation
                                    cameraDistance = 12f * density
                                }
                                .background(
                                    if (rotation < 90f) Color(0xFF6200EE) else Color(0xFFBB86FC),
                                    RoundedCornerShape(16.dp)
                                )
                                .clickable { flipped = !flipped },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                if (rotation < 90f) "Front Side" else "Back Side",
                                color = Color.White,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }


                    item {
                        var pressed by remember { mutableStateOf(false) }
                        val scale by animateFloatAsState(
                            targetValue = if (pressed) 1.3f else 1f,
                            animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy)
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(80.dp)
                                .scale(scale)
                                .background(Color(0xFFFFC107), RoundedCornerShape(20.dp))
                                .clickable { pressed = !pressed },
                            contentAlignment = Alignment.Center
                        ) {
                            Text("Springy Button", fontSize = 18.sp, color = Color.White, fontWeight = FontWeight.Bold)
                        }
                    }


                    item {
                        var confetti by remember { mutableStateOf(listOf<Offset>()) }

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(120.dp)
                                .background(Color.DarkGray, RoundedCornerShape(20.dp))
                                .clickable {
                                    val newParticles = List(20) { Offset(Random.nextFloat() * 300, 0f) }
                                    confetti = confetti + newParticles
                                }
                        ) {
                            confetti.forEachIndexed { index, offset ->
                                var animY by remember { mutableStateOf(offset.y) }
                                LaunchedEffect(animY) {
                                    animate(animY, targetValue = 120f, animationSpec = tween(800)) { value, _ ->
                                        animY = value
                                    }
                                }
                                Box(
                                    modifier = Modifier
                                        .offset(x = offset.x.dp, y = animY.dp)
                                        .size(8.dp)
                                        .background(
                                            Color(Random.nextInt(0, 255), Random.nextInt(0, 255), Random.nextInt(0, 255)),
                                            CircleShape
                                        )
                                )
                            }
                            Text("Tap for Confetti!", color = Color.White, modifier = Modifier.align(Alignment.Center))
                        }
                    }


                    item {
                        var offsetX by remember { mutableStateOf(0f) }
                        var offsetY by remember { mutableStateOf(0f) }
                        var rotationX by remember { mutableStateOf(0f) }
                        var rotationY by remember { mutableStateOf(0f) }

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(120.dp)
                                .background(Color(0xFF03DAC5), RoundedCornerShape(16.dp))
                                .pointerInput(Unit) {
                                    detectDragGestures { change, dragAmount ->
                                        change.consume()
                                        offsetX += dragAmount.x
                                        offsetY += dragAmount.y
                                        rotationX = offsetY / 10
                                        rotationY = -offsetX / 10
                                    }
                                }
                                .graphicsLayer {
                                    translationX = offsetX
                                    translationY = offsetY
                                    rotationX = rotationX
                                    rotationY = rotationY
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Text("Drag & Tilt Me!", color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                        }
                    }


                    item {
                        val infiniteTransition = rememberInfiniteTransition()
                        val shimmer by infiniteTransition.animateFloat(
                            initialValue = 0f, targetValue = 1f,
                            animationSpec = infiniteRepeatable(tween(1000), RepeatMode.Restart)
                        )

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                                .clip(RoundedCornerShape(16.dp))
                                .background(
                                    Brush.horizontalGradient(
                                        listOf(Color.LightGray, Color.White.copy(alpha = 0.5f), Color.LightGray),
                                        startX = shimmer * 300
                                    )
                                )
                        )
                    }


                    item {
                        var textIndex by remember { mutableStateOf(0) }
                        val message = "This is a typing animation demo!"
                        LaunchedEffect(Unit) {
                            while (textIndex < message.length) {
                                textIndex++
                                delay(80)
                            }
                        }
                        Text(message.take(textIndex), fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    }


                    item {
                        val cards = remember { mutableStateListOf("Card 1", "Card 2", "Card 3", "Card 4") }
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            cards.reversed().forEachIndexed { index, card ->
                                var offsetX by remember { mutableStateOf(0f) }
                                var offsetY by remember { mutableStateOf(0f) }
                                var rotation by remember { mutableStateOf(0f) }

                                if (index == cards.lastIndex) {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth(0.8f)
                                            .height(160.dp)
                                            .graphicsLayer {
                                                translationX = offsetX
                                                translationY = offsetY
                                                rotationZ = rotation
                                                scaleX = 1f - index * 0.05f
                                                scaleY = 1f - index * 0.05f
                                            }
                                            .background(Color(0xFF6200EE + index * 0x00111111), RoundedCornerShape(16.dp))
                                            .pointerInput(Unit) {
                                                detectDragGestures(
                                                    onDragEnd = {
                                                        if (offsetX.absoluteValue > 150) {
                                                            cards.removeAt(cards.lastIndex)
                                                        } else {
                                                            offsetX = 0f
                                                            offsetY = 0f
                                                            rotation = 0f
                                                        }
                                                    }
                                                ) { change, dragAmount ->
                                                    change.consume()
                                                    offsetX += dragAmount.x
                                                    offsetY += dragAmount.y
                                                    rotation = offsetX / 10
                                                }
                                            },
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(card, color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                                    }
                                } else {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth(0.8f)
                                            .height(160.dp)
                                            .graphicsLayer {
                                                scaleX = 1f - index * 0.05f
                                                scaleY = 1f - index * 0.05f
                                                translationY = index * 16.dp.toPx()
                                            }
                                            .background(Color(0xFF6200EE + index * 0x00111111), RoundedCornerShape(16.dp)),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(card, color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                                    }
                                }
                            }
                        }
                    }


                    item {
                        var expanded by remember { mutableStateOf(false) }
                        val rotation by animateFloatAsState(
                            targetValue = if (expanded) 45f else 0f,
                            animationSpec = tween(durationMillis = 300)
                        )

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(100.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                AnimatedVisibility(
                                    visible = expanded,
                                    enter = fadeIn() + expandHorizontally(),
                                    exit = fadeOut() + shrinkHorizontally()
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .background(Color(0xFF03A9F4), RoundedCornerShape(16.dp))
                                            .padding(horizontal = 16.dp, vertical = 8.dp)
                                    ) {
                                        Text("Options Expanded!", color = Color.White)
                                    }
                                }

                                Box(
                                    modifier = Modifier
                                        .size(56.dp)
                                        .background(Color(0xFFFF4081), CircleShape)
                                        .clickable { expanded = !expanded },
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        painter = painterResource(HugeIcons.Add),
                                        contentDescription = "Add",
                                        tint = Color.White,
                                        modifier = Modifier.rotate(rotation)
                                    )
                                }
                            }
                        }
                    }

                    item {
                        var liked by remember { mutableStateOf(false) }
                        val infiniteTransition = rememberInfiniteTransition()
                        val pulse by infiniteTransition.animateFloat(
                            initialValue = 1f,
                            targetValue = if (liked) 1.2f else 1f,
                            animationSpec = infiniteRepeatable(
                                animation = keyframes {
                                    durationMillis = 800
                                    1.0f at 0 with LinearEasing
                                    1.2f at 400 with LinearEasing
                                    1.0f at 800 with LinearEasing
                                },
                                repeatMode = RepeatMode.Restart
                            )
                        )

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(80.dp)
                                .background(Color(0x22FF0000), RoundedCornerShape(16.dp))
                                .clickable { liked = !liked },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                Icons.Filled.Favorite,
                                contentDescription = "Heart",
                                tint = if (liked) Color(0xFFFF0000) else Color(0x88AAAAAA),
                                modifier = Modifier
                                    .size(40.dp)
                                    .scale(pulse)
                            )
                        }
                    }


                    item {
                        var waves by remember { mutableStateOf(listOf<WaveParticle>()) }

                        LaunchedEffect(Unit) {
                            while (true) {
                                if (waves.size < 15) {
                                    waves = waves + WaveParticle(
                                        offset = Offset(Random.nextFloat() * 300, 120f),
                                        progress = 0f,
                                        speed = Random.nextFloat() * 2 + 1f,
                                        size = Random.nextFloat() * 10 + 5f
                                    )
                                }
                                delay(200)
                            }
                        }

                        LaunchedEffect(waves) {
                            waves.forEachIndexed { index, wave ->
                                animate(
                                    initialValue = 0f,
                                    targetValue = 1f,
                                    animationSpec = tween(durationMillis = (2000 / wave.speed).toInt())
                                ) { value, _ ->
                                    waves = waves.toMutableList().apply {
                                        this[index] = wave.copy(progress = value)
                                    }
                                }
                            }
                        }

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(150.dp)
                                .background(Color(0xFF3F51B5), RoundedCornerShape(16.dp))
                                .pointerInput(Unit) {
                                    detectTapGestures {
                                        waves = waves + WaveParticle(
                                            offset = Offset(it.x, it.y),
                                            progress = 0f,
                                            speed = Random.nextFloat() * 2 + 1f,
                                            size = Random.nextFloat() * 10 + 5f
                                        )
                                    }
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Text("Tap for Ripple Effect!", color = Color.White)

                            Canvas(modifier = Modifier.fillMaxSize()) {
                                waves.forEach { wave ->
                                    drawCircle(
                                        color = Color.White.copy(alpha = 1 - wave.progress),
                                        center = Offset(wave.offset.x, wave.offset.y),
                                        radius = wave.size * wave.progress * 10
                                    )
                                }

                                waves = waves.filter { it.progress < 0.99f }
                            }
                        }
                    }


                    item {
                        var tilt by remember { mutableStateOf(Offset(0f, 0f)) }

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                                .pointerInput(Unit) {
                                    detectDragGestures { change, dragAmount ->
                                        tilt = Offset(
                                            x = (tilt.x + dragAmount.x / 10).coerceIn(-10f, 10f),
                                            y = (tilt.y + dragAmount.y / 10).coerceIn(-10f, 10f)
                                        )
                                    }
                                }
                        ) {

                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .offset(x = tilt.x.dp * 0.5f, y = tilt.y.dp * 0.5f)
                                    .background(
                                        Brush.radialGradient(
                                            colors = listOf(Color(0xFFE91E63), Color(0xFF9C27B0)),
                                            center = Offset(0.3f, 0.3f),
                                            radius = 300f
                                        ),
                                        RoundedCornerShape(16.dp)
                                    )
                            )


                            Box(
                                modifier = Modifier
                                    .align(Alignment.Center)
                                    .size(120.dp)
                                    .offset(x = tilt.x.dp * 1f, y = tilt.y.dp * 1f)
                                    .background(
                                        Brush.verticalGradient(
                                            colors = listOf(Color(0x99FFFFFF), Color(0x33FFFFFF))
                                        ),
                                        CircleShape
                                    )
                            )

                            Text(
                                "Parallax Effect",
                                color = Color.White,
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier
                                    .align(Alignment.Center)
                                    .offset(x = tilt.x.dp * 1.5f, y = tilt.y.dp * 1.5f)
                            )
                        }
                    }


                    item {
                        val infiniteTransition = rememberInfiniteTransition()
                        val bounce by infiniteTransition.animateFloat(
                            initialValue = 0f,
                            targetValue = 1f,
                            animationSpec = infiniteRepeatable(
                                animation = keyframes {
                                    durationMillis = 1000
                                    0.0f at 0 with LinearEasing
                                    1.0f at 500 with LinearEasing
                                    0.0f at 1000 with LinearEasing
                                }
                            )
                        )

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(100.dp)
                                .background(Color(0xFF212121), RoundedCornerShape(16.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {

                                listOf(0, 150, 300).forEach { delayMs ->
                                    val ballBounce by infiniteTransition.animateFloat(
                                        initialValue = 0f,
                                        targetValue = 1f,
                                        animationSpec = infiniteRepeatable(
                                            animation = keyframes {
                                                durationMillis = 1000
                                                0.0f at 0 with LinearEasing
                                                1.0f at 500 with LinearEasing
                                                0.0f at 1000 with LinearEasing
                                            },
                                            initialStartOffset = StartOffset(delayMs)
                                        )
                                    )

                                    Box(
                                        modifier = Modifier
                                            .size(20.dp)
                                            .offset(y = -(ballBounce * 30).dp)
                                            .background(Color(0xFF00BCD4), CircleShape)
                                    )
                                }
                            }
                        }
                    }


                    item {
                        val infiniteTransition = rememberInfiniteTransition()
                        val colorProgress by infiniteTransition.animateFloat(
                            initialValue = 0f,
                            targetValue = 1f,
                            animationSpec = infiniteRepeatable(
                                tween(4000, easing = LinearEasing),
                                RepeatMode.Reverse
                            )
                        )

                        val colors = listOf(
                            Color(0xFFFF5252),
                            Color(0xFFFF4081),
                            Color(0xFFE040FB),
                            Color(0xFF7C4DFF),
                            Color(0xFF536DFE),
                            Color(0xFF448AFF),
                            Color(0xFF40C4FF),
                            Color(0xFF18FFFF)
                        )

                        val currentColor = lerpColor(colors, colorProgress)

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(100.dp)
                                .background(
                                    Brush.linearGradient(
                                        listOf(currentColor, currentColor.copy(alpha = 0.8f))
                                    ),
                                    RoundedCornerShape(16.dp)
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                "Color Morphing",
                                color = Color.White,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }


                    item {
                        var rating by remember { mutableStateOf(0) }
                        val stars = listOf(1, 2, 3, 4, 5)

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(80.dp)
                                .background(Color(0xFFEEEEEE), RoundedCornerShape(16.dp)),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            stars.forEach { star ->
                                val scale by animateFloatAsState(
                                    targetValue = if (rating >= star) 1.2f else 1f,
                                    animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy)
                                )

                                Icon(
                                    Icons.Filled.Star,
                                    contentDescription = "Star",
                                    tint = if (rating >= star) Color(0xFFFFC107) else Color(0xFF9E9E9E),
                                    modifier = Modifier
                                        .size(40.dp)
                                        .scale(scale)
                                        .clickable { rating = star }
                                )
                            }
                        }
                    }

                    item {
                        var offset by remember { mutableStateOf(Offset.Zero) }
                        var isStuck by remember { mutableStateOf(false) }
                        val animatableOffset = remember { Animatable(Offset.Zero, Offset.VectorConverter) }
                        val coroutineScope = rememberCoroutineScope()

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(100.dp)
                                .background(Color(0xFFF5F5F5), RoundedCornerShape(16.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Box(
                                modifier = Modifier
                                    .offset { IntOffset(offset.x.toInt(), offset.y.toInt()) }
                                    .size(60.dp)
                                    .pointerInput(Unit) {
                                        detectDragGestures(
                                            onDragEnd = {
                                                val target = if (offset.x.absoluteValue < 100f && offset.y.absoluteValue < 100f) {
                                                    isStuck = false
                                                    Offset.Zero
                                                } else {
                                                    isStuck = true
                                                    Offset(
                                                        x = if (offset.x > 0) 150f else -150f,
                                                        y = if (offset.y > 0) 150f else -150f
                                                    )
                                                }

                                                coroutineScope.launch {
                                                    animatableOffset.animateTo(target, spring(dampingRatio = Spring.DampingRatioMediumBouncy))
                                                    offset = animatableOffset.value
                                                }
                                            }
                                        ) { change, dragAmount ->
                                            change.consume()
                                            offset += dragAmount
                                        }
                                    }
                                    .background(
                                        if (isStuck) Color(0xFFFF5722) else Color(0xFF3F51B5),
                                        CircleShape
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Text("Drag", color = Color.White)
                            }


                            Box(
                                modifier = Modifier
                                    .align(Alignment.TopStart)
                                    .padding(16.dp)
                                    .size(8.dp)
                                    .background(Color.Red, CircleShape)
                            )
                            Box(
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .padding(16.dp)
                                    .size(8.dp)
                                    .background(Color.Red, CircleShape)
                            )
                            Box(
                                modifier = Modifier
                                    .align(Alignment.BottomStart)
                                    .padding(16.dp)
                                    .size(8.dp)
                                    .background(Color.Red, CircleShape)
                            )
                            Box(
                                modifier = Modifier
                                    .align(Alignment.BottomEnd)
                                    .padding(16.dp)
                                    .size(8.dp)
                                    .background(Color.Red, CircleShape)
                            )
                        }
                    }



                    item {
                        var progress by remember { mutableStateOf(0f) }
                        val infiniteTransition = rememberInfiniteTransition()
                        val waveOffset by infiniteTransition.animateFloat(
                            initialValue = 0f,
                            targetValue = 1f,
                            animationSpec = infiniteRepeatable(
                                tween(2000, easing = LinearEasing),
                                RepeatMode.Restart
                            )
                        )
                        val coroutineScope = rememberCoroutineScope()

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(100.dp)
                                .background(Color(0xFFE0F7FA), RoundedCornerShape(16.dp))
                                .clickable {
                                    coroutineScope.launch {
                                        animate(
                                            initialValue = progress,
                                            targetValue = if (progress == 0f) 1f else 0f,
                                            animationSpec = tween(1000)
                                        ) { value, _ ->
                                            progress = value
                                        }
                                    }
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Canvas(modifier = Modifier.fillMaxSize()) {
                                val width = size.width
                                val height = size.height
                                val waveHeight = 20f
                                val adjustedProgress = progress * width

                                drawRect(Color(0xFF0097A7))


                                drawPath(
                                    Path().apply {
                                        moveTo(0f, height)
                                        lineTo(0f, height * 0.7f)

                                        for (x in 0 until width.toInt() step 5) {
                                            val y = height * 0.7f + sin((x / width * 20 + waveOffset * 10)) * waveHeight
                                            lineTo(x.toFloat(), y)
                                        }

                                        lineTo(width, height)
                                        close()
                                    },
                                    color = Color(0xFF00BCD4)
                                )

                                drawCircle(
                                    color = Color(0xFFB2EBF2),
                                    center = Offset(adjustedProgress, height * 0.7f),
                                    radius = 30f * progress
                                )
                            }

                            Text(
                                "Liquid Swipe →",
                                color = Color.White,
                                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                                modifier = Modifier.padding(start = 30.dp)
                            )
                        }
                    }



                    item {
                        var rotationAngle by remember { mutableStateOf(0f) }
                        val infiniteTransition = rememberInfiniteTransition()
                        val rotation by infiniteTransition.animateFloat(
                            initialValue = 0f,
                            targetValue = 360f,
                            animationSpec = infiniteRepeatable(tween(10000, easing = LinearEasing))
                        )

                        LaunchedEffect(Unit) {
                            while (true) {
                                rotationAngle += 0.5f
                                if (rotationAngle > 360f) rotationAngle = 0f
                                delay(16)
                            }
                        }

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(150.dp)
                                .graphicsLayer {
                                    rotationZ = rotation / 10
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Canvas(modifier = Modifier.fillMaxSize()) {
                                val width = size.width
                                val height = size.height

                                rotate(rotationAngle) {
                                    drawRect(
                                        brush = Brush.sweepGradient(
                                            center = Offset(width / 2, height / 2),
                                            colors = listOf(
                                                Color(0xFFFF00FF),
                                                Color(0xFF00FFFF),
                                                Color(0xFFFFFF00),
                                                Color(0xFFFF00FF)
                                            )
                                        ),
                                        alpha = 0.7f
                                    )
                                }


                                drawRect(
                                    color = Color.Black.copy(alpha = 0.7f),
                                    topLeft = Offset(20f, 20f),
                                    size = Size(width - 40f, height - 40f)
                                )
                            }

                            Text(
                                "HOLO CARD",
                                color = Color.White,
                                fontSize = 24.sp,
                                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                                letterSpacing = 4.sp
                            )
                        }
                    }



                    item {
                        var isCircle by remember { mutableStateOf(true) }
                        val cornerRadius by animateFloatAsState(
                            targetValue = if (isCircle) 100f else 16f,
                            animationSpec = tween(1000, easing = FastOutSlowInEasing)
                        )
                        val rotation by animateFloatAsState(
                            targetValue = if (isCircle) 0f else 45f,
                            animationSpec = tween(1000, easing = FastOutSlowInEasing)
                        )

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(120.dp)
                                .clickable { isCircle = !isCircle },
                            contentAlignment = Alignment.Center
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(80.dp)
                                    .graphicsLayer {
                                        rotationZ = rotation
                                    }
                                    .background(
                                        color = Color(0xFFFF9800),
                                        shape = RoundedCornerShape(cornerRadius.dp)
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    if (isCircle) "○" else "□",
                                    color = Color.White,
                                    fontSize = 30.sp
                                )
                            }
                        }
                    }


                    item {
                        val density = LocalDensity.current
                        var particles by remember { mutableStateOf(listOf<com.naulian.composable.screens.animations.Particle>()) }

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(150.dp)
                                .background(Color(0xFF263238), RoundedCornerShape(16.dp))
                                .clickable {

                                    particles = List(15) {
                                        com.naulian.composable.screens.animations.Particle(
                                            position = Offset(
                                                x = Random.nextFloat() * with(density) { 300.dp.toPx() },
                                                y = 0f
                                            ),
                                            velocity = Offset(0f, 0f),
                                            size = Random.nextFloat() * 10 + 5f,
                                            color = Color(
                                                red = Random.nextInt(180, 255),
                                                green = Random.nextInt(180, 255),
                                                blue = Random.nextInt(180, 255)
                                            )
                                        )
                                    }
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Text("Tap for Gravity", color = Color.White)

                            Canvas(modifier = Modifier.fillMaxSize()) {
                                particles = particles.map { particle ->
                                    var newVelocity: Offset = particle.velocity + Offset(0f, 0.2f)
                                    var newPosition: Offset = particle.position + newVelocity


                                    if (newPosition.y > size.height - particle.size) {
                                        newPosition = Offset(newPosition.x, size.height - particle.size)
                                        newVelocity = Offset(newVelocity.x, -newVelocity.y * 0.8f)
                                    }


                                    if (newPosition.x < particle.size || newPosition.x > size.width - particle.size) {
                                        newVelocity = Offset(-newVelocity.x * 0.8f, newVelocity.y)
                                    }

                                    drawCircle(
                                        color = particle.color,
                                        center = newPosition,
                                        radius = particle.size
                                    )

                                    particle.copy(position = newPosition, velocity = newVelocity)
                                }
                            }

                            LaunchedEffect(particles) {
                                while (true) {
                                    delay(16)
                                    particles = particles
                                }
                            }
                        }
                    }



                    item {
                        val infiniteTransition = rememberInfiniteTransition()
                        val pulse by infiniteTransition.animateFloat(
                            initialValue = 0f,
                            targetValue = 1f,
                            animationSpec = infiniteRepeatable(
                                tween(2000, easing = LinearEasing),
                                RepeatMode.Reverse
                            )
                        )

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(180.dp)
                                .background(Color.Black, RoundedCornerShape(16.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Canvas(modifier = Modifier.fillMaxSize()) {
                                val width = size.width
                                val height = size.height
                                val nodes = 5
                                val layers = 3


                                for (layer in 0 until layers - 1) {
                                    for (i in 0 until nodes) {
                                        for (j in 0 until nodes) {
                                            val startX = (layer + 1) * width / (layers + 1)
                                            val endX = (layer + 2) * width / (layers + 1)
                                            val startY = (i + 1) * height / (nodes + 1)
                                            val endY = (j + 1) * height / (nodes + 1)

                                            val alpha = 0.3f + 0.7f * (1 - (abs(i - j) / nodes.toFloat()))

                                            drawLine(
                                                color = Color.Green.copy(alpha = alpha * pulse),
                                                start = Offset(startX, startY),
                                                end = Offset(endX, endY),
                                                strokeWidth = 1f
                                            )
                                        }
                                    }
                                }


                                for (layer in 0 until layers) {
                                    for (i in 0 until nodes) {
                                        val x = (layer + 1) * width / (layers + 1)
                                        val y = (i + 1) * height / (nodes + 1)
                                        val size = 12f + 8f * sin(pulse * 6.28f + layer + i)

                                        drawCircle(
                                            color = Color.Cyan,
                                            center = Offset(x, y),
                                            radius = size
                                        )
                                    }
                                }
                            }

                            Text("Neural Network", color = Color.White, modifier = Modifier.padding(8.dp))
                        }
                    }

                    item {
                        var glitch by remember { mutableStateOf(false) }
                        val infiniteTransition = rememberInfiniteTransition()
                        val offset by infiniteTransition.animateFloat(
                            initialValue = 0f,
                            targetValue = 1f,
                            animationSpec = infiniteRepeatable(
                                tween(100, easing = LinearEasing),
                                RepeatMode.Restart
                            )
                        )

                        LaunchedEffect(glitch) {
                            if (glitch) {
                                delay(500)
                                glitch = false
                            }
                        }

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(80.dp)
                                .background(Color(0xFF121212), RoundedCornerShape(16.dp))
                                .clickable { glitch = true },
                            contentAlignment = Alignment.Center
                        ) {
                            if (glitch) {
                                Text(
                                    "GL!TCH 3FF3CT",
                                    color = Color.Red,
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.offset(x = (-2 + offset * 4).dp, y = (-2 + offset * 4).dp)
                                )
                                Text(
                                    "GL!TCH 3FF3CT",
                                    color = Color.Blue,
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.offset(x = (2 - offset * 4).dp, y = (2 - offset * 4).dp)
                                )
                            }

                            Text(
                                "GLITCH EFFECT",
                                color = Color.White,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    item {
                        val infiniteTransition = rememberInfiniteTransition()
                        val auroraShift by infiniteTransition.animateFloat(
                            initialValue = 0f,
                            targetValue = 1f,
                            animationSpec = infiniteRepeatable(
                                tween(8000, easing = LinearEasing),
                                RepeatMode.Restart
                            )
                        )

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(120.dp)
                                .clip(RoundedCornerShape(16.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Canvas(modifier = Modifier.fillMaxSize()) {
                                val width = size.width
                                val height = size.height

                                for (i in 0 until 5) {
                                    drawRect(
                                        brush = Brush.verticalGradient(
                                            colors = listOf(
                                                Color.Transparent,
                                                Color.hsl((auroraShift * 360 + i * 30) % 360, 0.8f, 0.5f, 0.7f),
                                                Color.Transparent
                                            ),
                                            startY = height * (i / 5f) - auroraShift * height / 2,
                                            endY = height * ((i + 1) / 5f) - auroraShift * height / 2
                                        )
                                    )
                                }
                            }

                            Text(
                                "Aurora Effect",
                                color = Color.White,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.background(Color.Black.copy(alpha = 0.5f), RoundedCornerShape(8.dp))
                                    .padding(8.dp)
                            )
                        }
                    }

                    item {
                        val infiniteTransition = rememberInfiniteTransition()
                        val progress by infiniteTransition.animateFloat(
                            initialValue = 0f,
                            targetValue = 1f,
                            animationSpec = infiniteRepeatable(
                                tween(2000, easing = LinearEasing),
                                RepeatMode.Restart
                            )
                        )

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(80.dp)
                                .background(Color(0xFFE0E0E0), RoundedCornerShape(16.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth(0.8f)
                                    .height(8.dp)
                                    .background(Color(0xFFBDBDBD), RoundedCornerShape(4.dp))
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth(progress)
                                        .height(8.dp)
                                        .background(
                                            Brush.horizontalGradient(
                                                listOf(Color(0xFF00BCD4), Color(0xFF3F51B5))
                                            ),
                                            RoundedCornerShape(4.dp)
                                        )
                                )
                            }

                            Text(
                                "Loading... ${(progress * 100).toInt()}%",
                                modifier = Modifier.padding(top = 32.dp),
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }



        data class WaveParticle(
            val offset: Offset,
            val progress: Float,
            val speed: Float,
            val size: Float
        )


        fun lerpColor(colors: List<Color>, progress: Float): Color {
            val segment = 1f / (colors.size - 1)
            val index = (progress / segment).toInt().coerceIn(0, colors.size - 2)
            val segmentProgress = (progress - index * segment) / segment

            return androidx.compose.ui.graphics.lerp(
                colors[index],
                colors[index + 1],
                segmentProgress
            )
        }

        @Preview(showBackground = true)
        @Composable
        fun AnimationsInteractionsScreenUIPreview() {
            ComposableTheme {
                AnimationsInteractionsScreenUI()
            }
        }

    """.trimIndent()
}


