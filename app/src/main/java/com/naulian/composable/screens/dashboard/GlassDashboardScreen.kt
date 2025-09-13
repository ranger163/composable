package com.naulian.composable.screens.dashboard
import kotlin.random.Random
import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.*
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlin.math.absoluteValue
import kotlin.math.cos
import kotlin.math.sin
import androidx.compose.ui.input.pointer.pointerInput
import com.naulian.composable.component.CodeBlock
import com.naulian.composable.screens.box.corneredBoxCode
import com.naulian.composable.screens.rating.RatingStarScreenUI
import com.naulian.composable.theme.ComposableTheme
import androidx.compose.ui.window.Dialog
import com.naulian.composable.LocalNavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GlassDashboardScreen(onBack: () -> Unit = {}) {
    val code = remember { GlassDashboard }
    var selectedTab by remember { mutableStateOf(0) }
    var showCode by remember { mutableStateOf(false) }
    val clipboardManager = LocalClipboardManager.current
    val context = LocalContext.current
    val dashboardSourceCode = ""
    val navController = LocalNavController.current

    val infiniteTransition = rememberInfiniteTransition()
    val animatedGradient by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(8000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )



    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.linearGradient(
                    colors = listOf(
                        Color(0xFF141E30),  
                        Color(0xFF243B55),  
                        Color(0xFF141E30)   
                    ),
                    start = androidx.compose.ui.geometry.Offset(0f, 0f),
                    end = androidx.compose.ui.geometry.Offset(
                        x = 1000f * sin(animatedGradient * Math.PI.toFloat()).absoluteValue,
                        y = 1000f * cos(animatedGradient * Math.PI.toFloat()).absoluteValue
                    )
                )
            )
    ) {

        AnimatedParticles(particleCount = 15)

        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            
            TopAppBar(
                title = { Text("Liquid Glass Dashboard", color = Color.White) },
                navigationIcon = {
                    IconButton(
                        onClick = { navController.popBackStack() }
                    ) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { showCode = !showCode }) {
                        Icon(
                            Icons.Default.Code,
                            contentDescription = "Show Code",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                )
            )


            if (showCode) {
                Dialog(
                    onDismissRequest = { showCode = false }
                ) {

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        LiquidGlassCard(height = 400.dp) {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(16.dp),
                                verticalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                Text(
                                    "Source Code",
                                    color = Color.White,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold
                                )

                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .weight(1f)
                                        .clip(RoundedCornerShape(12.dp))
                                        .background(Color.Black.copy(alpha = 0.7f))
                                        .border(1.dp, Color.White.copy(alpha = 0.3f), RoundedCornerShape(12.dp))
                                        .verticalScroll(rememberScrollState())
                                        .padding(16.dp)
                                ) {
                                    Text(
                                        text = code,
                                        color = Color.White,
                                        fontSize = 12.sp,
                                        lineHeight = 16.sp
                                    )
                                }

                                Button(
                                    onClick = {
                                        clipboardManager.setText(AnnotatedString(code))
                                    },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color(0xFF6DD5FA),
                                        contentColor = Color.White
                                    ),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Icon(Icons.Default.ContentCopy, contentDescription = "Copy")
                                    Spacer(Modifier.width(8.dp))
                                    Text("Copy Code")
                                }
                            }
                        }
                    }
                }
            }



            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(start = 16.dp, end = 16.dp, bottom = 80.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                LiquidGlassCard(width = 340.dp, height = 120.dp) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(60.dp)
                                .clip(CircleShape)
                                .liquidGlassEffect(
                                    shape = CircleShape,
                                    borderColor = Color.White.copy(alpha = 0.4f),
                                    intensity = 0.3f,   
                                    blurRadius = 0.dp    
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("SB", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                        }


                        Spacer(Modifier.width(16.dp))
                        Column {
                            Text("Welcome Back", color = Color.White.copy(alpha = 0.8f), fontSize = 14.sp)
                            Text(
                                "Shree Bhargav",
                                color = Color.White,
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }

                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    LiquidGlassCard("Revenue", "$12,345", 160.dp, 100.dp)
                    LiquidGlassCard("Orders", "1,234", 160.dp, 100.dp)
                }
                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    LiquidGlassCard("Customers", "567", 160.dp, 100.dp)
                    LiquidGlassCard("Growth", "📈 12%", 160.dp, 100.dp)
                }

                LiquidGlassCard("Monthly Progress", "🔥 85%", 340.dp, 160.dp, showProgressBar = true)

                LiquidGlassCard(width = 340.dp, height = 120.dp) {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text("Notifications", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Notifications, contentDescription = null, tint = Color.White, modifier = Modifier.size(18.dp))
                            Spacer(Modifier.width(8.dp))
                            Text("3 New Alerts", color = Color.White.copy(alpha = 0.9f), fontSize = 14.sp)
                        }
                    }
                }

                LiquidGlassCard(width = 340.dp, height = 140.dp) {
                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Text("Upcoming Events", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        Text("• Team Meeting - 10:00 AM", color = Color.White.copy(alpha = 0.9f), fontSize = 14.sp)
                        Text("• Client Call - 2:00 PM", color = Color.White.copy(alpha = 0.9f), fontSize = 14.sp)
                        Text("• Review - 5:30 PM", color = Color.White.copy(alpha = 0.9f), fontSize = 14.sp)
                    }
                }

                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    QuickActionButton(Icons.Default.Settings, "Settings")
                    QuickActionButton(Icons.Default.Share, "Share")
                    QuickActionButton(Icons.Default.ExitToApp, "Logout")
                }

                CodeBlock(source = code, language = "kotlin")
            }




            BottomLiquidGlassNavBar(
                items = listOf(
                    Icons.Default.Home to "Home",
                    Icons.Default.Info to "Reports",
                    Icons.Default.Person to "Profile",
                    Icons.Default.Notifications to "Alerts"
                ),
                selectedIndex = selectedTab,
                onItemSelected = { selectedTab = it }
            )
        }

    }


}

