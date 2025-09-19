package com.naulian.composable.icc.rating

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.naulian.composable.core.LocalNavController
import com.naulian.composable.core.component.CodeBlock
import com.naulian.composable.core.theme.ComposableTheme
import com.naulian.composable.icc.R
import com.naulian.modify.ExperimentalModifyApi
import com.naulian.modify.HugeIcons
import com.naulian.modify.columnItem

@Composable
fun RatingStarsScreen() {
    val navController = LocalNavController.current

    RatingStarScreenUI(
        onBack = { navController.navigateUp() }
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalModifyApi::class)
@Composable
private fun RatingStarScreenUI(onBack: () -> Unit = {}) {
    val code = remember { ratingStarsCode }

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
                title = { Text(text = "RatingStars") },
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
                verticalArrangement = Arrangement.spacedBy(48.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                var ratingValue by remember { mutableIntStateOf(2) }

                RatingStars(
                    modifier = Modifier.padding(20.dp),
                    rating = ratingValue,
                    ratedStarIcon = painterResource(R.drawable.ic_star_filled),
                    unRatedStarIcon = painterResource(R.drawable.ic_star_outlined),
                    onRatingChange = {
                        ratingValue = it
                    },
                    iconSize = 48.dp
                )

                CodeBlock(
                    source = code,
                    language = "kotlin"
                )
            }
        }
    }
}

@Preview
@Composable
private fun RatingStarScreenUIPreview() {
    ComposableTheme {
        RatingStarScreenUI()
    }
}