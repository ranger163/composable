package com.naulian.composable.core.component

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.naulian.modify.HugeIcons

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ComposableTopAppBar(
    title: String,
    modifier: Modifier = Modifier,
    onBack: () -> Unit = {},
    enableBack: Boolean = true
) {
    TopAppBar(
        modifier = modifier,
        navigationIcon = {
            if (enableBack) {
                IconButton(onClick = onBack) {
                    Icon(
                        painter = painterResource(HugeIcons.Back),
                        contentDescription = "Back Icon"
                    )
                }
            }
        },
        title = { Text(text = title) },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent
        )
    )
}