@Composable
fun LiquidGlassCard(
    title: String? = null,
    value: String? = null,
    width: Dp,
    height: Dp,
    showProgressBar: Boolean = false,
    content: @Composable (() -> Unit)? = null
) {

    val infiniteTransition = rememberInfiniteTransition()
    val floatingOffset by infiniteTransition.animateFloat(
        initialValue = -2f,
        targetValue = 2f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    Box(
        modifier = Modifier
            .width(width)
            .height(height)
            .graphicsLayer {
                translationY = floatingOffset
            }
    ) {
        
        Box(
            modifier = Modifier
                .matchParentSize()
                .liquidGlassEffect(
                    shape = RoundedCornerShape(28.dp),
                    borderColor = Color.White.copy(alpha = 0.4f),
                    intensity = 0.9f
                )
        )

        
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            if (content != null) {
                content()
            } else {
                if (title != null) {
                    Text(title, color = Color.White.copy(alpha = 0.9f), fontSize = 16.sp)
                }
                if (value != null) {
                    Text(value, color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                }
                if (showProgressBar) {
                    Spacer(Modifier.height(12.dp))
                    LiquidProgressBar(progress = 0.85f)
                }
            }
        }
    }
}

@Composable
fun LiquidGlassCard(
    title: String? = null,
    value: String? = null,
    height: Dp,
    showProgressBar: Boolean = false,
    content: @Composable (() -> Unit)? = null
) {
    val infiniteTransition = rememberInfiniteTransition()
    val floatingOffset by infiniteTransition.animateFloat(
        initialValue = -2f,
        targetValue = 2f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    Box(
        modifier = Modifier
            .fillMaxWidth() 
            .height(height)
            .graphicsLayer {
                translationY = floatingOffset
            }
    ) {
        
        Box(
            modifier = Modifier
                .matchParentSize()
                .liquidGlassEffect(
                    shape = RoundedCornerShape(28.dp),
                    borderColor = Color.White.copy(alpha = 0.4f),
                    intensity = 0.9f
                )
        )

        
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            content?.invoke() ?: run {
                if (title != null) {
                    Text(title, color = Color.White.copy(alpha = 0.9f), fontSize = 16.sp)
                }
                if (value != null) {
                    Text(value, color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                }
                if (showProgressBar) {
                    Spacer(Modifier.height(12.dp))
                    LiquidProgressBar(progress = 0.85f)
                }
            }
        }
    }
}

@Composable
fun Modifier.liquidGlassEffect(
    shape: androidx.compose.ui.graphics.Shape,
    borderColor: Color,
    intensity: Float = 0.7f,
    blurRadius: Dp = 0.dp 
): Modifier {
    return this
        .clip(shape)
        .background(
            Brush.radialGradient(
                colors = listOf(
                    Color.White.copy(alpha = 0.15f * intensity),
                    Color.White.copy(alpha = 0.05f * intensity),
                    Color.Transparent
                ),
                center = androidx.compose.ui.geometry.Offset(0.3f, 0.3f),
                radius = 500f
            )
        )
        .drawBehind {
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        Color.White.copy(alpha = 0.2f * intensity),
                        Color.Transparent
                    ),
                    center = androidx.compose.ui.geometry.Offset(0.3f, 0.3f),
                    radius = size.width * 0.8f
                ),
                radius = size.width * 0.7f,
                center = androidx.compose.ui.geometry.Offset(size.width * 0.3f, size.height * 0.3f)
            )
        }
        .border(BorderStroke(1.dp, borderColor), shape)

        .then(if (blurRadius > 0.dp) Modifier.blur(blurRadius) else Modifier)
}


