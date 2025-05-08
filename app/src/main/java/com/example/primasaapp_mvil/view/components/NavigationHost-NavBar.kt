package com.example.primasaapp_mvil.view.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.primasaapp_mvil.view.modules.*

@Composable
fun NavigationHost(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        startDestination = "home",
        modifier = modifier
    ) {
        composable("home") { HomeScreen() }

        composable("inventory") {
            InventarioView(onCategoriaClick = { categoria ->
                navController.navigate("categoria/$categoria")
            })
        }

        composable("clients") { ClientsScreen() }

        composable("orders") { OrdersScreen() }

        composable("categoria/{nombreCategoria}") { backStackEntry ->
            val nombreCategoria = backStackEntry.arguments?.getString("nombreCategoria") ?: ""
            CategoriaDetailScreen(nombreCategoria = nombreCategoria)
        }
    }
}


