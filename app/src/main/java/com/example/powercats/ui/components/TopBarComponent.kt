package com.example.powercats.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.powercats.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    userName: String = "Vinicius",
    modifier: Modifier = Modifier,
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    CenterAlignedTopAppBar(
        modifier =
            modifier
                .fillMaxWidth()
                .height(120.dp)
                .background(
                    brush =
                        Brush.verticalGradient(
                            colors =
                                listOf(
                                    Color(0xFFFFA726),
                                    Color(0xFFFF8F00),
                                ),
                        ),
                ),
        colors =
            TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = Color.Transparent,
                titleContentColor = Color.White,
            ),
        title = {
            TopBarContent(userName = userName)
        },
        scrollBehavior = scrollBehavior,
    )
}

@Composable
private fun TopBarContent(userName: String) {
    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.weight(1f),
        ) {
            Text(
                text = "Olá, $userName 👋",
                style =
                    MaterialTheme.typography.titleLarge.copy(
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                    ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "Acompanhe as notificações dos dispositivos",
                style =
                    MaterialTheme.typography.bodyMedium.copy(
                        fontSize = 14.sp,
                        color = Color.White.copy(alpha = 0.9f),
                    ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }

        Icon(
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = "Logo PowerCATS",
            tint = Color.White,
            modifier =
                Modifier
                    .size(64.dp)
                    .padding(start = 12.dp),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun TopBarPreview() {
    Scaffold { innerpaddind ->
        val i = innerpaddind
        TopBar()
    }
}