@Composable
fun LiquidProgressBar(progress: Float) {
    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(1000, easing = FastOutSlowInEasing)
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(12.dp)
            .clip(RoundedCornerShape(6.dp))
            .background(Color.White.copy(alpha = 0.15f))
    ) {
        
        Box(
            modifier = Modifier
                .fillMaxWidth(animatedProgress)
                .fillMaxHeight()
                .clip(RoundedCornerShape(6.dp))
                .drawBehind {
                    
                    drawRect(
                        brush = Brush.linearGradient(
                            colors = listOf(
                                Color(0xFF6DD5FA).copy(alpha = 0.9f),
                                Color(0xFF2980B9).copy(alpha = 0.9f),
                                Color(0xFF6DD5FA).copy(alpha = 0.9f)
                            ),
                            start = androidx.compose.ui.geometry.Offset(0f, 0f),
                            end = androidx.compose.ui.geometry.Offset(size.width * 2, size.height * 2)
                        )
                    )

                    
                    drawCircle(
                        color = Color.White.copy(alpha = 0.3f),
                        radius = size.height * 0.4f,
                        center = androidx.compose.ui.geometry.Offset(size.width * 0.8f, size.height * 0.3f)
                    )
                }
        )

        
        val infiniteTransition = rememberInfiniteTransition()
        val ripple by infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = tween(2000, easing = LinearEasing)
            )
        )

        Box(
            modifier = Modifier
                .fillMaxWidth(animatedProgress)
                .fillMaxHeight()
                .drawBehind {
                    
                    drawRect(
                        color = Color.White.copy(alpha = 0.2f),
                        topLeft = androidx.compose.ui.geometry.Offset(size.width * ripple - 20f, 0f),
                        size = androidx.compose.ui.geometry.Size(40f, size.height)
                    )
                }
        )
    }
}

@Composable
fun QuickActionButton(icon: ImageVector, label: String) {
    var isHovered by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(if (isHovered) 1.1f else 1f)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
            }
            .pointerInput(Unit) {
                awaitPointerEventScope {
                    while (true) {
                        val event = awaitPointerEvent()
                        isHovered = event.changes.any { it.pressed }
                    }
                }
            }
    ) {
        Box(
            modifier = Modifier
                .size(60.dp)
                .liquidGlassEffect(
                    shape = RoundedCornerShape(20.dp),
                    borderColor = Color.White.copy(alpha = 0.4f),
                    intensity = 0.3f,
                    blurRadius = 0.dp  
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(icon, contentDescription = label, tint = Color.White, modifier = Modifier.size(24.dp))
        }

        Spacer(Modifier.height(6.dp))
        Text(label, color = Color.White, fontSize = 12.sp)
    }
}


