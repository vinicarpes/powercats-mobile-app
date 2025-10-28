package com.example.powercats.ui.components

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.example.powercats.ui.theme.md_theme_dark_primary
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun AlertLocationMap(
    latitude: Double,
    longitude: Double,
) {
    val context = LocalContext.current
    val lat = latitude
    val lon = longitude
    val alertLatLng = LatLng(lat, lon)

    var hasLocationPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION,
            ) == PackageManager.PERMISSION_GRANTED,
        )
    }

    val launcher =
        rememberLauncherForActivityResult(
            ActivityResultContracts.RequestPermission(),
        ) { granted ->
            hasLocationPermission = granted
        }

    LaunchedEffect(key1 = Unit) {
        if (!hasLocationPermission) {
            launcher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    val positionState =
        rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(alertLatLng, 14f)
        }

    val properties =
        MapProperties(
            mapType = MapType.NORMAL,
            isMyLocationEnabled = hasLocationPermission,
            minZoomPreference = 1f,
            maxZoomPreference = 20f,
        )
    val uiSettings =
        MapUiSettings(
            zoomControlsEnabled = true,
            compassEnabled = true,
            scrollGesturesEnabled = true,
            zoomGesturesEnabled = true,
            tiltGesturesEnabled = true,
            myLocationButtonEnabled = hasLocationPermission,
        )

    Card(
        modifier =
            Modifier
                .fillMaxWidth()
                .height(400.dp),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, md_theme_dark_primary),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
    ) {
        GoogleMap(
            modifier =
                Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(12.dp)),
            cameraPositionState = positionState,
            properties = properties,
            uiSettings = uiSettings,
        ) {
            Marker(
                state = MarkerState(position = alertLatLng),
                title = "Alerta",
                snippet = "Local do alerta",
            )
        }
    }
}
