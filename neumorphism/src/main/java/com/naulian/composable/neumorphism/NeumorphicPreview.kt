package com.naulian.composable.neumorphism

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.naulian.composable.theme.ComposableTheme

@Composable
internal fun NeumorphicPreview(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    ComposableTheme {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .background(color = MaterialTheme.colorScheme.background)
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.CenterVertically),
            content = content
        )
    }
}