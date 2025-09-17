package com.naulian.composable.screens.shapes

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.naulian.composable.R
import com.naulian.composable.component.CodeBlock
import com.naulian.composable.screens.cards.LightDarkShadowColor
import com.naulian.composable.screens.cards.LightNeumorphicBackground
import com.naulian.composable.screens.cards.LightShadowColor
import com.naulian.composable.theme.ComposableTheme
import com.naulian.modify.White

@Composable
fun NeomorphicTicket(
    modifier: Modifier = Modifier
) {
    val verticalShapeTicketSource = remember { verticalTicketShapeCode }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp),
    ) {
        NeomorphicShapeTicket(modifier)

        CodeBlock(
            source = verticalShapeTicketSource,
            language = "kotlin"
        )
    }
}

@Composable
fun NeomorphicShapeTicket(
    modifier: Modifier = Modifier,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val elevation by animateDpAsState(
        targetValue = if (isPressed) 2.dp else 6.dp,
        label = "card_elevation"
    )

    val ticketHeight = 400.dp
    val ticketWith= 320.dp
    val cutoutFraction = 0.7f
    val cutoutCenterHeight = ticketHeight * cutoutFraction
    val ticketShape = VerticalTicketShape(
        cornerRadius = 12.dp,
        cutoutRadius = 10.dp,
        cutoutHeightFraction = cutoutFraction
    )

    Box(
        modifier = modifier
            .size(width = ticketWith, height = ticketHeight)
            .neumorphicShapeShadow(
                shape = ticketShape,
                lightShadowColor = if (isPressed) LightDarkShadowColor else LightShadowColor,
                darkShadowColor = if (isPressed) LightShadowColor else LightDarkShadowColor,
                lightOffset = Offset(-6f, -6f),
                darkOffset = Offset(6f, 6f),
                elevation = elevation
            )
            .background(LightNeumorphicBackground, ticketShape)
            .clip(shape = ticketShape)
            .clickable(interactionSource = interactionSource, indication = null) { }
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                painter = painterResource(R.drawable.poster),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(cutoutCenterHeight)
                    .padding(4.dp)
                    .padding(bottom = 12.dp)
                    .clip(RoundedCornerShape(12.dp))
            )

            DottedDivider()

            Column(
                modifier = Modifier
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(
                            "Date",
                            style = MaterialTheme.typography.labelSmall,
                            color = Color.Gray
                        )
                        Text(
                            "Dec 25nd",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Column {
                        Text(
                            "Time",
                            style = MaterialTheme.typography.labelSmall,
                            color = Color.Gray
                        )
                        Text(
                            "6pm",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Column {
                        Text(
                            "Seat",
                            style = MaterialTheme.typography.labelSmall,
                            color = Color.Gray
                        )
                        Text(
                            "R4 5-7",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .padding(bottom = 12.dp),
            ) {
                RandomBarcode(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                )
            }
        }
    }
}

@Composable
fun RandomBarcode(modifier: Modifier = Modifier) {
    val bars = List(40) { (4..16).random() } // random widths
    val gap = 4f

    Canvas(modifier = modifier) {
        val totalBarsWidth = bars.sumOf { it } + gap * (bars.size - 1)
        var x = (size.width - totalBarsWidth) / 2f // center offset

        bars.forEach { barWidth ->
            drawRect(
                color = Color.Black,
                topLeft = Offset(x, 0f),
                size = Size(barWidth.toFloat(), size.height)
            )
            x += barWidth + gap
        }
    }
}


@Composable
fun DottedDivider(
    modifier: Modifier = Modifier,
    dashLength: Dp = 12.dp,
    gap: Dp = 8.dp,
    lineHeight: Dp = 1.dp,
    lineColor: Color = Color.Gray,
    strokeWidth: Dp = 2.dp
) {
    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(lineHeight)
    ) {
        val dashLength = dashLength.toPx()
        val gap = gap.toPx()
        var x = 0f
        val y = size.height / 2
        while (x < size.width) {
            drawLine(
                color = lineColor,
                start = Offset(x, y),
                end = Offset(x + dashLength, y),
                strokeWidth = strokeWidth.toPx()
            )
            x += dashLength + gap
        }
    }
}


@Preview
@Composable
fun NeomorphicTicketWithInnerShadowPreview() {
    ComposableTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
                .background(White)
        ) {
            NeomorphicTicket(
                modifier = Modifier
                    .align(Alignment.Center)
            )
        }
    }
}

