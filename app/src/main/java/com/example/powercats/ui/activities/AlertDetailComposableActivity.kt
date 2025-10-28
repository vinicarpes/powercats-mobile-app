package com.example.powercats.ui.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.powercats.ui.components.AlertLevelTag
import com.example.powercats.ui.components.AlertLocationMap
import com.example.powercats.ui.components.AlertStatusTag
import com.example.powercats.ui.components.TopBar
import com.example.powercats.ui.model.AlertUi
import com.example.powercats.ui.theme.PowerCATSTheme
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.rememberCameraPositionState

class AlertDetailComposableActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val alertUi = intent.getSerializableExtra("alert") as AlertUi
        setContent {
            PowerCATSTheme {
                Scaffold(
                    topBar = { TopBar() },
                    modifier = Modifier.fillMaxSize(),
                ) { innerPadding ->
                    AlertDetailScreen(
                        modifier = Modifier.padding(innerPadding),
                        alertUi = alertUi,
                    )
                }
            }
        }
    }
}

@Composable
private fun AlertDetailScreen(
    modifier: Modifier = Modifier,
    alertUi: AlertUi,
) {
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
        Divider(
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

//@Composable
//fun AlertLocationMap(
//    latitude: String,
//    longitude: String,
//) {
//    val lat = latitude.toDoubleOrNull() ?: 27.5969
//    val lon = longitude.toDoubleOrNull() ?: 48.5494
//    val positionState =
//        rememberCameraPositionState {
//            position = CameraPosition.fromLatLngZoom(LatLng(lat, lon), 15f)
//        }
//
//    val properties =
//        MapProperties(
//            mapType = MapType.NORMAL,
//            isMyLocationEnabled = true,
//        )
//
//    val uiSettings =
//        MapUiSettings(
//            zoomControlsEnabled = true,
//            myLocationButtonEnabled = false,
//        )
//
//    GoogleMap(
//        modifier =
//            Modifier
//                .fillMaxWidth()
//                .height(200.dp)
//                .clip(RoundedCornerShape(12.dp)),
//        cameraPositionState = positionState,
//        properties = properties,
//        uiSettings = uiSettings,
//    )
//}

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
                        longitude = "-26.129",
                        latitude = "-46.400",
                        dateTime = "10:30 28/10/2025",
                        alertLevel = "Alto",
                        status = "Pendente",
                        description = "Dispositivo P21 - IFSC",
                    ),
            )
        }
    }
}
