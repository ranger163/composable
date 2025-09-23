package com.naulian.composable.acc.clock

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.naulian.composable.core.LocalNavController
import com.naulian.composable.core.component.CodeBlock
import com.naulian.composable.core.component.ComposableTopAppBar

@Composable
fun ClockScreen() {
    val navController = LocalNavController.current

    ClockScreenUI (
        onBack = { navController.navigateUp() }
    )
}

@Composable
private fun ClockScreenUI(onBack : () -> Unit = {}) {
    val code = remember { clockCode }

    Scaffold(
        topBar = {
            ComposableTopAppBar(
                title = "Analog Clock",
                onBack = onBack
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(40.dp)
        ) {

            item {
                Clock(modifier = Modifier.fillMaxWidth())
            }

            item {
                CodeBlock(
                    source = code,
                    language = "kotlin",
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}