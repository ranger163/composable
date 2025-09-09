package com.naulian.composable.neumorphism

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.SwitchColors
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun NeumorphicSwitch(
    checked: Boolean,
    onCheckedChange: ((Boolean) -> Unit)?,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    colors: SwitchColors = SwitchDefaults.colors(),
    interactionSource: MutableInteractionSource? = null,
) {
    val interactionSource = interactionSource ?: remember { MutableInteractionSource() }

    val toggleableModifier =
        if (onCheckedChange != null) {
            Modifier
                .minimumInteractiveComponentSize()
                .toggleable(
                    value = checked,
                    onValueChange = onCheckedChange,
                    enabled = enabled,
                    role = Role.Switch,
                    interactionSource = interactionSource,
                    indication = null
                )
        } else {
            Modifier
        }

    val thumbOffset by animateDpAsState(
        targetValue = if (checked) 32.dp else 0.dp,
        label = "thumbOffset",
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioLowBouncy,
            stiffness = Spring.StiffnessLow
        )
    )

    Box(
        modifier = modifier
            .then(toggleableModifier)
            .width(64.dp)
            .height(32.dp)
            .neumorphicDown(
                shape = CircleShape,
                shadowPadding = 4.dp,
            )//.padding(4.dp)
    ) {
        Box(
            modifier = Modifier
                .size(32.dp)
                .offset {
                    IntOffset(x = thumbOffset.toPx().toInt(), y = 0)
                }
                .neumorphicUp(
                    shape = CircleShape,
                    shadowPadding = 4.dp
                ),
            contentAlignment = Alignment.Center
        ) {}
    }
}

@Preview
@Composable
private fun NeumorphicSwitchPreview() {
    NeumorphicPreview {

        var checked by remember { mutableStateOf(false) }

        LaunchedEffect(Unit) {
            while (true) {
                delay(1000)
                checked = !checked
            }
        }

        NeumorphicSwitch(
            checked = checked,
            onCheckedChange = { checked = it }
        )

        NeumorphicSwitch(
            checked = !checked,
            onCheckedChange = { checked = !it }
        )
    }
}