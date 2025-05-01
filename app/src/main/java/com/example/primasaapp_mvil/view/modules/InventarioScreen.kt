/*
package com.example.primasaapp_mvil.view.modules

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.inventario.model.Producto

data class Producto(
    val id: Int,
    val product_name: String,
    val measure: String,
    val price: Double,
    val stock: Int,
    val imgUrl: String
)

fun clasificarProductos(productos: List<Producto>): Map<String, List<Producto>> {
    val categorias = mapOf(
        "Rodillos" to listOf("ROD", "MINI ROD"),
        "Brochas" to listOf("BROCHA"),
        "Espatulas" to listOf("ESP."),
        "Llanas" to listOf("LLANA"),
        "Accesorios" to listOf("BANDEJA", "CUBETA")
    )

    return categorias.mapValues { (_, palabrasClave) ->
        productos.filter { producto ->
            palabrasClave.any { keyword ->
                producto.product_name.contains(keyword, ignoreCase = true)
            }
        }
    }.filterValues { it.isNotEmpty() }
}

@Composable
fun InventarioScreen(productos: List<Producto>, onVerMasClick: (String) -> Unit) {
    val productosPorCategoria = clasificarProductos(productos)

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 16.dp)
    ) {
        productosPorCategoria.forEach { (categoria, productos) ->
            item {
                CategoriaConProductos(
                    categoria = categoria,
                    productos = productos,
                    onVerMasClick = onVerMasClick
                )
            }
        }
    }
}

@Composable
fun CategoriaConProductos(
    categoria: String,
    productos: List<Producto>,
    onVerMasClick: (String) -> Unit
) {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = categoria, style = MaterialTheme.typography.titleMedium)
            Text(
                text = "Ver más",
                color = Color.Blue,
                modifier = Modifier.clickable { onVerMasClick(categoria) }
            )
        }

        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(productos) { producto ->
                ProductoCard(producto)
            }
        }
    }
}

@Composable
fun ProductoCard(producto: Producto) {
    Card(
        modifier = Modifier
            .width(160.dp)
            .height(220.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = producto.imgUrl,
                contentDescription = producto.product_name,
                modifier = Modifier
                    .height(80.dp)
                    .fillMaxWidth(),
                contentScale = ContentScale.Fit
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = producto.product_name,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                fontSize = 12.sp
            )
            Text(text = "C.Dis: ${producto.stock}")
            Text(text = "$${producto.price}", fontWeight = FontWeight.Bold)
        }
    }
}
*/