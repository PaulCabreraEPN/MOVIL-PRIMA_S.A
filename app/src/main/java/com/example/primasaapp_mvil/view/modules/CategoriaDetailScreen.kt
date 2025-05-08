package com.example.primasaapp_mvil.view.modules

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.primasaapp_mvil.viewmodel.ProductViewModel
import androidx.hilt.navigation.compose.hiltViewModel


@Composable
fun CategoriaDetailScreen(
    nombreCategoria: String,
    viewModel: ProductViewModel = hiltViewModel()
) {
    val productos by viewModel.products.collectAsState()
    val productosPorCategoria = clasificarProductos(productos)
    val productosFiltrados = productosPorCategoria[nombreCategoria] ?: emptyList()

    Scaffold(
        containerColor = Color(0xFFF5F5F5) // fondo gris claro
    ) { padding ->
        LazyColumn(
            contentPadding = padding,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            item {
                Text(
                    text = nombreCategoria,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF005BBB)
                    ),
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }

            items(productosFiltrados) { producto ->
                ProductoCard(
                    producto = producto,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}






