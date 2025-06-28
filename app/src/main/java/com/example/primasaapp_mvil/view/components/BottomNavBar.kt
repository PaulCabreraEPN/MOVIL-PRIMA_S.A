package com.example.primasaapp_mvil.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.foundation.clickable
import androidx.compose.ui.res.painterResource
import com.example.primasaapp_mvil.R
import androidx.compose.material3.Surface
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController


@Composable
fun BottomNavBar(navController: NavController, selectedRoute: String) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .shadow(8.dp),
        color = Color.White,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            BottomNavItem(
                icon = R.drawable.home_ic_b,
                label = "Inicio",
                isSelected = selectedRoute == "home"
            ) {
                navController.navigate("home")
            }
            BottomNavItem(
                icon = R.drawable.orders_ic_b,
                label = "Pedidos",
                isSelected = selectedRoute == "inventory"
            ) {
                navController.navigate("inventory")
            }
            BottomNavItem(
                icon = R.drawable.clients_ic_b,
                label = "Clientes",
                isSelected = selectedRoute == "clients"
            ) {
                navController.navigate("clients")
            }
            BottomNavItem(
                icon = R.drawable.document_ic_b,
                label = "Documentos",
                isSelected = selectedRoute == "orders"
            ) {
                navController.navigate("orders")
            }
        }
    }
}

@Composable
fun BottomNavItem(
    icon: Int,
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val backgroundColor = if (isSelected) Color(0xFF005BBB).copy(alpha = 0.1f) else Color.Transparent
    val iconTint = if (isSelected) Color(0xFF005BBB) else Color.Black

    Column(
        modifier = Modifier
            .clip(MaterialTheme.shapes.medium)
            .background(backgroundColor)
            .clickable(onClick = onClick)
            .padding(6.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = label,
            tint = iconTint,
            modifier = Modifier.size(28.dp)
        )
    }
}


