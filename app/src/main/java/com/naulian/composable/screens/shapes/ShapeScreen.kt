package com.naulian.composable.screens.shapes

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.naulian.composable.LocalNavController
import com.naulian.composable.screens.cards.DarkDarkShadowColor
import com.naulian.composable.screens.cards.DarkLightShadowColor
import com.naulian.composable.screens.cards.DarkNeumorphicBackground
import com.naulian.composable.screens.cards.DarkTextColor
import com.naulian.composable.screens.cards.LightDarkShadowColor
import com.naulian.composable.screens.cards.LightNeumorphicBackground
import com.naulian.composable.screens.cards.LightShadowColor
import com.naulian.composable.screens.cards.LightTextColor
import com.naulian.composable.screens.cards.NeumorphicTheme
import com.naulian.modify.ExperimentalModifyApi
import com.naulian.modify.HugeIcons

@Composable
fun ShapesScreen() {
    val navController = LocalNavController.current

    ShapesScreenUI(
        onBack = { navController.navigateUp() }
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class, ExperimentalModifyApi::class)
@Composable
fun ShapesScreenUI(onBack: () -> Unit = {}) {
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
                title = { Text("Neumorphic Shapes") },
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
                0 -> NeomorphicTicket()
                // more to come
            }
        }
    }
}