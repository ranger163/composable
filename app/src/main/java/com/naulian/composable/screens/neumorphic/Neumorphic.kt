package com.naulian.composable.screens.neumorphic

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.innerShadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.naulian.composable.neumorphism.component.neumorphicDown
import com.naulian.composable.neumorphism.component.neumorphicUp
import com.naulian.composable.neumorphism.component.neumorphicUp2
import com.naulian.composable.theme.ComposeTheme
import com.naulian.modify.Transparent


@Composable
fun NeuMorphicUP(
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(20),
    contentPadding: Dp = 6.dp,
    lightColor: Color = Color.White,
    shadowColor: Color = Color.LightGray,
    contentAlignment: Alignment = Alignment.TopStart,
    content: @Composable BoxScope.() -> Unit = {},
) {
    Box(
        modifier = modifier.neumorphicUp(
            shape = shape,
            shadowPadding = contentPadding,
            light = lightColor,
            shadow = shadowColor
        ),
        content = content,
        contentAlignment = contentAlignment
    )
}

@Composable
fun NeuMorphicUP2(
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(20),
    contentPadding: Dp = 12.dp,
    lightColor: Color = Color.White,
    shadowColor: Color = Color.LightGray,
    contentAlignment: Alignment = Alignment.TopStart,
    content: @Composable BoxScope.() -> Unit = {},
) {
    Box(
        modifier = modifier.neumorphicUp2(
            shape = shape,
            shadowPadding = contentPadding,
            light = lightColor,
            shadow = shadowColor
        ),
        content = content,
        contentAlignment = contentAlignment
    )
}

@Composable
fun NeuMorphicDown(
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(20),
    contentPadding: Dp = 20.dp,
    contentAlignment: Alignment = Alignment.TopStart,
    lightColor: Color = Color.White,
    shadowColor: Color = Color.LightGray,
    content: @Composable BoxScope.() -> Unit = {},
) {
    Box(
        modifier = modifier.neumorphicDown(
            shape = shape,
            shadowPadding = contentPadding,
            light = lightColor,
            shadow = shadowColor
        ),
        content = content,
        contentAlignment = contentAlignment
    )
}

@Preview
@Composable
private fun NeumorphicBoxPreview() {
    ComposeTheme {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .background(MaterialTheme.colorScheme.background)
                .padding(48.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp, Alignment.CenterVertically)
        ) {
            NeuMorphicUP(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = CircleShape,
                contentAlignment = Alignment.Center
            )

            NeuMorphicUP2(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = CircleShape,
                contentAlignment = Alignment.Center
            )
        }
    }
}

val neumorphicCode by lazy {
    """
        fun Modifier.neumorphicUp2(
            shape: Shape,
            shadowPadding: Dp,
            light: Color = NeumorphicLight,
            shadow: Color = NeumorphicDark
        ) = innerShadow(
            shape = shape,
            shadow = Shadow(
                radius = shadowPadding,
                color = light,
                offset = DpOffset(x = shadowPadding, y = shadowPadding)
            )
        ).innerShadow(
            shape = shape,
            shadow = Shadow(
                radius = shadowPadding,
                color = shadow,
                offset = DpOffset(x = -shadowPadding, y = -shadowPadding)
            )
        )
        
        fun Modifier.neumorphicDown(
            shape: Shape,
            shadowPadding: Dp,
            light: Color = NeumorphicLight,
            shadow: Color = NeumorphicDark
        ) = innerShadow(
            shape = shape,
            shadow = Shadow(
                radius = shadowPadding,
                color = light,
                offset = DpOffset(x = -shadowPadding, y = -shadowPadding)
            )
        ).innerShadow(
            shape = shape,
            shadow = Shadow(
                radius = shadowPadding,
                color = shadow,
                offset = DpOffset(x = shadowPadding, y = shadowPadding)
            )
        )
        
        fun Modifier.neumorphicUp(
            shape: Shape,
            shadowPadding: Dp,
            light: Color = NeumorphicLight,
            shadow: Color = NeumorphicDark,
            backgroundColor: Color = NeumorphicContainer,
        ) = dropShadow(
            shape = shape,
            shadow = Shadow(
                radius = shadowPadding,
                color = light,
                offset = DpOffset(x = -shadowPadding, y = -shadowPadding)
            )
        )
            .dropShadow(
                shape = shape,
                shadow = Shadow(
                    radius = shadowPadding,
                    color = shadow,
                    offset = DpOffset(x = shadowPadding, y = shadowPadding)
                )
            )
            .background(backgroundColor, shape)
    
        @Composable
        fun NeuMorphicUP(
            modifier: Modifier = Modifier,
            shape: Shape = RoundedCornerShape(20),
            contentPadding: Dp = 6.dp,
            lightColor: Color = Color.White,
            shadowColor: Color = Color.LightGray,
            contentAlignment: Alignment = Alignment.TopStart,
            content: @Composable BoxScope.() -> Unit = {},
        ) {
            Box(
                modifier = modifier.neumorphicUp(
                    shape = shape,
                    shadowPadding = contentPadding,
                    light = lightColor,
                    shadow = shadowColor
                ),
                content = content,
                contentAlignment = contentAlignment
            )
        }
        
        @Composable
        fun NeuMorphicUP2(
            modifier: Modifier = Modifier,
            shape: Shape = RoundedCornerShape(20),
            contentPadding: Dp = 12.dp,
            lightColor: Color = Color.White,
            shadowColor: Color = Color.LightGray,
            contentAlignment: Alignment = Alignment.TopStart,
            content: @Composable BoxScope.() -> Unit = {},
        ) {
            Box(
                modifier = modifier.neumorphicUp2(
                    shape = shape,
                    shadowPadding = contentPadding,
                    light = lightColor,
                    shadow = shadowColor
                ),
                content = content,
                contentAlignment = contentAlignment
            )
        }
        
        @Composable
        fun NeuMorphicDown(
            modifier: Modifier = Modifier,
            shape: Shape = RoundedCornerShape(20),
            contentPadding: Dp = 20.dp,
            contentAlignment: Alignment = Alignment.TopStart,
            lightColor: Color = Color.White,
            shadowColor: Color = Color.LightGray,
            content: @Composable BoxScope.() -> Unit = {},
        ) {
            Box(
                modifier = modifier.neumorphicDown(
                    shape = shape,
                    shadowPadding = contentPadding,
                    light = lightColor,
                    shadow = shadowColor
                ),
                content = content,
                contentAlignment = contentAlignment
            )
        }
    """.trimIndent()
}