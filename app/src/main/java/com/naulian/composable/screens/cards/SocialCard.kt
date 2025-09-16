package com.naulian.composable.screens.cards

import android.graphics.Paint
import androidx.annotation.DrawableRes
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.asAndroidPath
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.naulian.composable.R
import com.naulian.composable.component.CodeBlock
import com.naulian.composable.neumorphism.NeumorphicButton
import com.naulian.composable.neumorphism.NeumorphicIconButton
import com.naulian.composable.neumorphism.NeumorphicSwitch
import com.naulian.modify.HugeIcons

// Theme Colors
val LightNeumorphicBackground = Color(0xFFE0E0E0)
val LightShadowColor = Color(0xFFFFFFFF)
val LightDarkShadowColor = Color(0xFFAFAFAF)
val LightTextColor = Color(0xFF333333)

val DarkNeumorphicBackground = Color(0xFF2C2C2C)
val DarkLightShadowColor = Color(0xFF3A3A3A)
val DarkDarkShadowColor = Color(0xFF1E1E1E)
val DarkTextColor = Color(0xFFE0E0E0)

data class NeumorphicTheme(
    val background: Color,
    val lightShadow: Color,
    val darkShadow: Color,
    val textColor: Color
)

data class SettingItem(
    val title: String,
    @param:DrawableRes val icon: Int,
    var isToggled: Boolean
)

// Neumorphic Shadow Modifier
fun Modifier.neumorphicShadow(
    lightShadowColor: Color,
    darkShadowColor: Color,
    lightOffset: Offset,
    darkOffset: Offset,
    elevation: Dp,
    cornerRadius: Dp = 20.dp
): Modifier = this.composed {
    val density = LocalDensity.current.density
    val elevationPx = elevation.value * density
    Modifier.drawBehind {
        val cornerRadiusPx = cornerRadius.toPx()
        val path = Path().apply {
            addRoundRect(
                RoundRect(
                    left = 0f,
                    top = 0f,
                    right = size.width,
                    bottom = size.height,
                    cornerRadius = CornerRadius(cornerRadiusPx, cornerRadiusPx)
                )
            )
        }
        drawIntoCanvas { canvas ->
            val lightPaint = Paint().apply {
                color = lightShadowColor.toArgb()
                setShadowLayer(
                    elevationPx,
                    lightOffset.x,
                    lightOffset.y,
                    lightShadowColor.copy(alpha = 0.5f).toArgb()
                )
            }
            canvas.nativeCanvas.drawPath(path.asAndroidPath(), lightPaint)

            val darkPaint = Paint().apply {
                color = darkShadowColor.toArgb()
                setShadowLayer(
                    elevationPx,
                    darkOffset.x,
                    darkOffset.y,
                    darkShadowColor.copy(alpha = 0.5f).toArgb()
                )
            }
            canvas.nativeCanvas.drawPath(path.asAndroidPath(), darkPaint)
        }
    }
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun SwipeableNeumorphicCardsScreen() {
    var isDarkMode by remember { mutableStateOf(false) }
    val pagerState = rememberPagerState(pageCount = { 3 })
    val scrollState = rememberLazyListState()

    val theme = if (isDarkMode) {
        NeumorphicTheme(
            background = DarkNeumorphicBackground,
            lightShadow = DarkLightShadowColor,
            darkShadow = DarkDarkShadowColor,
            textColor = DarkTextColor
        )
    } else {
        NeumorphicTheme(
            background = LightNeumorphicBackground,
            lightShadow = LightShadowColor,
            darkShadow = LightDarkShadowColor,
            textColor = LightTextColor
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Neumorphic Cards") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = theme.background,
                    titleContentColor = theme.textColor
                )
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(theme.background)
                .padding(paddingValues)
        ) {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxSize()
            ) { page ->
                when (page) {
                    0 -> SocialCardPage(theme = theme, scrollState = scrollState)
                    1 -> MusicCardPage(theme = theme, scrollState = scrollState)
                    2 -> SettingsCardPage(
                        theme = theme,
                        scrollState = scrollState,
                        isDarkMode = isDarkMode,
                        onDarkModeToggle = { isDarkMode = it }
                    )
                }
            }
        }
    }
}

