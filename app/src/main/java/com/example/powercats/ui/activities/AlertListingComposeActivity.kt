package com.example.powercats.ui.activities

import AlertListingViewModel
import AlertsState
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.powercats.ui.components.TopBar
import com.example.powercats.ui.model.AlertUi
import com.example.powercats.ui.theme.PowerCATSTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

class AlertListingComposeActivity : ComponentActivity() {
    private val viewModel: AlertListingViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PowerCATSTheme {
                Scaffold(
                    topBar = { TopBar() },
                    modifier = Modifier.fillMaxSize(),
                ) { innerPadding ->
                    AlertScreen(modifier = Modifier.padding(innerPadding), viewModel = viewModel)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
    }
}

@Composable
fun AlertScreen(
    viewModel: AlertListingViewModel,
    modifier: Modifier,
) {
    val state = viewModel.state.collectAsState()
    when (val currentState = state.value) {
        is AlertsState.Loading -> {
            Text("Carregando...")
        }

        is AlertsState.Success -> {
            AlertList(alerts = currentState.alerts, modifier = modifier)
        }

        is AlertsState.Error -> {
            Text("Erro: ${currentState.message}")
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

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                AlertLevelTag(title = alertUi.alertLevel)
                AlertStatusTag(status = alertUi.status)
            }
        }
    }
}

@Composable
fun AlertStatusTag(status: String) {
    val (bgColor, textColor) =
        when (status.lowercase()) {
            "ativo" -> Color(0xFFE8F5E9) to Color(0xFF2E7D32)
            "resolvido" -> Color(0xFFE3F2FD) to Color(0xFF1565C0)
            "pendente" -> Color(0xFFFFF3E0) to Color(0xFFEF6C00)
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
                status = "ativo",
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
            status = "Ativo",
        ),
        AlertUi(
            location = "Rua das Flores, 50 - Curitiba",
            latitude = "-25.43",
            longitude = "-49.27",
            dateTime = "23/10/2025 10:30",
            alertLevel = "Alto",
            status = "Pendente",
        ),
        AlertUi(
            location = "Av. Paulista, 1000 - São Paulo",
            latitude = "-23.56",
            longitude = "-46.64",
            dateTime = "23/10/2025 12:10",
            alertLevel = "Médio",
            status = "Resolvido",
        ),
        AlertUi(
            location = "Rua das Flores, 50 - Curitiba",
            latitude = "-25.43",
            longitude = "-49.27",
            dateTime = "23/10/2025 10:30",
            alertLevel = "Baixo",
            status = "Cancelado",
        ),
    )
