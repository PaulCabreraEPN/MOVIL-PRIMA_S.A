package com.example.primasaapp_mvil.view.modules

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.primasaapp_mvil.viewmodel.ProductViewModel
import androidx.hilt.navigation.compose.hiltViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoriaDetailScreen(
    nombreCategoria: String,
    viewModel: ProductViewModel = hiltViewModel()
) {
    val productos by viewModel.products.collectAsState()

    val productosPorCategoria = clasificarProductos(productos)
    val productosFiltrados = productosPorCategoria[nombreCategoria] ?: emptyList()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = nombreCategoria) }
            )
        }
    ) { padding ->
        LazyColumn(
            contentPadding = padding,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            items(productosFiltrados) { producto ->
                ProductoCard(producto = producto)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}
