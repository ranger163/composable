package com.naulian.composable.neumorphism

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
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.naulian.composable.theme.ComposableTheme

@Composable
fun Down(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(horizontal = 24.dp, vertical = 8.dp),
    shape: Shape = ButtonDefaults.shape,
    content: @Composable RowScope.() -> Unit
) {

    var touch by remember { mutableStateOf(false) }
    val shadowPadding by animateDpAsState(
        targetValue = if (touch) 4.dp else 6.dp,
        animationSpec = tween(
            durationMillis = 500
        )
    )

    Row(
        modifier = modifier
            .neumorphicDown(
                shape = shape,
                shadowPadding = shadowPadding,
            )
            .defaultMinSize(
                minWidth = 58.dp,
                minHeight = 40.dp
            )
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
            .padding(contentPadding),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        content = content,
    )
}

@Composable
fun Up(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(horizontal = 24.dp, vertical = 8.dp),
    shape: Shape = ButtonDefaults.shape,
    content: @Composable RowScope.() -> Unit
) {

    var touch by remember { mutableStateOf(false) }
    val shadowPadding by animateDpAsState(
        targetValue = if (touch) 2.dp else 6.dp,
        animationSpec = tween(
            durationMillis = 500
        )
    )

    Row(
        modifier = modifier
            .neumorphicUp(
                shape = shape,
                shadowPadding = shadowPadding,
            )
            .defaultMinSize(
                minWidth = 58.dp,
                minHeight = 40.dp
            )
            .background(
                color = MaterialTheme.colorScheme.surfaceContainer, shape
            )
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
            .padding(contentPadding),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        content = content,
    )
}

@Preview
@Composable
private fun ButtonPreview() {
    ComposableTheme {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .background(MaterialTheme.colorScheme.background),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
        ) {
            Up {
                Text(text = "Neumorphic Up")
            }

            Down {
                Text(text = "Neumorphic Down")
            }
        }
    }
}