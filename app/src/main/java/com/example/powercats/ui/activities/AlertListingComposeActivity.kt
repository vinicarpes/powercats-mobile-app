package com.example.powercats.ui.activities

import AlertListingViewModel
import AlertsState
import android.content.Intent
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.powercats.ui.components.AlertLevelTag
import com.example.powercats.ui.components.AlertStatusTag
import com.example.powercats.ui.components.TopBar
import com.example.powercats.ui.model.AlertUi
import com.example.powercats.ui.theme.PowerCATSTheme
import java.io.Serializable
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
        viewModel.getAlerts()
    }
}

@Composable
private fun AlertScreen(
    viewModel: AlertListingViewModel,
    modifier: Modifier,
) {
    val state = viewModel.state.collectAsState()

    Box(modifier = modifier.fillMaxSize()) {
        val alerts = (state.value as? AlertsState.Success)?.alerts ?: emptyList()
        AlertList(alerts = alerts, modifier = Modifier.fillMaxSize())

        if (state.value is AlertsState.Loading) {
            Box(
                modifier =
                    Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.3f)),
                contentAlignment = Alignment.Center,
            ) {
                CircularProgressIndicator(color = Color.White)
            }
        }

        if (state.value is AlertsState.Error) {
            Box(
                modifier =
                    Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.3f)),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = "Erro: ${(state.value as AlertsState.Error).message}",
                    color = Color.White,
                )
            }
        }
    }
}

@Composable
private fun AlertList(
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
private fun AlertItem(
    alertUi: AlertUi,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    Card(
        modifier =
            modifier
                .fillMaxWidth()
                .shadow(2.dp, RoundedCornerShape(12.dp))
                .clickable {
                    val intent =
                        Intent(context, AlertDetailComposableActivity::class.java)
                    intent.putExtra("alert", alertUi as Serializable)
                    context.startActivity(intent)
                },
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
                    text = alertUi.description,
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
                AlertLevelTag(level = alertUi.alertLevel)
                AlertStatusTag(status = alertUi.status)
            }
        }
    }
}

@Preview
@Composable
private fun AlertItemPreview() {
    AlertItem(
        alertUi =
            AlertUi(
                location = "Rua Mauro Ramos, 500 - Florianópolis",
                latitude = -26.00,
                longitude = 40.00,
                dateTime = "23/10/2025 14:04",
                alertLevel = "Crítico",
                status = "ativo",
                description = "Dispositivo 1p2",
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
            latitude = -26.00,
            longitude = 40.00,
            dateTime = "23/10/2025 14:04",
            alertLevel = "Crítico",
            status = "Ativo",
            description = "Dispositivo 1p2",
        ),
        AlertUi(
            location = "Rua das Flores, 50 - Curitiba",
            latitude = -26.00,
            longitude = 40.00,
            dateTime = "23/10/2025 10:30",
            alertLevel = "Alto",
            status = "Pendente",
            description = "Dispositivo 1p2",
        ),
        AlertUi(
            location = "Av. Paulista, 1000 - São Paulo",
            latitude = -26.00,
            longitude = 40.00,
            dateTime = "23/10/2025 12:10",
            alertLevel = "Médio",
            status = "Resolvido",
            description = "Dispositivo 1p2",
        ),
        AlertUi(
            location = "Rua das Flores, 50 - Curitiba",
            latitude = -26.00,
            longitude = 40.00,
            dateTime = "23/10/2025 10:30",
            alertLevel = "Baixo",
            status = "Cancelado",
            description = "Dispositivo 1p2",
        ),
    )
