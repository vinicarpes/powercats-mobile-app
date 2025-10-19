package com.example.powercats.ui.activities

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.powercats.ui.components.TopBar
import com.example.powercats.ui.theme.PowerCATSTheme

class HomeComposeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PowerCATSTheme {
                Scaffold(
                    topBar = { TopBar() },
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    HomeScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {

}

@Preview
@Composable
private fun HomeScreenPreview() {
    PowerCATSTheme {
        Scaffold(
            topBar = { TopBar() },
            modifier = Modifier.fillMaxSize()
        ) { innerPadding ->
            HomeScreen(modifier = Modifier.padding(innerPadding))
        }
    }
}