package com.example.primasaapp_mvil.view.modules

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.primasaapp_mvil.model.Product
import com.example.primasaapp_mvil.view.components.BarraInferiorPedido
import com.example.primasaapp_mvil.viewmodel.OrderViewModel
import com.example.primasaapp_mvil.viewmodel.ProductViewModel


@Composable
fun CategoriaDetailScreenToSend(
    nombreCategoria: String,
    productViewModel: ProductViewModel = hiltViewModel(),
    orderViewModel: OrderViewModel,
    onBack: () -> Unit
) {
    val isLoading by productViewModel.isLoading.collectAsState()
    val productos by productViewModel.products.collectAsState()
    val productosPorCategoria = clasificarProductos(productos)

    var searchQuery by remember { mutableStateOf("") }

    // Filtrado con búsqueda
    val productosFiltrados = productosPorCategoria[nombreCategoria]
        ?.filter {
            it.product_name.contains(searchQuery, ignoreCase = true) ||
                    it.reference.contains(searchQuery, ignoreCase = true)
        } ?: emptyList()

    val productosSeleccionados = orderViewModel.productosSeleccionados.collectAsState().value
    val total by orderViewModel.total.collectAsState()

    if (isLoading) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = Color(0xFF005BBB))
        }
    }else{
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF6F6F6))
        ) {
            Text(
                text = nombreCategoria,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF005BBB)
                ),
                modifier = Modifier.padding(start = 16.dp, top = 16.dp)
            )

            Text(
                text = "Seleccione productos",
                fontWeight = FontWeight.Light,
                modifier = Modifier.padding(start = 16.dp, bottom = 8.dp),
                color = Color(0xFF313131)
            )

            // Buscador
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = { Text("Buscar producto") },
                trailingIcon = {
                    Icon(Icons.Default.Search, contentDescription = "Buscar")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 4.dp),
                shape = RoundedCornerShape(50),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF2563EB),
                    unfocusedBorderColor = Color(0xFF2563EB),
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    cursorColor = Color(0xFF2563EB)
                )
            )

            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp)
            ) {
                items(productosFiltrados) { producto ->
                    ProductoCardToSend(producto = producto, orderViewModel = orderViewModel)
                }
            }

            BarraInferiorPedido(
                total = total.toFloat(),
                productosSeleccionados = productosSeleccionados,
                productos = productos,
                onFinalizarClick = {
                    onBack()
                    val seleccion = orderViewModel.obtenerProductosParaEnviar(productos)
                    Log.d("Pedido", seleccion.toString())
                }
            )
        }
    }

}

