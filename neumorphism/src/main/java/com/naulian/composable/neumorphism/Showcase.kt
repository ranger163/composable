package com.naulian.composable.neumorphism

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.naulian.modify.HugeIcons

@Preview
@Composable
private fun ShowcasePreview() {
    NeumorphicPreviewColumn(
        modifier = Modifier.fillMaxSize()
    ) {

        NeumorphicBorder(
            modifier = Modifier.fillMaxWidth(),
            cornerRadiusDp = 10.dp,
            borderThickness = 2.dp,
            contentPadding = PaddingValues(20.dp)
        ) {

            Column(
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                NeumorphicTextField(
                    modifier = Modifier.fillMaxWidth(),
                    shape = CircleShape,
                    state = TextFieldState("Text Field")
                )

                NeumorphicTextField(
                    modifier = Modifier.fillMaxWidth(),
                    shape = CircleShape,
                    state = TextFieldState(),
                    placeHolder = "Text Field with Placeholder"
                )

                NeumorphicButton(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {},
                ) {
                    Text(text = "Button")
                }

                NeumorphicButton(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {},
                    enabled = false
                ) {
                    Text(text = "Disabled Button")
                }
            }
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {

            NeumorphicIconButton(onClick = {}) {
                Icon(
                    painter = painterResource(HugeIcons.Favourite),
                    contentDescription = "Fav"
                )
            }

            NeumorphicIconButton(onClick = {}) {
                Icon(
                    painter = painterResource(HugeIcons.Delete),
                    contentDescription = "Fav"
                )
            }

            NeumorphicIconButton(onClick = {}) {
                Icon(
                    painter = painterResource(HugeIcons.Copy),
                    contentDescription = "Fav"
                )
            }

            NeumorphicIconButton(onClick = {}) {
                Icon(
                    painter = painterResource(HugeIcons.Home),
                    contentDescription = "Fav"
                )
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Switch Unchecked")

            NeumorphicSwitch(checked = false, onCheckedChange = {})
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Switch Check")

            NeumorphicSwitch(checked = true, onCheckedChange = {})
        }
    }
}