package com.example.powercats.ui.activities

import AlertsViewModel
import AlertsViewModel.AlertsState
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.powercats.ui.components.AlertLevelTag
import com.example.powercats.ui.components.AlertLocationMap
import com.example.powercats.ui.components.AlertStatusTag
import com.example.powercats.ui.components.ButtonComponent
import com.example.powercats.ui.components.ResolveAlertBottomSheet
import com.example.powercats.ui.components.TopBar
import com.example.powercats.ui.model.AlertUi
import com.example.powercats.ui.model.EAlertStatus
import com.example.powercats.ui.theme.PowerCATSTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

class AlertDetailComposableActivity : ComponentActivity() {
    private val viewModel: AlertsViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val alert =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                intent.getSerializableExtra("alert", AlertUi::class.java)
            } else {
                @Suppress("DEPRECATION")
                intent.getSerializableExtra("alert") as? AlertUi
            }

        if (alert == null) {
            finish()
            return
        }

        setContent {
            PowerCATSTheme {
                Scaffold(
                    topBar = { TopBar() },
                    modifier = Modifier.fillMaxSize(),
                ) { innerPadding ->
                    AlertDetailScreen(
                        innerPadding = innerPadding,
                        viewModel = viewModel,
                        alert = alert,
                        onClickClose = { finish() },
                    )
                }
            }
        }
    }
}

@Composable
private fun AlertDetailScreen(
    innerPadding: PaddingValues,
    viewModel: AlertsViewModel,
    alert: AlertUi,
    onClickClose: () -> Unit = {},
) {
    val state by viewModel.state.collectAsState()

    val displayedAlert =
        when (val currentState = state) {
            is AlertsState.Success -> currentState.alerts.find { it.id == alert.id } ?: alert
            else -> alert
        }

    AlertDetailScreen(
        modifier = Modifier.padding(innerPadding),
        alertUi = displayedAlert,
        onResolveAlert = { viewModel.updateAlertStatus(displayedAlert, EAlertStatus.FULFILLED) },
        onCancelAlert = { viewModel.updateAlertStatus(displayedAlert, EAlertStatus.CANCELLED) },
        state = state,
        onClickClose = onClickClose,
    )
}

@Composable
private fun AlertDetailScreen(
    modifier: Modifier = Modifier,
    alertUi: AlertUi,
    onResolveAlert: () -> Unit,
    onCancelAlert: () -> Unit,
    state: AlertsState,
    onClickClose: () -> Unit = {},
) {
    var showBottomSheet by remember { mutableStateOf(false) }

    Column(
        modifier =
            modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 16.dp),
    ) {
        AlertDetailHeader(level = alertUi.alertLevel)
        Spacer(modifier = Modifier.height(24.dp))
        AlertDetails(alertUi = alertUi)
        Spacer(modifier = Modifier.height(32.dp))
        AlertLocationMap(
            latitude = alertUi.latitude,
            longitude = alertUi.longitude,
        )
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            ButtonComponent(text = "Voltar", isPrimaryButton = false, onClick = {
                onClickClose()
            })
            ButtonComponent(text = "Resolver", onClick = {
                showBottomSheet = true
            })
        }
    }
    if (showBottomSheet) {
        ResolveAlertBottomSheet(
            visible = showBottomSheet,
            onDismissRequest = { showBottomSheet = false },
            onConfirmation = {
                onResolveAlert()
                showBottomSheet = false
            },
            sheetTitle = "Resolver alerta?",
            sheetText = "Tem certeza que deseja resolver este alerta?",
            onCancelAlert = {
                onCancelAlert()
                showBottomSheet = false
            },
            state = state,
        )
    }
}

@Composable
private fun AlertDetailHeader(
    modifier: Modifier = Modifier,
    level: String,
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(
                text = "Detalhes do alerta",
                style =
                    MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                    ),
            )
            AlertLevelTag(level = level)
        }
        HorizontalDivider(
            thickness = 1.dp,
            modifier =
                Modifier
                    .padding(top = 8.dp)
                    .fillMaxWidth(),
        )
    }
}

@Composable
private fun AlertDetails(
    modifier: Modifier = Modifier,
    alertUi: AlertUi,
) {
    Column(modifier = modifier) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(
                text = alertUi.description,
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold),
            )
            AlertStatusTag(status = alertUi.status)
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Emitido em ${alertUi.dateTime}",
            style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onSurfaceVariant),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun AlertDetailScreenPreview() {
    PowerCATSTheme {
        Scaffold(
            topBar = { TopBar() },
            modifier = Modifier.fillMaxSize(),
        ) { innerPadding ->
            AlertDetailScreen(
                modifier = Modifier.padding(innerPadding),
                alertUi =
                    AlertUi(
                        location = "Campus IFSC",
                        longitude = -26.129,
                        latitude = -46.400,
                        dateTime = "10:30 28/10/2025",
                        alertLevel = "Alto",
                        status = "Pendente",
                        description = "Dispositivo P21 - IFSC",
                        id = 0,
                    ),
                onResolveAlert = { },
                onCancelAlert = { },
                state = AlertsState.Loading,
            )
        }
    }
}