val verticalTicketShapeCode = """
@Composable
fun NeomorphicShapeTicket(
    modifier: Modifier = Modifier,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val elevation by animateDpAsState(
        targetValue = if (isPressed) 2.dp else 6.dp,
        label = "card_elevation"
    )
    val ticketHeight = 400.dp
    val ticketWith= 320.dp
    val cutoutFraction = 0.7f
    val cutoutCenterHeight = ticketHeight * cutoutFraction
    val ticketShape = VerticalTicketShape(
        cornerRadius = 12.dp,
        cutoutRadius = 10.dp,
        cutoutHeightFraction = cutoutFraction
    )
    Box(
        modifier = modifier
            .size(width = ticketWith, height = ticketHeight)
            .neumorphicShapeShadow(
                shape = ticketShape,
                lightShadowColor = if (isPressed) LightDarkShadowColor else LightShadowColor,
                darkShadowColor = if (isPressed) LightShadowColor else LightDarkShadowColor,
                lightOffset = Offset(-6f, -6f),
                darkOffset = Offset(6f, 6f),
                elevation = elevation
            )
            .background(LightNeumorphicBackground, ticketShape)
            .clip(shape = ticketShape)
            .clickable(interactionSource = interactionSource, indication = null) { }
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                painter = painterResource(R.drawable.img),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(cutoutCenterHeight)
                    .padding(32.dp)
                    .clip(RoundedCornerShape(12.dp))
            )
            DottedDivider()
            Column(
                modifier = Modifier
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(
                            "Date",
                            style = MaterialTheme.typography.labelSmall,
                            color = Color.Gray
                        )
                        Text(
                            "Dec 25nd",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Column {
                        Text(
                            "Time",
                            style = MaterialTheme.typography.labelSmall,
                            color = Color.Gray
                        )
                        Text(
                            "6pm",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Column {
                        Text(
                            "Seat",
                            style = MaterialTheme.typography.labelSmall,
                            color = Color.Gray
                        )
                        Text(
                            "R4 5-7",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .padding(bottom = 12.dp),
            ) {
                RandomBarcode(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                )
            }
        }
    }
}
@Composable
fun RandomBarcode(modifier: Modifier = Modifier) {
    val bars = List(40) { (4..16).random() } // random widths
    val gap = 4f
    Canvas(modifier = modifier) {
        val totalBarsWidth = bars.sumOf { it } + gap * (bars.size - 1)
        var x = (size.width - totalBarsWidth) / 2f // center offset
        bars.forEach { barWidth ->
            drawRect(
                color = Color.Black,
                topLeft = Offset(x, 0f),
                size = Size(barWidth.toFloat(), size.height)
            )
            x += barWidth + gap
        }
    }
}
@Composable
fun DottedDivider(
    modifier: Modifier = Modifier,
    dashLength: Dp = 12.dp,
    gap: Dp = 8.dp,
    lineHeight: Dp = 1.dp,
    lineColor: Color = Color.Gray,
    strokeWidth: Dp = 2.dp
) {
    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(lineHeight)
    ) {
        val dashLength = dashLength.toPx()
        val gap = gap.toPx()
        var x = 0f
        val y = size.height / 2
        while (x < size.width) {
            drawLine(
                color = lineColor,
                start = Offset(x, y),
                end = Offset(x + dashLength, y),
                strokeWidth = strokeWidth.toPx()
            )
            x += dashLength + gap
        }
    }
}

class VerticalTicketShape(
    private val cornerRadius: Dp = 16.dp,
    private val cutoutRadius: Dp = 16.dp,
    /**
     * Fraction of the height where the cutout center will be placed.
     * Example: 0.5f = middle, 0.25f = upper quarter.
     */
    private val cutoutHeightFraction: Float = 0.80f
) : Shape {

    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        with(density) {
            val cornerRadiusPx = cornerRadius.toPx()
            val cutoutRadiusPx = cutoutRadius.toPx()

            // Base ticket rectangle with rounded corners
            val ticketPath = Path().apply {
                addRoundRect(
                    RoundRect(
                        rect = Rect(0f, 0f, size.width, size.height),
                        cornerRadius = CornerRadius(cornerRadiusPx, cornerRadiusPx)
                    )
                )
            }

            // Calculate vertical center for cutouts
            val cutoutCenterY = size.height * cutoutHeightFraction

            // Cutout on the left edge
            val leftCutout = Path().apply {
                addOval(
                    Rect(
                        left = -cutoutRadiusPx,
                        top = cutoutCenterY - cutoutRadiusPx,
                        right = cutoutRadiusPx,
                        bottom = cutoutCenterY + cutoutRadiusPx
                    )
                )
            }

            // Cutout on the right edge
            val rightCutout = Path().apply {
                addOval(
                    Rect(
                        left = size.width - cutoutRadiusPx,
                        top = cutoutCenterY - cutoutRadiusPx,
                        right = size.width + cutoutRadiusPx,
                        bottom = cutoutCenterY + cutoutRadiusPx
                    )
                )
            }

            // Subtract cutouts from base shape
            val finalPath = Path().apply {
                op(ticketPath, leftCutout, PathOperation.Difference)
                op(this, rightCutout, PathOperation.Difference)
            }

            return Outline.Generic(finalPath)
        }
    }
}
""".trimIndent()