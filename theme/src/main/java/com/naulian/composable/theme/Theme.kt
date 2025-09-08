package com.naulian.composable.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import com.naulian.modify.Black
import com.naulian.modify.DarkGray
import com.naulian.modify.LightGray
import com.naulian.modify.White

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    onPrimaryContainer = White,

    background = Color(0xFF232323),
    onBackground = White,

    secondary = PurpleGrey80,
    tertiary = DarkGray,

    surface = DarkGray,
    surfaceBright = Color(0xFF4B4B4B),
    surfaceDim = Color(0xFF000000)
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    onPrimaryContainer = Black,

    background = Color(0xFFEEEEEE),
    onBackground = DarkGray,

    secondary = PurpleGrey40,
    tertiary = White,

    surface = White,
    surfaceBright = White,
    surfaceDim = LightGray
)

@Composable
fun ComposeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}