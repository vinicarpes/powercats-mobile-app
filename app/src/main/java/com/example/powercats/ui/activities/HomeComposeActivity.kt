package com.example.powercats.ui.activities

import android.Manifest
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import com.example.powercats.R.drawable.device_map
import com.example.powercats.R.drawable.notifications
import com.example.powercats.R.drawable.sensors_register
import com.example.powercats.R.drawable.user_register
import com.example.powercats.ui.components.TopBar
import com.example.powercats.ui.navigation.Destination
import com.example.powercats.ui.navigation.navigateTo
import com.example.powercats.ui.theme.PowerCATSTheme

class HomeComposeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PowerCATSTheme {
                Scaffold(
                    topBar = { TopBar() },
                    modifier = Modifier.fillMaxSize(),
                ) { innerPadding ->
                    HomeScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                1,
            )
        }
    }
}

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current

    val rowModifier =
        Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)

    Column(
        modifier =
            modifier
                .padding(horizontal = 30.dp, vertical = 12.dp)
                .fillMaxWidth(),
    ) {
        Row(
            modifier = rowModifier,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            OptionCard(
                text = "Notificações e Alertas",
                painter = painterResource(notifications),
                onClick = { navigateTo(context, Destination.Alerts) },
            )

            OptionCard(
                text = "Cadastro de Sensores",
                painter = painterResource(sensors_register),
                onClick = { navigateTo(context, Destination.Sensors) },
            )
        }

        Row(
            modifier = rowModifier,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            OptionCard(
                text = "Mapa Geolocalização",
                painter = painterResource(device_map),
                onClick = { navigateTo(context, Destination.Map) },
            )

            OptionCard(
                text = "Cadastro de Usuários",
                painter = painterResource(user_register),
                onClick = { navigateTo(context, Destination.Users) },
            )
        }

        Row(
            modifier = rowModifier,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            OptionCard(
                text = "Relatório de Alertas",
                painter = painterResource(sensors_register),
                onClick = { navigateTo(context, Destination.Reports) },
            )
        }
    }
}

@Composable
private fun OptionCard(
    modifier: Modifier = Modifier,
    text: String,
    painter: Painter = painterResource(notifications),
    onClick: () -> Unit = {},
) {
    Column(
        modifier =
            modifier
                .width(150.dp)
                .height(150.dp)
                .border(1.dp, Color(0xFFE1E1E5), shape = RoundedCornerShape(10.dp))
                .clickable(onClick = onClick),
    ) {
        Icon(
            painter = painter,
            contentDescription = "Ícone do card de selecao",
            tint = Color.Unspecified,
            modifier =
                Modifier
                    .padding(start = 16.dp, top = 16.dp, bottom = 16.dp)
                    .size(40.dp),
        )
        Text(
            text = text,
            modifier = Modifier.padding(start = 16.dp),
            fontSize = 16.sp,
        )
    }
}

@Preview
@Composable
private fun HomeScreenPreview() {
    PowerCATSTheme {
        Scaffold(
            topBar = { TopBar() },
            modifier = Modifier.fillMaxSize(),
        ) { innerPadding ->
            HomeScreen(modifier = Modifier.padding(innerPadding))
        }
    }
}
