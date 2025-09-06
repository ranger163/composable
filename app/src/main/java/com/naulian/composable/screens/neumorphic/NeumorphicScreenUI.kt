package com.naulian.composable.screens.neumorphic

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.naulian.composable.component.CodeBlock
import com.naulian.composable.theme.ComposeTheme
import com.naulian.modify.ExperimentalModifyApi
import com.naulian.modify.HugeIcons
import com.naulian.modify.columnItem

@OptIn(ExperimentalModifyApi::class, ExperimentalMaterial3Api::class)
@Composable
fun NeumorphicScreenUI(onBack : () -> Unit = {}){
    val code = remember { neumorphicCode }
    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            painter = painterResource(HugeIcons.Back),
                            contentDescription = "Back icon"
                        )
                    }
                },
                title = { Text(text = "Neumorphism") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        }
    ) { scaffoldPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(scaffoldPadding)
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
                        contentAlignment = Alignment.Center,
                        lightColor = MaterialTheme.colorScheme.surfaceBright,
                        shadowColor = MaterialTheme.colorScheme.surfaceDim
                    )

                    NeuMorphicDown(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                            .aspectRatio(1f),
                        contentAlignment = Alignment.Center,
                        lightColor = MaterialTheme.colorScheme.surfaceBright,
                        shadowColor = MaterialTheme.colorScheme.surfaceDim
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
                        contentAlignment = Alignment.Center,
                        lightColor = MaterialTheme.colorScheme.surfaceBright,
                        shadowColor = MaterialTheme.colorScheme.surfaceDim
                    )

                    NeuMorphicDown(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                            .aspectRatio(1f),
                        shape = CircleShape,
                        contentAlignment = Alignment.Center,
                        lightColor = MaterialTheme.colorScheme.surfaceBright,
                        shadowColor = MaterialTheme.colorScheme.surfaceDim
                    )
                }

                NeuMorphicUP(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp),
                    shape = CircleShape,
                    contentPadding = 8.dp,
                    contentAlignment = Alignment.Center,
                    lightColor = MaterialTheme.colorScheme.surfaceBright,
                    shadowColor = MaterialTheme.colorScheme.surfaceDim
                )

                NeuMorphicDown(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp),
                    shape = CircleShape,
                    contentPadding = 8.dp,
                    contentAlignment = Alignment.Center,
                    lightColor = MaterialTheme.colorScheme.surfaceBright,
                    shadowColor = MaterialTheme.colorScheme.surfaceDim
                )

                CodeBlock(
                    source = code,
                    language = "kotlin"
                )

                Text(text = "Bonus")
                var touch by remember { mutableStateOf(false) }

                NeumorphicTouch(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f),
                    onTouch = { touch = it },
                    contentAlignment = Alignment.Center,
                    firstColor = MaterialTheme.colorScheme.surfaceBright,
                    secondColor = MaterialTheme.colorScheme.surfaceDim
                ) {
                    Text(text = if (touch) "Touch" else "Not Touch")
                }
            }
        }
    }
}

@Preview
@Composable
private fun NeumorphicScreenUIPreview() {
    ComposeTheme {
        NeumorphicScreenUI()
    }
}