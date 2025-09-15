package com.naulian.composable.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.naulian.composable.theme.ComposableTheme
import com.naulian.modify.White

@Composable
fun HomeSection(
    modifier: Modifier = Modifier,
    horizontalPadding: Dp = 20.dp,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    content: @Composable ColumnScope.() -> Unit,
) {
    ConstraintLayout(modifier = modifier.fillMaxWidth()) {
        val (left, center, right) = createRefs()

        VerticalDivider(
            modifier = Modifier
                .padding(start = horizontalPadding)
                .height(intrinsicSize = IntrinsicSize.Max)
                .constrainAs(left) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    height = Dimension.fillToConstraints
                }
        )

        Column(
            modifier = Modifier
                .constrainAs(center) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(left.end)
                    end.linkTo(right.start)
                    width = Dimension.fillToConstraints
                }
                .padding(contentPadding),
            content = content
        )

        VerticalDivider(
            modifier = Modifier
                .padding(end = horizontalPadding)
                .constrainAs(right) {
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    height = Dimension.fillToConstraints
                }
        )
    }
}

@Preview
@Composable
private fun HomeSectionPreview(modifier: Modifier = Modifier) {
    ComposableTheme {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .background(White)
        ) {
            HomeSection(modifier = modifier) {
                Text(text = "Home Section", style = MaterialTheme.typography.headlineLarge)
            }
        }
    }
}