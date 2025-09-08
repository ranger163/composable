package com.naulian.composable.screens.box

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.naulian.anhance.Lorem
import com.naulian.composable.component.CodeBlock
import com.naulian.composable.theme.ComposableTheme
import com.naulian.modify.ExperimentalModifyApi
import com.naulian.modify.Gray
import com.naulian.modify.HugeIcons
import com.naulian.modify.columnItem

@OptIn(ExperimentalMaterial3Api::class, ExperimentalModifyApi::class)
@Composable
fun CorneredBoxScreenUI(onBack: () -> Unit = {}) {
    val code = remember { corneredBoxCode }

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick =onBack) {
                        Icon(
                            painter = painterResource(HugeIcons.Back),
                            contentDescription = "Back icon"
                        )
                    }
                },
                title = { Text(text = "Cornered Box") },
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
private fun CorneredBoxScreenUIPreview() {
    ComposableTheme {
        CorneredBoxScreenUI()
    }
}