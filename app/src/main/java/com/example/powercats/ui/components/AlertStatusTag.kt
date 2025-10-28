package com.example.powercats.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

@Composable
fun AlertStatusTag(status: String) {
    val (bgColor, textColor) =
        when (status) {
            "Ativo" -> Color(0xFFE8F5E9) to Color(0xFF2E7D32)
            "Resolvido" -> Color(0xFFE3F2FD) to Color(0xFF1565C0)
            "Pendente" -> Color(0xFFFFF3E0) to Color(0xFFEF6C00)
            "Cancelado" -> Color(0xFFFFE5E5) to Color(0xFFD32F2F)
            else -> Color(0xFFF5F5F5) to Color(0xFF757575)
        }

    Box(
        modifier =
            Modifier
                .background(bgColor, shape = RoundedCornerShape(8.dp))
                .padding(horizontal = 10.dp, vertical = 4.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = status,
            color = textColor,
            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Medium),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}