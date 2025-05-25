package com.example.primasaapp_mvil.view.components

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TextButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.primasaapp_mvil.model.Product


@Composable
fun BarraInferiorPedido(
    total: Float,
    productosSeleccionados: Map<Int, Int>, // id -> cantidad
    productos: List<Product>, // lista completa de productos para mostrar nombres/precios
    onFinalizarClick: () -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }

    Column {
        // Panel desplegable de productos seleccionados
        AnimatedVisibility(visible = expanded) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFF5F5F5))
                    .padding(8.dp)
            ) {
                Text(
                    text = "Productos seleccionados",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                productosSeleccionados.forEach { (id, cantidad) ->
                    val producto = productos.find { it.id == id }
                    producto?.let {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(it.product_name)
                            Text("x$cantidad - $${"%.2f".format(it.price * cantidad)}")
                        }
                    }
                }
            }
        }

        // Barra inferior
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp),
            color = Color(0xFF1A4C9C),
            shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
            shadowElevation = 4.dp
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = { expanded = !expanded }) {
                        Icon(
                            imageVector = if (expanded) Icons.Filled.KeyboardArrowDown else Icons.Filled.KeyboardArrowUp,
                            contentDescription = "Expandir o colapsar",
                            tint = Color.White
                        )
                    }

                    Text(
                        text = "Total: $${"%.2f".format(total)}",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                }

                TextButton(onClick = onFinalizarClick) {
                    Text("Finalizar", color = Color.White, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

