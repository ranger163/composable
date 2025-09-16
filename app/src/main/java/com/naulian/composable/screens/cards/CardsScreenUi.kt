package com.naulian.composable.screens.cards

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.naulian.composable.component.CodeBlock
import com.naulian.composable.theme.ComposableTheme
import com.naulian.modify.ExperimentalModifyApi
import com.naulian.modify.HugeIcons

@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalFoundationApi::class,
    ExperimentalModifyApi::class
)
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
        // Removed the Box wrapper and global page indicators
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxSize()
                .background(theme.background)
                .padding(paddingValues)
        ) { page ->
            when (page) {
                0 -> SocialCardPage(theme = theme, currentPage = page)
                1 -> MusicCardPage(theme = theme, currentPage = page)
                2 -> SettingsCardPage(
                    theme = theme,
                    isDarkMode = isDarkMode,
                    onDarkModeToggle = { isDarkMode = it },
                    currentPage = page
                )
            }
        }
    }
}

@Composable
private fun SocialCardPage(theme: NeumorphicTheme, currentPage: Int) {
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

        // Page indicator between card and code
        item {
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
                                color = if (index == currentPage)
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
private fun MusicCardPage(theme: NeumorphicTheme, currentPage: Int) {
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

        // Page indicator between card and code
        item {
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
                                color = if (index == currentPage)
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
    isDarkMode: Boolean,
    onDarkModeToggle: (Boolean) -> Unit,
    currentPage: Int
) {
    val scrollState = rememberLazyListState()
    var settingsItems by remember {
        mutableStateOf(
            listOf(
                SettingItem("Dark Mode", HugeIcons.Settings, isDarkMode),
                SettingItem("Notifications", HugeIcons.Settings, true),
                SettingItem("WiFi", HugeIcons.Settings, false),
                SettingItem("Sound", HugeIcons.Settings, true),
                SettingItem("Profile", HugeIcons.Settings, false),
                SettingItem("Bluetooth", HugeIcons.Settings, true),
                SettingItem("Location", HugeIcons.Settings, false),
                SettingItem("Auto-rotate", HugeIcons.Settings, true)
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

        // Page indicator between cards and code
        item {
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
                                color = if (index == currentPage)
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