@Composable
private fun SocialCardPage(
    theme: NeumorphicTheme,
    scrollState: androidx.compose.foundation.lazy.LazyListState
) {
    LazyColumn(
        state = scrollState,
        modifier = Modifier
            .fillMaxSize()
            .background(theme.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp),
        contentPadding = PaddingValues(16.dp)
    ) {
        item {
            NeumorphicSocialProfileCard(
                profileImageRes = R.drawable.img,
                name = "Aryan Jaiswal",
                description = "Android Developer and DevOps Engineer passionate about creating beautiful user interfaces.",
                theme = theme
            )
        }
        item {
            // Page Indicator
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                repeat(3) { index ->
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .background(
                                color = if (index == 0)
                                    theme.textColor else theme.textColor.copy(alpha = 0.3f),
                                shape = CircleShape
                            )
                    )
                    if (index < 2) {
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                }
            }
        }
        item {
            CodeBlock(
                source = socialCardGenericCode,
                language = "kotlin"
            )
        }
    }
}

@Composable
private fun MusicCardPage(
    theme: NeumorphicTheme,
    scrollState: androidx.compose.foundation.lazy.LazyListState
) {
    LazyColumn(
        state = scrollState,
        modifier = Modifier
            .fillMaxSize()
            .background(theme.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp),
        contentPadding = PaddingValues(16.dp)
    ) {
        item {
            NeumorphicMusicCard(
                albumArtRes = R.drawable.img,
                songTitle = "Neumorphic Vibes",
                artist = "Compose Artist",
                theme = theme
            )
        }
        item {
            // Page Indicator
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                repeat(3) { index ->
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .background(
                                color = if (index == 1)
                                    theme.textColor else theme.textColor.copy(alpha = 0.3f),
                                shape = CircleShape
                            )
                    )
                    if (index < 2) {
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                }
            }
        }
        item {
            CodeBlock(
                source = musicCardGenericCode,
                language = "kotlin"
            )
        }
    }
}

@Composable
private fun SettingsCardPage(
    theme: NeumorphicTheme,
    scrollState: androidx.compose.foundation.lazy.LazyListState,
    isDarkMode: Boolean,
    onDarkModeToggle: (Boolean) -> Unit
) {
    var settingsItems by remember {
        mutableStateOf(
            listOf(
                SettingItem("Dark Mode", HugeIcons.Settings, isDarkMode),
                SettingItem("Notifications", HugeIcons.Notification, true),
                SettingItem("WiFi", HugeIcons.Favourite, false),
                SettingItem("Sound", HugeIcons.Share, true),
                SettingItem("Profile", HugeIcons.Account, false)
            )
        )
    }

    LazyColumn(
        state = scrollState,
        modifier = Modifier
            .fillMaxSize()
            .background(theme.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(16.dp)
    ) {
        // Settings Cards
        items(settingsItems.size) { index ->
            val item = settingsItems[index]
            NeumorphicSettingsCard(
                title = item.title,
                painter = painterResource(item.icon),
                theme = theme,
                isToggled = if (item.title == "Dark Mode") isDarkMode else item.isToggled,
                onToggle = { newValue ->
                    if (item.title == "Dark Mode") {
                        onDarkModeToggle(newValue)
                    }
                    settingsItems = settingsItems.mapIndexed { i, settingItem ->
                        if (i == index) settingItem.copy(isToggled = newValue)
                        else settingItem
                    }
                }
            )
        }

        // Page Indicator - positioned after settings cards but before code
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 24.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                repeat(3) { index ->
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .background(
                                color = if (index == 2)
                                    theme.textColor else theme.textColor.copy(alpha = 0.3f),
                                shape = CircleShape
                            )
                    )
                    if (index < 2) {
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                }
            }
        }

        // Code Block
        item {
            CodeBlock(
                source = settingsCardGenericCode,
                language = "kotlin"
            )
        }
    }
}

