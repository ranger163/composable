package com.naulian.composable.screens.neumorphic.component

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.naulian.composable.theme.ComposeTheme
import com.naulian.modify.noRippleClick


@Composable
fun Button(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    contentPadding: PaddingValues = PaddingValues(horizontal = 24.dp, vertical = 8.dp),
    shape: Shape = ButtonDefaults.shape,
    content: @Composable RowScope.() -> Unit
) {

    var touch by remember { mutableStateOf(false) }
    val shadowPadding by animateDpAsState(
        targetValue = if (touch) 4.dp else contentPadding.calculateTopPadding(),
        animationSpec = tween(
            durationMillis = 500
        )
    )

    Row(
        modifier = modifier
            .neumorphicUp(
                shape = shape,
                shadowPadding = shadowPadding,
                light = Color.White,
                shadow = Color.LightGray
            )
            .defaultMinSize(
                minWidth = 58.dp,
                minHeight = 40.dp
            )
            .background(NeumorphicContainer, shape)
            .clip(shape)
            .pointerInput(Unit) {
                awaitPointerEventScope {
                    while (true) {
                        val event = awaitPointerEvent()
                        val change = event.changes.first()
                        touch = change.pressed
                    }
                }
            }
            .noRippleClick(enabled) { onClick() }
            .padding(contentPadding),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        content = content,
    )
}

@Preview
@Composable
private fun ButtonPreview() {
    ComposeTheme {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .background(NeumorphicBg),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
        ) {
            Button(onClick = {}) {
                Text(text = "Neumorphic Button")
            }

            Button(onClick = {}, shape = RoundedCornerShape(20)) {
                Text(text = "Neumorphic Button")
            }
        }
    }
}