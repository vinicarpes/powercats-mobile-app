package com.example.powercats.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.powercats.R

data class BottomNavItem(
    val label: String,
    val icon: Int,
    val route: String,
)

@Composable
fun BottomNavigationBar(
    modifier: Modifier = Modifier,
    items: List<BottomNavItem> = defaultBottomNavItems(),
    currentRoute: String = items.first().route,
    onItemSelected: (BottomNavItem) -> Unit = {},
) {
    NavigationBar(
        modifier =
            modifier
                .fillMaxWidth()
                .height(70.dp)
                .padding(horizontal = 4.dp),
        containerColor = Color(0xFFFF8F00), // Laranja forte
        tonalElevation = 3.dp,
    ) {
        items.forEach { item ->
            val isSelected = item.route == currentRoute
            NavigationBarItem(
                selected = isSelected,
                onClick = { onItemSelected(item) },
                icon = {
                    Icon(
                        painter = painterResource(id = item.icon),
                        contentDescription = item.label,
                        tint = if (isSelected) Color.White else Color.White.copy(alpha = 0.7f),
                    )
                },
                label = {
                    Text(
                        text = item.label,
                        color = if (isSelected) Color.White else Color.White.copy(alpha = 0.8f),
                        fontSize = 12.sp,
                    )
                },
                alwaysShowLabel = true,
                colors =
                    NavigationBarItemDefaults.colors(
                        indicatorColor = Color.White.copy(alpha = 0.1f),
                    ),
            )
        }
    }
}

fun defaultBottomNavItems() =
    listOf(
        BottomNavItem("Início", R.drawable.ic_home, "home"),
    )

@Preview(showBackground = true)
@Composable
fun BottomNavigationBarPreview() {
    BottomNavigationBar()
}
