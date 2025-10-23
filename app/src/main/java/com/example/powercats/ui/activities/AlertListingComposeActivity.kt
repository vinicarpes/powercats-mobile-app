package com.example.powercats.ui.activities

import android.R
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.powercats.R.drawable.device_map
import com.example.powercats.R.drawable.notifications
import com.example.powercats.R.drawable.sensors_register
import com.example.powercats.R.drawable.user_register
import com.example.powercats.ui.components.BottomNavigationBar
import com.example.powercats.ui.components.TopBar
import com.example.powercats.ui.model.AlertUi
import com.example.powercats.ui.theme.PowerCATSTheme

class AlertListingComposeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PowerCATSTheme {
                Scaffold(
                    topBar = { TopBar() },
                    modifier = Modifier.fillMaxSize(),
                ) { innerPadding ->
                    AlertList(modifier = Modifier.padding(innerPadding), alerts = sampleAlerts())
                }
            }
        }
    }
}

@Composable
fun AlertList(
    modifier: Modifier = Modifier,
    alerts: List<AlertUi>,
) {
    LazyColumn(
        modifier =
            modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
        contentPadding = PaddingValues(vertical = 12.dp, horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        items(alerts.size) { i ->
            AlertItem(alertUi = alerts[i])
        }
    }
}

@Composable
fun AlertItem(
    alertUi: AlertUi,
    modifier: Modifier = Modifier,
) {
    val backgroundColor by animateColorAsState(
        when (alertUi.alertLevel.lowercase()) {
            "crítico", "critico" -> MaterialTheme.colorScheme.errorContainer
            "alto" -> Color.White
            "médio", "medio" -> Color(0xFFFFF59D)
            "baixo" -> Color(0xFFC8E6C9)
            else -> MaterialTheme.colorScheme.surface
        },
        label = "Alert color",
    )

    Card(
        modifier =
            modifier
                .fillMaxWidth()
                .shadow(2.dp, RoundedCornerShape(12.dp))
                .clickable { /* ação futura: abrir detalhe */ },
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    ) {
        Row(
            modifier =
                Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            // Coluna principal com local e data
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                Text(
                    text = alertUi.location,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.onSurface,
                )
                Text(
                    text = alertUi.dateTime,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            // Tag de nível
            AlertLevelTag(title = alertUi.alertLevel)
        }
    }
}

@Composable
private fun AlertLevelTag(
    title: String,
    modifier: Modifier = Modifier,
) {
    val (bgColor, textColor) =
        when (title.lowercase()) {
            "crítico", "critico" -> Color(0xFFF5E9EC) to Color(0xFFCC3750)
            "alto" -> Color(0xFFF5EFE9) to Color(0xFFE8891C)
            "médio", "medio" -> Color(0xFFE1F5EC) to Color(0xFF03AB4F)
            "baixo" -> Color(0xFFEBEBFC) to Color(0xFF6363DB)
            else -> MaterialTheme.colorScheme.primaryContainer to MaterialTheme.colorScheme.onPrimaryContainer
        }

    Surface(
        modifier = modifier,
        color = bgColor,
        shape = RoundedCornerShape(8.dp),
    ) {
        Text(
            text = title,
            color = textColor,
            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Medium),
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

@Composable
fun AlertScreenTitle(
    modifier: Modifier = Modifier,
    text: String,
) {
    Text(
        text = text,
        modifier = modifier,
    )
}

@Preview
@Composable
private fun AlertItemPreview() {
    AlertItem(
        alertUi =
            AlertUi(
                location = "Rua Mauro Ramos, 500 - Florianópolis",
                latitude = "-26.00",
                longitude = "",
                dateTime = "23/10/2025 14:04",
                alertLevel = "Crítico",
            ),
    )
}

@Preview
@Composable
private fun AlertScreenPreview() {
    PowerCATSTheme {
        Scaffold(
            topBar = { TopBar() },
            modifier = Modifier.fillMaxSize(),
        ) { innerPadding ->
            AlertList(
                modifier = Modifier.padding(innerPadding),
                alerts = sampleAlerts(),
            )
        }
    }
}

private fun sampleAlerts() =
    listOf(
        AlertUi(
            location = "Rua Mauro Ramos, 500 - Florianópolis",
            latitude = "-26.00",
            longitude = "",
            dateTime = "23/10/2025 14:04",
            alertLevel = "Crítico",
        ),
        AlertUi(
            location = "Rua das Flores, 50 - Curitiba",
            latitude = "-25.43",
            longitude = "-49.27",
            dateTime = "23/10/2025 10:30",
            alertLevel = "Alto",
        ),
        AlertUi(
            location = "Av. Paulista, 1000 - São Paulo",
            latitude = "-23.56",
            longitude = "-46.64",
            dateTime = "23/10/2025 12:10",
            alertLevel = "Médio",
        ),
        AlertUi(
            location = "Rua das Flores, 50 - Curitiba",
            latitude = "-25.43",
            longitude = "-49.27",
            dateTime = "23/10/2025 10:30",
            alertLevel = "Baixo",
        ),
    )