// Card Components
@Composable
fun NeumorphicSocialProfileCard(
    modifier: Modifier = Modifier,
    profileImageRes: Int,
    name: String,
    description: String,
    theme: NeumorphicTheme
) {
    var isFollowing by remember { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val elevation by animateDpAsState(
        targetValue = if (isPressed) 2.dp else 6.dp,
        label = "card_elevation"
    )

    Box(
        modifier = modifier
            .width(320.dp)
            .neumorphicShadow(
                lightShadowColor = if (isPressed) theme.darkShadow else theme.lightShadow,
                darkShadowColor = if (isPressed) theme.lightShadow else theme.darkShadow,
                lightOffset = Offset(-6f, -6f),
                darkOffset = Offset(6f, 6f),
                elevation = elevation
            )
            .background(theme.background, RoundedCornerShape(20.dp))
            .clip(RoundedCornerShape(20.dp))
            .clickable(interactionSource = interactionSource, indication = null) { }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .neumorphicShadow(
                        lightShadowColor = theme.lightShadow,
                        darkShadowColor = theme.darkShadow,
                        lightOffset = Offset(-6f, -6f),
                        darkOffset = Offset(6f, 6f),
                        elevation = 6.dp,
                        cornerRadius = 50.dp
                    )
                    .background(theme.background, CircleShape)
                    .clip(CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(profileImageRes),
                    contentDescription = "Profile",
                    modifier = Modifier
                        .size(90.dp)
                        .clip(CircleShape)
                )
            }

            Text(
                text = name,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = theme.textColor
            )

            Text(
                text = description,
                fontSize = 14.sp,
                color = theme.textColor.copy(alpha = 0.7f),
                textAlign = TextAlign.Center
            )

            NeumorphicButton(
                onClick = {
                    { isFollowing = !isFollowing }
                }
            ) {
                Text(if (isFollowing) "Unfollow" else "Follow")
            }
        }
    }
}

@Composable
fun NeumorphicMusicCard(
    modifier: Modifier = Modifier,
    albumArtRes: Int,
    songTitle: String,
    artist: String,
    theme: NeumorphicTheme
) {
    var isPlaying by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .width(320.dp)
            .neumorphicShadow(
                lightShadowColor = theme.lightShadow,
                darkShadowColor = theme.darkShadow,
                lightOffset = Offset(-6f, -6f),
                darkOffset = Offset(6f, 6f),
                elevation = 6.dp
            )
            .background(theme.background, RoundedCornerShape(20.dp))
            .clip(RoundedCornerShape(20.dp))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(150.dp)
                    .neumorphicShadow(
                        lightShadowColor = theme.lightShadow,
                        darkShadowColor = theme.darkShadow,
                        lightOffset = Offset(-4f, -4f),
                        darkOffset = Offset(4f, 4f),
                        elevation = 4.dp,
                        cornerRadius = 12.dp
                    )
                    .background(theme.background, RoundedCornerShape(12.dp))
                    .clip(RoundedCornerShape(12.dp))
            ) {
                Image(
                    painter = painterResource(albumArtRes),
                    contentDescription = "Album Art",
                    modifier = Modifier.fillMaxSize()
                )
            }

            Text(
                text = songTitle,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = theme.textColor
            )

            Text(
                text = artist,
                fontSize = 14.sp,
                color = theme.textColor.copy(alpha = 0.7f)
            )

            NeumorphicIconButton(
                onClick = { isPlaying = !isPlaying }
            ) {
                Icon(
                    painter = painterResource(id = HugeIcons.Share),
                    contentDescription = null
                )
            }
        }
    }
}

@Composable
fun NeumorphicSettingsCard(
    modifier: Modifier = Modifier,
    title: String,
    painter: Painter,
    theme: NeumorphicTheme,
    isToggled: Boolean,
    onToggle: (Boolean) -> Unit
) {
    Box(
        modifier = modifier
            .width(320.dp)
            .height(80.dp)
            .neumorphicShadow(
                lightShadowColor = theme.lightShadow,
                darkShadowColor = theme.darkShadow,
                lightOffset = Offset(-6f, -6f),
                darkOffset = Offset(6f, 6f),
                elevation = 6.dp,
                cornerRadius = 16.dp
            )
            .background(theme.background, RoundedCornerShape(16.dp))
            .clip(RoundedCornerShape(16.dp))
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Icon(
                    painter = painter,
                    contentDescription = null,
                    tint = theme.textColor
                )
                Text(
                    text = title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = theme.textColor
                )
            }

            NeumorphicSwitch(
                checked = isToggled,
                onCheckedChange = { onToggle(it) }
            )
        }
    }
}


