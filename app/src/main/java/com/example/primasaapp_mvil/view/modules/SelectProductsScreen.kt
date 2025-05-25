package com.example.primasaapp_mvil.view.modules

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
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
    val productos by productViewModel.products.collectAsState()
    val productosPorCategoria = clasificarProductos(productos)
    val productosFiltrados = productosPorCategoria[nombreCategoria] ?: emptyList()
    val productosSeleccionados = orderViewModel.productosSeleccionados.collectAsState().value
    val total by orderViewModel.total.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF6F6F6)) // Fondo aplicado
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
            modifier = Modifier.padding(start = 16.dp, bottom = 16.dp),
            color = Color(0xFF313131)
        )

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 8.dp) // Padding opcional para mejor presentación
        ) {
            items(productosFiltrados) { producto ->
                ProductoCardToSend(producto = producto, pedidoViewModel = orderViewModel)
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