@Composable
fun BottomLiquidGlassNavBar(
    items: List<Pair<ImageVector, String>>,
    selectedIndex: Int,
    onItemSelected: (Int) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(90.dp)
            .padding(12.dp)
    ) {

        Box(
            modifier = Modifier
                .matchParentSize()
                .liquidGlassEffect(
                    shape = RoundedCornerShape(30.dp),
                    borderColor = Color.White.copy(alpha = 0.5f),
                    intensity = 1f
                )
        )

        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            items.forEachIndexed { index, item ->
                
                val isSelected = selectedIndex == index
                val scale by animateFloatAsState(if (isSelected) 1.15f else 1f)

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .weight(1f)
                        .clickable { onItemSelected(index) }
                        .graphicsLayer {
                            scaleX = scale
                            scaleY = scale
                        }
                ) {
                    Icon(
                        item.first,
                        contentDescription = item.second,
                        tint = if (isSelected) Color.Cyan else Color.White.copy(alpha = 0.8f),
                        modifier = Modifier.size(28.dp)
                    )
                    Text(
                        item.second,
                        fontSize = 12.sp,
                        color = if (isSelected) Color.Cyan else Color.White.copy(alpha = 0.8f)
                    )
                }
            }
        }
    }
}

@Composable
fun AnimatedParticles(particleCount: Int) {
    val particles = remember { List(particleCount) { Particle() } }

    Box(modifier = Modifier.fillMaxSize()) {
        particles.forEach { particle ->
            var position by remember { mutableStateOf(particle.reset()) }

            LaunchedEffect(particle) {
                while (true) {
                    position = particle.update()
                    delay(16) 
                }
            }

            Box(
                modifier = Modifier
                    .offset(x = position.x.dp, y = position.y.dp)
                    .size(particle.size.dp)
                    .graphicsLayer {
                        alpha = particle.opacity
                        rotationZ = particle.rotation
                    }
                    .background(
                        color = Color.White.copy(alpha = particle.opacity * 0.3f),
                        shape = CircleShape
                    )
            )
        }
    }
}

data class Particle(
    var x: Float = 0f,
    var y: Float = 0f,
    var size: Float = 0f,
    var speed: Float = 0f,
    var direction: Float = 0f,
    var opacity: Float = 0f,
    var rotation: Float = 0f,
    var rotationSpeed: Float = 0f
) {
    fun reset(): androidx.compose.ui.geometry.Offset {
        x = (0..1000).random().toFloat()
        y = (0..2000).random().toFloat()
        size = (2..8).random().toFloat()
        speed = Random.nextFloat() * (1.5f - 0.2f) + 0.2f
        direction = (0..360).random().toFloat()
        opacity = Random.nextFloat() * (0.4f - 0.1f) + 0.1f
        rotation = (0..360).random().toFloat()
        rotationSpeed = Random.nextFloat() * (2f - (-2f)) + (-2f)
        return androidx.compose.ui.geometry.Offset(x, y)
    }

    fun update(): androidx.compose.ui.geometry.Offset {
        x += cos(Math.toRadians(direction.toDouble())).toFloat() * speed
        y += sin(Math.toRadians(direction.toDouble())).toFloat() * speed
        rotation += rotationSpeed

        
        if (x < -100f || x > 1100f || y < -100f || y > 2100f) {
            reset()
        }

        return androidx.compose.ui.geometry.Offset(x, y)
    }
}

@Preview
@Composable
private fun GlassDashboardScreenPreview() {
    ComposableTheme {
        GlassDashboardScreen()
    }
}

