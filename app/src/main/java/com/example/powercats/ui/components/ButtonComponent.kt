package com.example.powercats.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.powercats.ui.theme.md_theme_dark_primary

@Composable
fun ButtonComponent(
    text: String,
    onClick: () -> Unit,
    isPrimaryButton: Boolean = true,
) {
    val buttonColor =
        if (isPrimaryButton) {
            ButtonColors(
                containerColor = md_theme_dark_primary,
                contentColor = Color.White,
                disabledContainerColor = Color.Unspecified,
                disabledContentColor = Color.Unspecified,
            )
        } else {
            ButtonColors(
                containerColor = Color.White,
                contentColor = Color.Gray,
                disabledContainerColor = Color.Unspecified,
                disabledContentColor = Color.Unspecified,
            )
        }
    Button(
        onClick = onClick,
        colors = buttonColor,
        contentPadding = PaddingValues(16.dp, 12.dp),
        shape = RoundedCornerShape(10.dp),
        border = BorderStroke(1.dp, Color(0xFFE1E1E5)),
        modifier =
            Modifier
                .width(160.dp)
                .padding(top = 32.dp),
    ) {
        Text(text = text, modifier = Modifier.height(20.dp))
    }
}
