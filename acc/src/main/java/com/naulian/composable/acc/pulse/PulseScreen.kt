package com.naulian.composable.acc.pulse

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.naulian.composable.core.LocalNavController
import com.naulian.composable.core.component.CodeBlock
import com.naulian.composable.core.component.ComposableTopAppBar
import com.naulian.modify.columnItem

@Composable
fun PulseScreen() {
    val navController = LocalNavController.current

    PulseScreenUI(
        onBack = { navController.navigateUp() }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PulseScreenUI(onBack: () -> Unit = {}) {
    Scaffold(
        topBar = {
            ComposableTopAppBar(
                title = "Pulse Animation",
                onBack = onBack
            )
        }
    ) { scaffoldPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(scaffoldPadding)
                .padding(20.dp)
        ) {
            columnItem {
                PulseAnimation()
                Spacer(modifier = Modifier.height(20.dp))

                CodeBlock(
                    source = """
                        @Composable
                        fun PulseAnimation(modifier: Modifier = Modifier) {
                            val infiniteTransition = rememberInfiniteTransition()
                            val pulse by infiniteTransition.animateFloat(
                                initialValue = 1f,
                                targetValue = 1f,
                                animationSpec = infiniteRepeatable(
                                    animation = keyframes {
                                        durationMillis = 800
                                        1.0f at 0 using LinearEasing
                                        1.2f at 400 using LinearEasing
                                        1.0f at 800 using LinearEasing
                                    },
                                    repeatMode = RepeatMode.Restart
                                )
                            )
                        
                            Icon(
                                imageVector = Icons.Filled.Favorite,
                                contentDescription = "Heart",
                                tint = Color(0xFFFF0000),
                                modifier = modifier
                                    .size(64.dp)
                                    .scale(pulse)
                            )
                        }
                    """.trimIndent(),
                    codeName = "TypingText",
                    language = "kotlin"
                )
            }
        }
    }
}