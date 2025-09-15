package com.naulian.composable.screens.cards

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.naulian.composable.component.CodeBlock
import com.naulian.composable.theme.ComposableTheme
import com.naulian.modify.ExperimentalModifyApi
import com.naulian.modify.HugeIcons
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class, ExperimentalModifyApi::class)
@Composable
fun SwipeableCardsScreenUI(onBack: () -> Unit = {}) {
    var isDarkMode by remember { mutableStateOf(false) }
    val pagerState = rememberPagerState(pageCount = { 3 })

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
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            painter = painterResource(HugeIcons.Back),
                            contentDescription = "Back icon"
                        )
                    }
                },
                title = { Text("Neumorphic Cards") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = theme.background,
                    titleContentColor = theme.textColor,
                    navigationIconContentColor = theme.textColor
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
                    0 -> SocialCardPage(theme = theme)
                    1 -> MusicCardPage(theme = theme)
                    2 -> SettingsCardPage(
                        theme = theme,
                        isDarkMode = isDarkMode,
                        onDarkModeToggle = { isDarkMode = it }
                    )
                }
            }

            // Page indicators
            Row(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                repeat(3) { index ->
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .background(
                                color = if (pagerState.currentPage == index)
                                    theme.textColor else theme.textColor.copy(alpha = 0.3f),
                                shape = CircleShape
                            )
                    )
                }
            }
        }
    }
}

@Composable
private fun SocialCardPage(theme: NeumorphicTheme) {
    val scrollState = rememberLazyListState()

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
                profileImageRes = com.naulian.composable.R.drawable.img,
                name = "Aryan Jaiswal",
                description = "Android Developer and DevOps Engineer passionate about creating beautiful user interfaces.",
                theme = theme
            )
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
private fun MusicCardPage(theme: NeumorphicTheme) {
    val scrollState = rememberLazyListState()

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
                albumArtRes = com.naulian.composable.R.drawable.img,
                songTitle = "Neumorphic Vibes",
                artist = "Compose Artist",
                theme = theme
            )
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
    isDarkMode: Boolean,
    onDarkModeToggle: (Boolean) -> Unit
) {
    val scrollState = rememberLazyListState()
    var settingsItems by remember {
        mutableStateOf(
            listOf(
                SettingItem("Dark Mode", Icons.Default.DarkMode, isDarkMode),
                SettingItem("Notifications", Icons.Default.Notifications, true),
                SettingItem("WiFi", Icons.Default.Wifi, false),
                SettingItem("Sound", Icons.Default.VolumeUp, true),
                SettingItem("Profile", Icons.Default.AccountCircle, false),
                SettingItem("Bluetooth", Icons.Default.Bluetooth, true),
                SettingItem("Location", Icons.Default.LocationOn, false),
                SettingItem("Auto-rotate", Icons.Default.ScreenRotation, true)
            )
        )
    }

    LazyColumn(
        state = scrollState,
        modifier = Modifier
            .fillMaxSize()
            .background(theme.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(settingsItems.size) { index ->
            val item = settingsItems[index]
            NeumorphicSettingsCard(
                title = item.title,
                icon = item.icon,
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

        item {
            Spacer(modifier = Modifier.height(16.dp))
            CodeBlock(
                source = settingsCardGenericCode,
                language = "kotlin"
            )
        }
    }
}


@Preview
@Composable
private fun SwipeableCardsScreenUIPreview() {
    ComposableTheme {
        SwipeableCardsScreenUI()
    }
}