package com.example.powercats.ui.components

import android.media.Image
import android.widget.ImageView
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.R
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar() {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    CenterAlignedTopAppBar(

        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
        ,
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Color(0xFFFF941A),
            titleContentColor = Color.White
        ),
        title = { TopBarTitle() },
        scrollBehavior = scrollBehavior
    )
}

@Composable
private fun TopBarTitle(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.padding(end = 12.dp)
        ) {
            Text(
                text = "Olá, Vinicius",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontSize = 22.sp,
                    color = Color.White
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Acompanhe as notificações dos dispositivos",
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontSize = 14.sp,
                    color = Color.White.copy(alpha = 0.9f)
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

        Icon(
            painter = painterResource(id = android.R.drawable.ic_menu_camera),
            contentDescription = "Logo PowerCats",
            tint = Color.White,
            modifier = Modifier
                .size(60.dp)
                .padding(end = 12.dp)
        )
    }
}



@Preview
@Composable
private fun TopBarPreview() {
    Scaffold {
        TopBar()
    }
}