val socialCardGenericCode = """
package com.yourpackage.neumorphic

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.asAndroidPath
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import android.graphics.Paint

// Theme Colors
val LightNeumorphicBackground = Color(0xFFE0E0E0)
val LightShadowColor = Color(0xFFFFFFFF)
val LightDarkShadowColor = Color(0xFFAFAFAF)
val LightTextColor = Color(0xFF333333)

data class NeumorphicTheme(
    val background: Color,
    val lightShadow: Color,
    val darkShadow: Color,
    val textColor: Color
)

fun Modifier.neumorphicShadow(
    lightShadowColor: Color,
    darkShadowColor: Color,
    lightOffset: Offset = Offset(-6f, -6f),
    darkOffset: Offset = Offset(6f, 6f),
    elevation: Dp,
    cornerRadius: Dp = 20.dp
): Modifier = this.composed {
    val density = LocalDensity.current.density
    val elevationPx = elevation.value * density
    Modifier.drawBehind {
        val cornerRadiusPx = cornerRadius.toPx()
        val path = Path().apply {
            addRoundRect(
                RoundRect(
                    left = 0f, top = 0f, right = size.width, bottom = size.height,
                    cornerRadius = CornerRadius(cornerRadiusPx, cornerRadiusPx)
                )
            )
        }
        drawIntoCanvas { canvas ->
            val lightPaint = Paint().apply {
                color = lightShadowColor.toArgb()
                setShadowLayer(elevationPx, lightOffset.x, lightOffset.y, 
                    lightShadowColor.copy(alpha = 0.5f).toArgb())
            }
            canvas.nativeCanvas.drawPath(path.asAndroidPath(), lightPaint)
            
            val darkPaint = Paint().apply {
                color = darkShadowColor.toArgb()
                setShadowLayer(elevationPx, darkOffset.x, darkOffset.y, 
                    darkShadowColor.copy(alpha = 0.5f).toArgb())
            }
            canvas.nativeCanvas.drawPath(path.asAndroidPath(), darkPaint)
        }
    }
}

// Neumorphic Button Component
@Composable
fun NeumorphicButton(
    text: String,
    theme: NeumorphicTheme,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val elevation by animateDpAsState(
        targetValue = if (isPressed) 2.dp else 4.dp,
        label = "button_elevation"
    )

    Box(
        modifier = modifier
            .width(120.dp)
            .neumorphicShadow(
                lightShadowColor = if (isPressed) theme.darkShadow else theme.lightShadow,
                darkShadowColor = if (isPressed) theme.lightShadow else theme.darkShadow,
                lightOffset = Offset(-4f, -4f),
                darkOffset = Offset(4f, 4f),
                elevation = elevation,
                cornerRadius = 12.dp
            )
            .background(theme.background, RoundedCornerShape(12.dp))
            .clip(RoundedCornerShape(12.dp))
            .clickable(interactionSource = interactionSource, indication = null, onClick = onClick)
            .padding(vertical = 12.dp, horizontal = 24.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(text = text, color = theme.textColor, fontWeight = FontWeight.Bold, fontSize = 16.sp)
    }
}

// Social Profile Card
@Composable
fun NeumorphicSocialProfileCard(
    modifier: Modifier = Modifier,
    profileImageRes: Int, // Replace with your drawable
    name: String,
    description: String,
    theme: NeumorphicTheme
) {
    var isFollowing by remember { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val elevation by animateDpAsState(
        targetValue = if (isPressed) 2.dp else 6.dp,
        label = "card_elevation"
    )

    Box(
        modifier = modifier
            .width(320.dp)
            .neumorphicShadow(
                lightShadowColor = if (isPressed) theme.darkShadow else theme.lightShadow,
                darkShadowColor = if (isPressed) theme.lightShadow else theme.darkShadow,
                lightOffset = Offset(-6f, -6f),
                darkOffset = Offset(6f, 6f),
                elevation = elevation
            )
            .background(theme.background, RoundedCornerShape(20.dp))
            .clip(RoundedCornerShape(20.dp))
            .clickable(interactionSource = interactionSource, indication = null) { }
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Profile Image with neumorphic shadow
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .neumorphicShadow(
                        lightShadowColor = theme.lightShadow,
                        darkShadowColor = theme.darkShadow,
                        elevation = 6.dp, cornerRadius = 50.dp
                    )
                    .background(theme.background, CircleShape)
                    .clip(CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(profileImageRes),
                    contentDescription = "Profile",
                    modifier = Modifier.size(90.dp).clip(CircleShape)
                )
            }
            
            Text(text = name, fontSize = 24.sp, fontWeight = FontWeight.Bold, color = theme.textColor)
            Text(text = description, fontSize = 14.sp, color = theme.textColor.copy(alpha = 0.7f), textAlign = TextAlign.Center)
            NeumorphicButton(
                text = if (isFollowing) "Unfollow" else "Follow",
                theme = theme,
                onClick = { isFollowing = !isFollowing }
            )
        }
    }
}

// Usage Example
@Composable
fun SocialCardExample() {
    val lightTheme = NeumorphicTheme(
        background = LightNeumorphicBackground,
        lightShadow = LightShadowColor,
        darkShadow = LightDarkShadowColor,
        textColor = LightTextColor
    )
    
    NeumorphicSocialProfileCard(
        profileImageRes = R.drawable.your_image, // Replace with your image
        name = "John Doe",
        description = "Android Developer passionate about creating beautiful UI",
        theme = lightTheme
    )
}
""".trimIndent()

