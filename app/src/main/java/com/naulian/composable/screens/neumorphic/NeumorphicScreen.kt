package com.naulian.composable.screens.neumorphic

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.naulian.anhance.copyString
import com.naulian.composable.theme.ComposeTheme
import com.naulian.composer.ui.CodeComponent
import com.naulian.modify.ExperimentalModifyApi
import com.naulian.modify.HugeIcons
import com.naulian.modify.columnItem

@OptIn(ExperimentalModifyApi::class)
@Composable
fun NeumorphicScreen() {
    val context = LocalContext.current

    val code = remember {
        """
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(scaffoldPadding)
                    .background(Color(0xFFEEEEEE))
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(48.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    NeuMorphicUP(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                            .aspectRatio(1f),
                        contentAlignment = Alignment.Center
                    )
    
                    NeuMorphicDown(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                            .aspectRatio(1f),
                        contentAlignment = Alignment.Center
                    )
                }
    
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    NeuMorphicUP(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                            .aspectRatio(1f),
                        shape = CircleShape,
                        contentAlignment = Alignment.Center
                    )
    
                    NeuMorphicDown(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                            .aspectRatio(1f),
                        shape = CircleShape,
                        contentAlignment = Alignment.Center
                    )
                }
    
                NeuMorphicUP(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp),
                    shape = CircleShape,
                    contentPadding = 8.dp,
                    contentAlignment = Alignment.Center
                )
    
                NeuMorphicDown(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp),
                    shape = CircleShape,
                    contentPadding = 8.dp,
                    contentAlignment = Alignment.Center
                )
            }
        """.trimIndent()
    }
    Scaffold { scaffoldPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(scaffoldPadding)
                .background(Color(0xFFEEEEEE))
                .padding(20.dp)
        ) {
            columnItem(
                verticalArrangement = Arrangement.spacedBy(48.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    NeuMorphicUP(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                            .aspectRatio(1f),
                        contentAlignment = Alignment.Center
                    )

                    NeuMorphicDown(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                            .aspectRatio(1f),
                        contentAlignment = Alignment.Center
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    NeuMorphicUP(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                            .aspectRatio(1f),
                        shape = CircleShape,
                        contentAlignment = Alignment.Center
                    )

                    NeuMorphicDown(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                            .aspectRatio(1f),
                        shape = CircleShape,
                        contentAlignment = Alignment.Center
                    )
                }

                NeuMorphicUP(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp),
                    shape = CircleShape,
                    contentPadding = 8.dp,
                    contentAlignment = Alignment.Center
                )

                NeuMorphicDown(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp),
                    shape = CircleShape,
                    contentPadding = 8.dp,
                    contentAlignment = Alignment.Center
                )

                SelectionContainer {
                    CodeComponent(
                        source = code,
                        language = "kotlin",
                        actionIcon = HugeIcons.Copy,
                        onClickAction = { context.copyString(code) }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun NeumorphicScreenPreview() {
    ComposeTheme {
        NeumorphicScreen()
    }
}