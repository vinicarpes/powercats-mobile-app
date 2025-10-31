package com.example.powercats.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

@Composable
fun AlertLevelTag(
    level: String,
    modifier: Modifier = Modifier,
) {
    val (bgColor, textColor) =
        when (level) {
            "Crítico" -> Color(0xFFF5E9EC) to Color(0xFFCC3750)
            "Alto" -> Color(0xFFF5EFE9) to Color(0xFFE8891C)
            "Médio" -> Color(0xFFE1F5EC) to Color(0xFF03AB4F)
            "Baixo" -> Color(0xFFEBEBFC) to Color(0xFF6363DB)
            else -> MaterialTheme.colorScheme.primaryContainer to MaterialTheme.colorScheme.onPrimaryContainer
        }

    Surface(
        modifier = modifier,
        color = bgColor,
        shape = RoundedCornerShape(8.dp),
    ) {
        Text(
            text = level,
            color = textColor,
            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Medium),
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}