val musicCardGenericCode = """
package com.yourpackage.neumorphic

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.asAndroidPath
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import android.graphics.Paint

// Theme Colors
val LightNeumorphicBackground = Color(0xFFE0E0E0)
val LightShadowColor = Color(0xFFFFFFFF)
val LightDarkShadowColor = Color(0xFFAFAFAF)
val LightTextColor = Color(0xFF333333)

data class NeumorphicTheme(
    val background: Color,
    val lightShadow: Color,
    val darkShadow: Color,
    val textColor: Color
)

// Neumorphic Shadow Modifier
fun Modifier.neumorphicShadow(
    lightShadowColor: Color,
    darkShadowColor: Color,
    lightOffset: Offset = Offset(-6f, -6f),
    darkOffset: Offset = Offset(6f, 6f),
    elevation: Dp,
    cornerRadius: Dp = 20.dp
): Modifier = this.composed {
    val density = LocalDensity.current.density
    val elevationPx = elevation.value * density
    Modifier.drawBehind {
        val cornerRadiusPx = cornerRadius.toPx()
        val path = Path().apply {
            addRoundRect(
                RoundRect(
                    left = 0f, top = 0f, right = size.width, bottom = size.height,
                    cornerRadius = CornerRadius(cornerRadiusPx, cornerRadiusPx)
                )
            )
        }
        drawIntoCanvas { canvas ->
            val lightPaint = Paint().apply {
                color = lightShadowColor.toArgb()
                setShadowLayer(elevationPx, lightOffset.x, lightOffset.y, 
                    lightShadowColor.copy(alpha = 0.5f).toArgb())
            }
            canvas.nativeCanvas.drawPath(path.asAndroidPath(), lightPaint)
            
            val darkPaint = Paint().apply {
                color = darkShadowColor.toArgb()
                setShadowLayer(elevationPx, darkOffset.x, darkOffset.y, 
                    darkShadowColor.copy(alpha = 0.5f).toArgb())
            }
            canvas.nativeCanvas.drawPath(path.asAndroidPath(), darkPaint)
        }
    }
}

// Neumorphic Icon Button Component
@Composable
fun NeumorphicIconButton(
    icon: ImageVector,
    theme: NeumorphicTheme,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val elevation by animateDpAsState(
        targetValue = if (isPressed) 2.dp else 4.dp,
        label = "icon_button_elevation"
    )

    Box(
        modifier = modifier
            .size(60.dp)
            .neumorphicShadow(
                lightShadowColor = if (isPressed) theme.darkShadow else theme.lightShadow,
                darkShadowColor = if (isPressed) theme.lightShadow else theme.darkShadow,
                lightOffset = Offset(-4f, -4f),
                darkOffset = Offset(4f, 4f),
                elevation = elevation,
                cornerRadius = 30.dp
            )
            .background(theme.background, CircleShape)
            .clip(CircleShape)
            .clickable(interactionSource = interactionSource, indication = null, onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Icon(imageVector = icon, contentDescription = null, tint = theme.textColor, modifier = Modifier.size(24.dp))
    }
}

// Music Card
@Composable
fun NeumorphicMusicCard(
    modifier: Modifier = Modifier,
    albumArtRes: Int, // Replace with your drawable
    songTitle: String,
    artist: String,
    theme: NeumorphicTheme
) {
    var isPlaying by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .width(320.dp)
            .neumorphicShadow(
                lightShadowColor = theme.lightShadow,
                darkShadowColor = theme.darkShadow,
                lightOffset = Offset(-6f, -6f),
                darkOffset = Offset(6f, 6f),
                elevation = 6.dp
            )
            .background(theme.background, RoundedCornerShape(20.dp))
            .clip(RoundedCornerShape(20.dp))
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Album Art with neumorphic shadow
            Box(
                modifier = Modifier
                    .size(150.dp)
                    .neumorphicShadow(
                        lightShadowColor = theme.lightShadow,
                        darkShadowColor = theme.darkShadow,
                        lightOffset = Offset(-4f, -4f),
                        darkOffset = Offset(4f, 4f),
                        elevation = 4.dp, cornerRadius = 12.dp
                    )
                    .background(theme.background, RoundedCornerShape(12.dp))
                    .clip(RoundedCornerShape(12.dp))
            ) {
                Image(
                    painter = painterResource(albumArtRes),
                    contentDescription = "Album Art",
                    modifier = Modifier.fillMaxSize()
                )
            }

            Text(text = songTitle, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = theme.textColor)
            Text(text = artist, fontSize = 14.sp, color = theme.textColor.copy(alpha = 0.7f))
            
            NeumorphicIconButton(
                icon = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                theme = theme,
                onClick = { isPlaying = !isPlaying }
            )
        }
    }
}

// Usage Example
@Composable
fun MusicCardExample() {
    val lightTheme = NeumorphicTheme(
        background = LightNeumorphicBackground,
        lightShadow = LightShadowColor,
        darkShadow = LightDarkShadowColor,
        textColor = LightTextColor
    )
    
    NeumorphicMusicCard(
        albumArtRes = R.drawable.your_album_art, // Replace with your image
        songTitle = "Beautiful Song",
        artist = "Amazing Artist",
        theme = lightTheme
    )
}
""".trimIndent()
val settingsCardGenericCode = """
package com.yourpackage.neumorphic

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.asAndroidPath
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import android.graphics.Paint

// Theme Colors
val LightNeumorphicBackground = Color(0xFFE0E0E0)
val LightShadowColor = Color(0xFFFFFFFF)
val LightDarkShadowColor = Color(0xFFAFAFAF)
val LightTextColor = Color(0xFF333333)

data class NeumorphicTheme(
    val background: Color,
    val lightShadow: Color,
    val darkShadow: Color,
    val textColor: Color
)

// Neumorphic Shadow Modifier
fun Modifier.neumorphicShadow(
    lightShadowColor: Color,
    darkShadowColor: Color,
    lightOffset: Offset = Offset(-6f, -6f),
    darkOffset: Offset = Offset(6f, 6f),
    elevation: Dp,
    cornerRadius: Dp = 20.dp
): Modifier = this.composed {
    val density = LocalDensity.current.density
    val elevationPx = elevation.value * density
    Modifier.drawBehind {
        val cornerRadiusPx = cornerRadius.toPx()
        val path = Path().apply {
            addRoundRect(
                RoundRect(
                    left = 0f, top = 0f, right = size.width, bottom = size.height,
                    cornerRadius = CornerRadius(cornerRadiusPx, cornerRadiusPx)
                )
            )
        }
        drawIntoCanvas { canvas ->
            val lightPaint = Paint().apply {
                color = lightShadowColor.toArgb()
                setShadowLayer(elevationPx, lightOffset.x, lightOffset.y, 
                    lightShadowColor.copy(alpha = 0.5f).toArgb())
            }
            canvas.nativeCanvas.drawPath(path.asAndroidPath(), lightPaint)
            
            val darkPaint = Paint().apply {
                color = darkShadowColor.toArgb()
                setShadowLayer(elevationPx, darkOffset.x, darkOffset.y, 
                    darkShadowColor.copy(alpha = 0.5f).toArgb())
            }
            canvas.nativeCanvas.drawPath(path.asAndroidPath(), darkPaint)
        }
    }
}

// Neumorphic Toggle Switch Component
@Composable
fun NeumorphicToggleSwitch(
    isToggled: Boolean,
    onToggle: (Boolean) -> Unit,
    theme: NeumorphicTheme,
    modifier: Modifier = Modifier
) {
    val toggleOffset by animateFloatAsState(
        targetValue = if (isToggled) 1f else 0f,
        animationSpec = tween(300),
        label = "toggle_offset"
    )

    Box(
        modifier = modifier
            .width(50.dp)
            .height(30.dp)
            .neumorphicShadow(
                lightShadowColor = theme.lightShadow,
                darkShadowColor = theme.darkShadow,
                lightOffset = Offset(-2f, -2f),
                darkOffset = Offset(2f, 2f),
                elevation = 2.dp, cornerRadius = 15.dp
            )
            .background(theme.background, RoundedCornerShape(15.dp))
            .clip(RoundedCornerShape(15.dp))
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = { onToggle(!isToggled) }
            )
    ) {
        Box(
            modifier = Modifier
                .size(22.dp)
                .offset(x = 4.dp + (18.dp * toggleOffset), y = 4.dp)
                .neumorphicShadow(
                    lightShadowColor = if (isToggled) theme.darkShadow else theme.lightShadow,
                    darkShadowColor = if (isToggled) theme.lightShadow else theme.darkShadow,
                    lightOffset = Offset(-2f, -2f),
                    darkOffset = Offset(2f, 2f),
                    elevation = if (isToggled) 2.dp else 4.dp,
                    cornerRadius = 11.dp
                )
                .background(
                    color = if (isToggled) Color(0xFF4CAF50) else theme.background,
                    shape = CircleShape
                )
        )
    }
}

// Settings Card
@Composable
fun NeumorphicSettingsCard(
    modifier: Modifier = Modifier,
    title: String,
    icon: ImageVector,
    theme: NeumorphicTheme,
    isToggled: Boolean,
    onToggle: (Boolean) -> Unit
) {
    Box(
        modifier = modifier
            .width(320.dp)
            .height(80.dp)
            .neumorphicShadow(
                lightShadowColor = theme.lightShadow,
                darkShadowColor = theme.darkShadow,
                lightOffset = Offset(-6f, -6f),
                darkOffset = Offset(6f, 6f),
                elevation = 6.dp, cornerRadius = 16.dp
            )
            .background(theme.background, RoundedCornerShape(16.dp))
            .clip(RoundedCornerShape(16.dp))
    ) {
        Row(
            modifier = Modifier.fillMaxSize().padding(horizontal = 24.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Icon(imageVector = icon, contentDescription = null, tint = theme.textColor)
                Text(text = title, fontSize = 16.sp, fontWeight = FontWeight.Medium, color = theme.textColor)
            }
            NeumorphicToggleSwitch(isToggled = isToggled, onToggle = onToggle, theme = theme)
        }
    }
}

// Usage Example
@Composable
fun SettingsCardExample() {
    val lightTheme = NeumorphicTheme(
        background = LightNeumorphicBackground,
        lightShadow = LightShadowColor,
        darkShadow = LightDarkShadowColor,
        textColor = LightTextColor
    )
    
    var isDarkMode by remember { mutableStateOf(false) }
    
    NeumorphicSettingsCard(
        title = "Dark Mode",
        icon = Icons.Default.DarkMode,
        theme = lightTheme,
        isToggled = isDarkMode,
        onToggle = { isDarkMode = it }
    )
}
""".trimIndent()

@Preview(showBackground = true)
@Composable
fun PreviewSwipeableNeumorphicCards() {
    SwipeableNeumorphicCardsScreen()
}