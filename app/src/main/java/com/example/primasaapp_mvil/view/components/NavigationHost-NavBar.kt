package com.example.primasaapp_mvil.view.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.primasaapp_mvil.view.modules.*
import com.example.primasaapp_mvil.viewmodel.ClientViewModel
import com.example.primasaapp_mvil.viewmodel.OrderViewModel
import com.example.primasaapp_mvil.viewmodel.ProductViewModel
import com.example.primasaapp_mvil.viewmodel.UserViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavigationHost(navController: NavHostController, modifier: Modifier = Modifier) {
    val clientViewModel: ClientViewModel = viewModel()
    val orderViewModel: OrderViewModel = viewModel()
    val productViewModel : ProductViewModel = viewModel ()
    val userViewModel: UserViewModel = hiltViewModel()


    NavHost(
        navController = navController,
        startDestination = "home",
        modifier = modifier
    ) {
        composable("home") { HomeScreen(
            navController = navController,
            orderViewModel = orderViewModel) }

        composable("inventory") {
            InventarioView(onCategoriaClick = { categoria ->
                navController.navigate("categoria/$categoria")
            })
        }

        composable("clients") {
            ClientsScreen(navController = navController)
        }

        composable("orders") {
            OrdersScreen(navController = navController,  orderViewModel = orderViewModel)
        }

        composable("profile") {
            ProfileScreen(navController = navController, userViewModel = userViewModel)
        }

        composable("topProducts") {
            TopProductosMasVendidosScreen()
        }

        composable("categoria/{nombreCategoria}") { backStackEntry ->
            val nombreCategoria = backStackEntry.arguments?.getString("nombreCategoria") ?: ""
            CategoriaDetailScreen(nombreCategoria = nombreCategoria)
        }

        composable("registerClient") {
            RegisterClientScreen(
                onSuccess = {
                    navController.navigate("clients") {
                        popUpTo("registerClient") { inclusive = true }
                    }
                }
            )
        }

        composable("clientDetail/{clientRuc}") { backStackEntry ->
            val clientRuc = backStackEntry.arguments?.getString("clientRuc") ?: return@composable
            ClientDetailScreen(
                clientRuc = clientRuc,
                clientViewModel = clientViewModel,
                onBack = { navController.popBackStack() }
            )
        }

        composable("orderDetail/{orderId}") { backStackEntry ->
            val orderId = backStackEntry.arguments?.getString("orderId") ?: return@composable
            OrderDetailScreen(
                orderId = orderId,
                orderViewModel = orderViewModel,
                onBack = { navController.popBackStack() },
                navController = navController
            )
        }

        composable("orderEdit/{orderId}") { backStackEntry ->
            val orderId = backStackEntry.arguments?.getString("orderId") ?: return@composable
            EditOrder(
                orderId = orderId,
                orderViewModel = orderViewModel,
                onBack = { navController.popBackStack() },
                navController = navController
            )
        }

        composable ( "resgisterOrder" ) {
            RegisterOrderScreen(
                orderId = "",
                navController = navController,
                orderViewModel = orderViewModel,
                onBack = { navController.popBackStack() }
            )
        }

        composable("productSelecter") {
            ProductSelectorView (
                onCategoriaClick = { categoria ->
                    navController.navigate("categoriatoSend/$categoria")
                }, orderViewModel = orderViewModel,
                onBack = { navController.popBackStack() }
            )
        }

        composable("categoriatoSend/{nombreCategoria}") { backStackEntry ->
            val nombreCategoria = backStackEntry.arguments?.getString("nombreCategoria") ?: ""
            CategoriaDetailScreenToSend(
                nombreCategoria = nombreCategoria,
                orderViewModel = orderViewModel,
                onBack = { navController.navigate("resgisterOrder") }
            )
        }

    }
}


