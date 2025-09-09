package com.naulian.composable.neumorphism

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.naulian.modify.touchIndicator

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NeumorphicSlider(
    state: SliderState,
    modifier: Modifier = Modifier,
    enabled : Boolean = true,
    trackThickness: Dp = 10.dp,
    trackShape: Shape = CircleShape,
    thumbSize: Dp = 24.dp,
    thumbShape: Shape = CircleShape,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    Slider(
        state = state,
        modifier = modifier,
        enabled = enabled,
        interactionSource = interactionSource,
        thumb = {
            Box(
                modifier = Modifier
                    .size(thumbSize)
                    .touchIndicator{}
                    .background(MaterialTheme.colorScheme.surfaceContainer, thumbShape)
                    .neumorphicUp(
                        shape = thumbShape,
                        shadowPadding = 2.dp
                    )
                    .draggable(
                        orientation = Orientation.Horizontal,
                        state = state,
                        onDragStopped = { state.onValueChangeFinished?.invoke() }
                    ),
                contentAlignment = Alignment.Center,
                content = {}
            )
        },
        track = {
            Box(
                modifier = modifier
                    .height(trackThickness)
                    .neumorphicDown(
                        shape = trackShape,
                        shadowPadding = 2.dp
                    ),
                content = {}
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun NeumorphicSliderPreview() {
    NeumorphicPreviewSquare {

        val sliderState = remember {
            SliderState(
                valueRange = 0f..100f,
                onValueChangeFinished = {},
                steps = 20
            )
        }

        NeumorphicSlider(
            modifier = Modifier.fillMaxWidth(),
            state = sliderState
        )
    }
}