val GlassDashboard by lazy {
    """
        @OptIn(ExperimentalMaterial3Api::class)
        @Composable
        fun GlassDashboardScreen(onBack: () -> Unit = {}) {
            val code = remember { GlassDashboard }
            var selectedTab by remember { mutableStateOf(0) }
            var showCode by remember { mutableStateOf(false) }
            val clipboardManager = LocalClipboardManager.current
            val context = LocalContext.current
            val dashboardSourceCode = ""
            val infiniteTransition = rememberInfiniteTransition()
            val animatedGradient by infiniteTransition.animateFloat(
                initialValue = 0f,
                targetValue = 1f,
                animationSpec = infiniteRepeatable(
                    animation = tween(8000, easing = LinearEasing),
                    repeatMode = RepeatMode.Reverse
                )
            )



            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.linearGradient(
                            colors = listOf(
                                Color(0xFF141E30),  
                                Color(0xFF243B55),  
                                Color(0xFF141E30)   
                            ),
                            start = androidx.compose.ui.geometry.Offset(0f, 0f),
                            end = androidx.compose.ui.geometry.Offset(
                                x = 1000f * sin(animatedGradient * Math.PI.toFloat()).absoluteValue,
                                y = 1000f * cos(animatedGradient * Math.PI.toFloat()).absoluteValue
                            )
                        )
                    )
            ) {

                AnimatedParticles(particleCount = 15)

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    
                    TopAppBar(
                        title = { Text("Liquid Glass Dashboard", color = Color.White) },
                        navigationIcon = {
                            IconButton(onClick = onBack) {
                                Icon(
                                    Icons.Default.ArrowBack,
                                    contentDescription = "Back",
                                    tint = Color.White
                                )
                            }
                        },
                        actions = {
                            IconButton(onClick = { showCode = !showCode }) {
                                Icon(
                                    Icons.Default.Code,
                                    contentDescription = "Show Code",
                                    tint = Color.White
                                )
                            }
                        },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = Color.Transparent
                        )
                    )

                    if (shoCode) {
                        Dialog(
                            onDismissRequest = { showCode = false }
                        ) {

                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                            ) {
                                LiquidGlassCard(height = 400.dp) {
                                    Column(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .padding(16.dp),
                                        verticalArrangement = Arrangement.spacedBy(16.dp)
                                    ) {
                                        Text(
                                            "Source Code",
                                            color = Color.White,
                                            fontSize = 18.sp,
                                            fontWeight = FontWeight.Bold
                                        )

                                        Box(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .weight(1f)
                                                .clip(RoundedCornerShape(12.dp))
                                                .background(Color.Black.copy(alpha = 0.7f))
                                                .border(1.dp, Color.White.copy(alpha = 0.3f), RoundedCornerShape(12.dp))
                                                .verticalScroll(rememberScrollState())
                                                .padding(16.dp)
                                        ) {
                                            Text(
                                                text = code,
                                                color = Color.White,
                                                fontSize = 12.sp,
                                                lineHeight = 16.sp
                                            )
                                        }

                                        Button(
                                            onClick = {
                                                clipboardManager.setText(AnnotatedString(code))
                                            },
                                            colors = ButtonDefaults.buttonColors(
                                                containerColor = Color(0xFF6DD5FA),
                                                contentColor = Color.White
                                            ),
                                            modifier = Modifier.fillMaxWidth()
                                        ) {
                                            Icon(Icons.Default.ContentCopy, contentDescription = "Copy")
                                            Spacer(Modifier.width(8.dp))
                                            Text("Copy Code")
                                        }
                                    }
                                }
                            }
                        }
                    }



                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .verticalScroll(rememberScrollState())
                            .padding(start = 16.dp, end = 16.dp, bottom = 80.dp),
                        verticalArrangement = Arrangement.spacedBy(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        LiquidGlassCard(width = 340.dp, height = 120.dp) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(60.dp)
                                        .clip(CircleShape)
                                        .liquidGlassEffect(
                                            shape = CircleShape,
                                            borderColor = Color.White.copy(alpha = 0.4f),
                                            intensity = 0.3f,   
                                            blurRadius = 0.dp    
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text("SB", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                                }


                                Spacer(Modifier.width(16.dp))
                                Column {
                                    Text("Welcome Back", color = Color.White.copy(alpha = 0.8f), fontSize = 14.sp)
                                    Text(
                                        "Shree Bhargav",
                                        color = Color.White,
                                        fontSize = 22.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }

                        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                            LiquidGlassCard("Revenue", "${'$'}12,345", 160.dp, 100.dp)
                            LiquidGlassCard("Orders", "1,234", 160.dp, 100.dp)
                        }
                        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                            LiquidGlassCard("Customers", "567", 160.dp, 100.dp)
                            LiquidGlassCard("Growth", "📈 12%", 160.dp, 100.dp)
                        }

                        LiquidGlassCard("Monthly Progress", "🔥 85%", 340.dp, 160.dp, showProgressBar = true)

                        LiquidGlassCard(width = 340.dp, height = 120.dp) {
                            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                Text("Notifications", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(Icons.Default.Notifications, contentDescription = null, tint = Color.White, modifier = Modifier.size(18.dp))
                                    Spacer(Modifier.width(8.dp))
                                    Text("3 New Alerts", color = Color.White.copy(alpha = 0.9f), fontSize = 14.sp)
                                }
                            }
                        }

                        LiquidGlassCard(width = 340.dp, height = 140.dp) {
                            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                                Text("Upcoming Events", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                                Text("• Team Meeting - 10:00 AM", color = Color.White.copy(alpha = 0.9f), fontSize = 14.sp)
                                Text("• Client Call - 2:00 PM", color = Color.White.copy(alpha = 0.9f), fontSize = 14.sp)
                                Text("• Review - 5:30 PM", color = Color.White.copy(alpha = 0.9f), fontSize = 14.sp)
                            }
                        }

                        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                            QuickActionButton(Icons.Default.Settings, "Settings")
                            QuickActionButton(Icons.Default.Share, "Share")
                            QuickActionButton(Icons.Default.ExitToApp, "Logout")
                        }

                        CodeBlock(source = code, language = "kotlin")
                    }

        


                    BottomLiquidGlassNavBar(
                        items = listOf(
                            Icons.Default.Home to "Home",
                            Icons.Default.Info to "Reports",
                            Icons.Default.Person to "Profile",
                            Icons.Default.Notifications to "Alerts"
                        ),
                        selectedIndex = selectedTab,
                        onItemSelected = { selectedTab = it }
                    )
                }

            }


        }

        @Composable
        fun LiquidGlassCard(
            title: String? = null,
            value: String? = null,
            width: Dp,
            height: Dp,
            showProgressBar: Boolean = false,
            content: @Composable (() -> Unit)? = null
        ) {

            val infiniteTransition = rememberInfiniteTransition()
            val floatingOffset by infiniteTransition.animateFloat(
                initialValue = -2f,
                targetValue = 2f,
                animationSpec = infiniteRepeatable(
                    animation = tween(3000, easing = FastOutSlowInEasing),
                    repeatMode = RepeatMode.Reverse
                )
            )

            Box(
                modifier = Modifier
                    .width(width)
                    .height(height)
                    .graphicsLayer {
                        translationY = floatingOffset
                    }
            ) {
                
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .liquidGlassEffect(
                            shape = RoundedCornerShape(28.dp),
                            borderColor = Color.White.copy(alpha = 0.4f),
                            intensity = 0.9f
                        )
                )

                
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    if (content != null) {
                        content()
                    } else {
                        if (title != null) {
                            Text(title, color = Color.White.copy(alpha = 0.9f), fontSize = 16.sp)
                        }
                        if (value != null) {
                            Text(value, color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                        }
                        if (showProgressBar) {
                            Spacer(Modifier.height(12.dp))
                            LiquidProgressBar(progress = 0.85f)
                        }
                    }
                }
            }
        }

        @Composable
        fun LiquidGlassCard(
            title: String? = null,
            value: String? = null,
            height: Dp,
            showProgressBar: Boolean = false,
            content: @Composable (() -> Unit)? = null
        ) {
            val infiniteTransition = rememberInfiniteTransition()
            val floatingOffset by infiniteTransition.animateFloat(
                initialValue = -2f,
                targetValue = 2f,
                animationSpec = infiniteRepeatable(
                    animation = tween(3000, easing = FastOutSlowInEasing),
                    repeatMode = RepeatMode.Reverse
                )
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth() 
                    .height(height)
                    .graphicsLayer {
                        translationY = floatingOffset
                    }
            ) {
                
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .liquidGlassEffect(
                            shape = RoundedCornerShape(28.dp),
                            borderColor = Color.White.copy(alpha = 0.4f),
                            intensity = 0.9f
                        )
                )

                
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    content?.invoke() ?: run {
                        if (title != null) {
                            Text(title, color = Color.White.copy(alpha = 0.9f), fontSize = 16.sp)
                        }
                        if (value != null) {
                            Text(value, color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                        }
                        if (showProgressBar) {
                            Spacer(Modifier.height(12.dp))
                            LiquidProgressBar(progress = 0.85f)
                        }
                    }
                }
            }
        }

        @Composable
        fun Modifier.liquidGlassEffect(
            shape: androidx.compose.ui.graphics.Shape,
            borderColor: Color,
            intensity: Float = 0.7f,
            blurRadius: Dp = 0.dp 
        ): Modifier {
            return this
                .clip(shape)
                .background(
                    Brush.radialGradient(
                        colors = listOf(
                            Color.White.copy(alpha = 0.15f * intensity),
                            Color.White.copy(alpha = 0.05f * intensity),
                            Color.Transparent
                        ),
                        center = androidx.compose.ui.geometry.Offset(0.3f, 0.3f),
                        radius = 500f
                    )
                )
                .drawBehind {
                    drawCircle(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                Color.White.copy(alpha = 0.2f * intensity),
                                Color.Transparent
                            ),
                            center = androidx.compose.ui.geometry.Offset(0.3f, 0.3f),
                            radius = size.width * 0.8f
                        ),
                        radius = size.width * 0.7f,
                        center = androidx.compose.ui.geometry.Offset(size.width * 0.3f, size.height * 0.3f)
                    )
                }
                .border(BorderStroke(1.dp, borderColor), shape)

                .then(if (blurRadius > 0.dp) Modifier.blur(blurRadius) else Modifier)
        }


        @Composable
        fun LiquidProgressBar(progress: Float) {
            val animatedProgress by animateFloatAsState(
                targetValue = progress,
                animationSpec = tween(1000, easing = FastOutSlowInEasing)
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(12.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .background(Color.White.copy(alpha = 0.15f))
            ) {
                
                Box(
                    modifier = Modifier
                        .fillMaxWidth(animatedProgress)
                        .fillMaxHeight()
                        .clip(RoundedCornerShape(6.dp))
                        .drawBehind {
                            
                            drawRect(
                                brush = Brush.linearGradient(
                                    colors = listOf(
                                        Color(0xFF6DD5FA).copy(alpha = 0.9f),
                                        Color(0xFF2980B9).copy(alpha = 0.9f),
                                        Color(0xFF6DD5FA).copy(alpha = 0.9f)
                                    ),
                                    start = androidx.compose.ui.geometry.Offset(0f, 0f),
                                    end = androidx.compose.ui.geometry.Offset(size.width * 2, size.height * 2)
                                )
                            )

                            
                            drawCircle(
                                color = Color.White.copy(alpha = 0.3f),
                                radius = size.height * 0.4f,
                                center = androidx.compose.ui.geometry.Offset(size.width * 0.8f, size.height * 0.3f)
                            )
                        }
                )

                
                val infiniteTransition = rememberInfiniteTransition()
                val ripple by infiniteTransition.animateFloat(
                    initialValue = 0f,
                    targetValue = 1f,
                    animationSpec = infiniteRepeatable(
                        animation = tween(2000, easing = LinearEasing)
                    )
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth(animatedProgress)
                        .fillMaxHeight()
                        .drawBehind {
                            
                            drawRect(
                                color = Color.White.copy(alpha = 0.2f),
                                topLeft = androidx.compose.ui.geometry.Offset(size.width * ripple - 20f, 0f),
                                size = androidx.compose.ui.geometry.Size(40f, size.height)
                            )
                        }
                )
            }
        }

        @Composable
        fun QuickActionButton(icon: ImageVector, label: String) {
            var isHovered by remember { mutableStateOf(false) }
            val scale by animateFloatAsState(if (isHovered) 1.1f else 1f)

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .graphicsLayer {
                        scaleX = scale
                        scaleY = scale
                    }
                    .pointerInput(Unit) {
                        awaitPointerEventScope {
                            while (true) {
                                val event = awaitPointerEvent()
                                isHovered = event.changes.any { it.pressed }
                            }
                        }
                    }
            ) {
                Box(
                    modifier = Modifier
                        .size(60.dp)
                        .liquidGlassEffect(
                            shape = RoundedCornerShape(20.dp),
                            borderColor = Color.White.copy(alpha = 0.4f),
                            intensity = 0.3f,
                            blurRadius = 0.dp  
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(icon, contentDescription = label, tint = Color.White, modifier = Modifier.size(24.dp))
                }

                Spacer(Modifier.height(6.dp))
                Text(label, color = Color.White, fontSize = 12.sp)
            }
        }


        @Composable
        fun BottomLiquidGlassNavBar(
            items: List<Pair<ImageVector, String>>,
            selectedIndex: Int,
            onItemSelected: (Int) -> Unit
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(90.dp)
                    .padding(12.dp)
            ) {
           
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .liquidGlassEffect(
                            shape = RoundedCornerShape(30.dp),
                            borderColor = Color.White.copy(alpha = 0.5f),
                            intensity = 1f
                        )
                )

                Row(
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    items.forEachIndexed { index, item ->
                        
                        val isSelected = selectedIndex == index
                        val scale by animateFloatAsState(if (isSelected) 1.15f else 1f)

                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .weight(1f)
                                .clickable { onItemSelected(index) }
                                .graphicsLayer {
                                    scaleX = scale
                                    scaleY = scale
                                }
                        ) {
                            Icon(
                                item.first,
                                contentDescription = item.second,
                                tint = if (isSelected) Color.Cyan else Color.White.copy(alpha = 0.8f),
                                modifier = Modifier.size(28.dp)
                            )
                            Text(
                                item.second,
                                fontSize = 12.sp,
                                color = if (isSelected) Color.Cyan else Color.White.copy(alpha = 0.8f)
                            )
                        }
                    }
                }
            }
        }

        @Composable
        fun AnimatedParticles(particleCount: Int) {
            val particles = remember { List(particleCount) { Particle() } }

            Box(modifier = Modifier.fillMaxSize()) {
                particles.forEach { particle ->
                    var position by remember { mutableStateOf(particle.reset()) }

                    LaunchedEffect(particle) {
                        while (true) {
                            position = particle.update()
                            delay(16) 
                        }
                    }

                    Box(
                        modifier = Modifier
                            .offset(x = position.x.dp, y = position.y.dp)
                            .size(particle.size.dp)
                            .graphicsLayer {
                                alpha = particle.opacity
                                rotationZ = particle.rotation
                            }
                            .background(
                                color = Color.White.copy(alpha = particle.opacity * 0.3f),
                                shape = CircleShape
                            )
                    )
                }
            }
        }

        data class Particle(
            var x: Float = 0f,
            var y: Float = 0f,
            var size: Float = 0f,
            var speed: Float = 0f,
            var direction: Float = 0f,
            var opacity: Float = 0f,
            var rotation: Float = 0f,
            var rotationSpeed: Float = 0f
        ) {
            fun reset(): androidx.compose.ui.geometry.Offset {
                x = (0..1000).random().toFloat()
                y = (0..2000).random().toFloat()
                size = (2..8).random().toFloat()
                speed = Random.nextFloat() * (1.5f - 0.2f) + 0.2f
                direction = (0..360).random().toFloat()
                opacity = Random.nextFloat() * (0.4f - 0.1f) + 0.1f
                rotation = (0..360).random().toFloat()
                rotationSpeed = Random.nextFloat() * (2f - (-2f)) + (-2f)
                return androidx.compose.ui.geometry.Offset(x, y)
            }

            fun update(): androidx.compose.ui.geometry.Offset {
                x += cos(Math.toRadians(direction.toDouble())).toFloat() * speed
                y += sin(Math.toRadians(direction.toDouble())).toFloat() * speed
                rotation += rotationSpeed

                
                if (x < -100f || x > 1100f || y < -100f || y > 2100f) {
                    reset()
                }

                return androidx.compose.ui.geometry.Offset(x, y)
            }
        }

        @Preview
        @Composable
        private fun GlassDashboardScreenPreview() {
            ComposableTheme {
                GlassDashboardScreen()
            }
        }
        
    """.trimIndent()
}


@Composable
fun GlassDashboardCodeSection(code: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "Source Code",
            color = Color.White,
            fontSize = 18.sp
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Color.Black.copy(alpha = 0.8f))
                .border(1.dp, Color.White.copy(alpha = 0.3f), RoundedCornerShape(12.dp))
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            Text(
                text = code,
                color = Color.White,
                fontSize = 12.sp,
                lineHeight = 16.sp
            )
        }


        CodeBlock(
            source = code,
            language = "kotlin"
        )
    }
}