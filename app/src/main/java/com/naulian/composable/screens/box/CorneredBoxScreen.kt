package com.naulian.composable.screens.box

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.naulian.anhance.Lorem
import com.naulian.composable.component.CodeBlock
import com.naulian.composable.theme.ComposeTheme
import com.naulian.modify.ExperimentalModifyApi
import com.naulian.modify.Gray
import com.naulian.modify.columnItem

@OptIn(ExperimentalModifyApi::class)
@Composable
fun CorneredBoxScreen() {
    val code = remember {
        """
            @Composable
            fun CorneredBox(
                modifier: Modifier = Modifier,
                cornerColor: Color = Color.White,
                containerColor: Color = Color.Transparent,
                cornerStrokeWidth: Dp = 6.dp,
                cornerSize: Dp = 20.dp,
                shape: Shape = RoundedCornerShape(10.dp),
                contentAlignment: Alignment = Alignment.TopStart,
                onClick: (() -> Unit)? = null,
                content: @Composable BoxScope.() -> Unit
            ) {
                Box(
                    modifier = modifier
                        .background(containerColor, shape)
                        .clip(shape)
                        .drawBehind {
                            var cornerRadius = 0f
                            val outline = shape.createOutline(size, layoutDirection, this)
                            if (outline is Outline.Rounded) {
                                cornerRadius = outline.roundRect.topLeftCornerRadius.x
                            }
                            val centerX = size.width / 2f
                            val centerY = size.height / 2f
            
                            val startX = 0f
                            val startY = 0f
            
                            val endX = size.width
                            val endY = size.height
            
                            val horLineSize = size.width - cornerSize.toPx() * 2f
                            val verLineSize = size.height - cornerSize.toPx() * 2f
            
                            with(drawContext.canvas.nativeCanvas) {
                                val checkPoint = saveLayer(null, null)
            
                                drawRoundRect(
                                    color = cornerColor,
                                    cornerRadius = CornerRadius(cornerRadius),
                                    style = Stroke(width = cornerStrokeWidth.toPx())
                                )
            
                                //top
                                drawLine(
                                    color = Yellow,
                                    start = Offset(
                                        x = centerX - horLineSize / 2,
                                        y = startY
                                    ),
                                    end = Offset(
                                        x = centerX + horLineSize / 2,
                                        y = startY
                                    ),
                                    strokeWidth = 20f,
                                    blendMode = BlendMode.Clear
                                )
            
                                //bottom
                                drawLine(
                                    color = Yellow,
                                    start = Offset(
                                        x = centerX - horLineSize / 2,
                                        y = endY
                                    ),
                                    end = Offset(
                                        x = centerX + horLineSize / 2,
                                        y = endY
                                    ),
                                    strokeWidth = 20f,
                                    blendMode = BlendMode.Clear
                                )
            
                                //left
                                drawLine(
                                    color = Yellow,
                                    start = Offset(
                                        x = startX,
                                        y = centerY - verLineSize / 2
                                    ),
                                    end = Offset(
                                        x = startX,
                                        y = centerY + verLineSize / 2
                                    ),
                                    strokeWidth = 20f,
                                    blendMode = BlendMode.Clear
                                )
            
                                //right
                                drawLine(
                                    color = Yellow,
                                    start = Offset(
                                        x = endX,
                                        y = centerY - verLineSize / 2
                                    ),
                                    end = Offset(
                                        x = endX,
                                        y = centerY + verLineSize / 2
                                    ),
                                    strokeWidth = 20f,
                                    blendMode = BlendMode.Clear
                                )
            
                                restoreToCount(checkPoint)
                            }
                        }
                        .clickable(onClick != null) { onClick?.invoke() },
                    contentAlignment = contentAlignment,
                    content = content
                )
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
                CorneredBox(
                    modifier = Modifier
                        .fillMaxWidth(),
                    cornerColor = Gray,
                    onClick = {},
                    contentPadding = PaddingValues(12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = Lorem.short, fontSize = 64.sp, color = Gray)
                }

                CodeBlock(source = code, language = "kotlin")
            }
        }
    }
}

@Preview
@Composable
private fun CorneredBoxScreenPreview() {
    ComposeTheme {
        CorneredBoxScreen()
    }
}