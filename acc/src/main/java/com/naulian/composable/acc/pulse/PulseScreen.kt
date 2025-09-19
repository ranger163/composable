package com.naulian.composable.acc.pulse

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
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
                PulseAnimation(modifier = Modifier.align(Alignment.CenterHorizontally))
                Spacer(modifier = Modifier.height(20.dp))

                CodeBlock(
                    source = """
                        @OptIn(ExperimentalMaterial3ExpressiveApi::class)
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
                        
                            Box(
                                modifier = modifier
                                    .size(100.dp)
                                    .scale(pulse)
                                    .background(
                                        shape = MaterialShapes.Heart.toShape(),
                                        color = Color(0xFFEF002F),
                                    )
                            )
                        }
                    """.trimIndent(),
                    codeName = "Pulsing Heart",
                    language = "kotlin"
                )
            }
        }
    }
}