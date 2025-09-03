package com.naulian.composable.screens.background

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.naulian.anhance.Lorem
import com.naulian.anhance.copyString
import com.naulian.composable.component.CodeBlock
import com.naulian.composable.theme.ComposeTheme
import com.naulian.modify.ExperimentalModifyApi
import com.naulian.modify.Gray
import com.naulian.modify.HugeIcons
import com.naulian.modify.columnItem

@OptIn(ExperimentalModifyApi::class)
@Composable
fun GridBackgroundScreen() {
    val context = LocalContext.current

    val code = remember {
        """
            fun Modifier.gridBackground(
                color: Color,
                lineColor: Color = Color(0xFFEFEFEF),
                spacing: Dp = 10.dp,
                shape: Shape = RectangleShape
            ) = background(color, shape).drawBehind {
                val spacingPx = spacing.toPx()
                val width = size.width
                val height = size.height

                val lineCountX = width / spacingPx
                val lineCountY = width / spacingPx

                val outline = shape.createOutline(size, layoutDirection, this)
                val path = when (outline) {
                    is Outline.Rectangle -> Path().apply { addRect(outline.rect) }
                    is Outline.Rounded -> Path().apply { addRoundRect(outline.roundRect) }
                    is Outline.Generic -> outline.path
                }

                clipPath(path) {
                    //draw vertical lines
                    for (i in 1..lineCountX.toInt() - 1) {
                        drawLine(
                            color = lineColor,
                            start = Offset(i * spacingPx, 0f),
                            end = Offset(i * spacingPx, height),
                            strokeWidth = 1f
                        )
                    }

                    //draw horizonal lines
                    for (i in 1..lineCountY.toInt() - 1) {
                        drawLine(
                            color = lineColor,
                            start = Offset(0f, i * spacingPx),
                            end = Offset(width, i * spacingPx),
                            strokeWidth = 1f
                        )
                    }
                }
            }
        """.trimIndent()
    }
    Scaffold { scaffoldPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(scaffoldPadding)
                .padding(20.dp)
        ) {
            columnItem(
                verticalArrangement = Arrangement.spacedBy(48.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f)
                        .gridBackground(
                            color = MaterialTheme.colorScheme.tertiary,
                            lineColor = MaterialTheme.colorScheme.surfaceDim,
                            shape = RoundedCornerShape(20.dp)
                        )
                        .padding(20.dp)
                ) {
                    Text(text = Lorem.short, fontSize = 64.sp, color = Gray)
                }

                CodeBlock(
                    source = code,
                    language = "kotlin",
                    actionIcon = HugeIcons.Copy,
                    onClickAction = { context.copyString(code) }
                )
            }
        }
    }
}

@Preview
@Composable
private fun GridBackgroundScreenPreview() {
    ComposeTheme {
        GridBackgroundScreen()
    }
}