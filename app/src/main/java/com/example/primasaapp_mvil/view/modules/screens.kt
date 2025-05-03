package com.example.primasaapp_mvil.view.modules

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.primasaapp_mvil.model.Client
import com.example.primasaapp_mvil.model.Product
import com.example.primasaapp_mvil.viewmodel.ClientViewModel
import com.example.primasaapp_mvil.viewmodel.ProductViewModel
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.primasaapp_mvil.data.dataStore.DataStoreManager
import com.example.primasaapp_mvil.model.Order
import com.example.primasaapp_mvil.model.ProductSelected
import com.example.primasaapp_mvil.viewmodel.OrderViewModel
import com.example.primasaapp_mvil.viewmodel.UserViewModel

//DASHBOARD
@Composable
fun HomeScreen(
    orderViewModel: OrderViewModel = hiltViewModel(),
    userViewModel: UserViewModel = hiltViewModel()
) {
    val username by userViewModel.username.collectAsState()
    val name by userViewModel.userName.collectAsState()
    val salesCity by userViewModel.sales_CITY.collectAsState()
    val orders by orderViewModel.orders.collectAsState()

    val totalOrders = orders.size
    val totalGeneral = orders.sumOf { it.totalWithTax }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Mi Perfil
        Text(
            text = "Mi Perfil",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
            color = Color(0xFF005BBB)
        )
        Spacer(modifier = Modifier.height(8.dp))
        PerfilCard(username = username, name = name, city = salesCity)

        Spacer(modifier = Modifier.height(16.dp))

        // Mis Ventas
        Text(
            text = "Mis ventas",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
            color = Color(0xFF005BBB)
        )
        Spacer(modifier = Modifier.height(8.dp))
        VentasCard(totalPedidos = totalOrders, totalVentas = totalGeneral)

        Spacer(modifier = Modifier.height(24.dp))

        // Atajos
        Text(
            text = "¿Qué deseas hacer?",
            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.SemiBold),
            color = Color(0xFF005BBB)
        )
        Spacer(modifier = Modifier.height(8.dp))
        OpcionesGrid()
    }
}

@Composable
fun PerfilCard(username: String, name: String, city: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = username,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Activo", color = Color(0xFF008000))
                    Spacer(modifier = Modifier.width(4.dp))
                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .background(Color.Green, CircleShape)
                    )
                }
            }
            Text(text = name, fontSize = 14.sp)
            Text(text = "CI: XXXXX4321", fontSize = 13.sp)
            Text(
                text = "Ciudad de ventas: $city",
                fontSize = 13.sp,
                color = Color(0xFF666666)
            )
        }
    }
}

@Composable
fun VentasCard(totalPedidos: Int, totalVentas: Double) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "$totalPedidos", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                Text(text = "Pedidos\ngenerados", textAlign = TextAlign.Center, fontSize = 12.sp)
            }
            Divider(
                color = Color.LightGray,
                modifier = Modifier
                    .width(1.dp)
                    .height(40.dp)
            )
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "$${String.format("%.2f", totalVentas)}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
                Text(text = "Vendidos", fontSize = 12.sp)
            }
        }
    }
}

@Composable
fun OpcionesGrid() {
    val opciones = listOf(
        "Tomar Pedido" to Icons.Default.Edit,
        "Registrar Cliente" to Icons.Default.PersonAdd,
        "Consular Inventario" to Icons.Default.List,
        "Modificar Pedido" to Icons.Default.EditNote,
        "Productos Destacados" to Icons.Default.Star,
        "Mis Proformas" to Icons.Default.Receipt
    )

    Column(modifier = Modifier.padding(horizontal = 8.dp)) {
        opciones.chunked(3).forEach { fila ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                fila.forEach { (label, icon) ->
                    OpcionShortcut(label = label, icon = icon, modifier = Modifier.weight(1f))
                }
                repeat(3 - fila.size) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun OpcionShortcut(label: String, icon: ImageVector, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .aspectRatio(1f)
            .clickable { /* TODO: Navegar a pantalla */ },
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                modifier = Modifier.size(28.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = label,
                fontSize = 13.sp,
                textAlign = TextAlign.Center,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}



// Clasificación por categorías
fun clasificarProductos(productos: List<Product>): Map<String, List<Product>> {
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

// Vista principal que usa el ViewModel
@Composable
fun InventarioView(
    viewModel: ProductViewModel = hiltViewModel(),
    onVerMasClick: (String) -> Unit
) {
    val productos by viewModel.products.collectAsState()

    InventarioScreen(
        productos = productos,
        onVerMasClick = onVerMasClick
    )
}

// Vista con la lista de productos clasificados
@Composable
fun InventarioScreen(
    productos: List<Product>,
    onVerMasClick: (String) -> Unit
) {
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
    productos: List<Product>,
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
fun ProductoCard(producto: Product) {
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


//CLIENTES
@Composable
fun ClientsScreen(viewModel: ClientViewModel = hiltViewModel()) {
    val client by viewModel.clients.collectAsState()

    LazyColumn (
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ){
        items(client) {
            client -> ClientCard(client)
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun  ClientCard(client: Client){
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(4.dp),
        modifier = Modifier.fillMaxSize()
    ){
        Column(modifier = Modifier
            .padding(16.dp)){
            Text(text = client.Name, style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "RUC ${client.Ruc}")
            Text(text = "Teléfono ${client.telephone}")
            Text(text = "Email ${client.email}")
            Text(text = "Crédito ${client.credit}")
            Text(text = "Dirección ${client.Address}")
            Text(
                text = client.state,
                modifier = Modifier.align(Alignment.End),
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}


@Composable
fun OrdersScreen(orderViewModel: OrderViewModel = hiltViewModel()) {
    val orders by orderViewModel.orders.collectAsState()
    LazyColumn {
        items(orders) { order ->
            OrderCard(order = order)
        }
    }
}

@Composable
fun OrderCard(order: Order) {
    var expanded by remember { mutableStateOf(false) }
    val customer = order.customer
    val products = order.products
    val firstProduct = products.firstOrNull()

    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Header
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(
                    text = "Orden #${order.hashCode().toString().takeLast(5)}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Text(
                    text = order.status,
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }

            Text(text = "Cliente: ${customer.Name}", fontSize = 14.sp)
            Text(
                text = "Total: $${order.totalWithTax}",
                color = Color(0xFF007BFF), // azul
                fontWeight = FontWeight.SemiBold,
                fontSize = 15.sp
            )

            Spacer(modifier = Modifier.height(8.dp))
            Text("Productos:", fontWeight = FontWeight.SemiBold)

            // Producto inicial
            if (firstProduct != null) {
                ProductItem(firstProduct)
                Divider()
            }

            // Productos expandidos
            AnimatedVisibility(visible = expanded) {
                Column {
                    products.drop(1).forEach { product ->
                        ProductItem(product)
                        Divider()
                    }
                }
            }

            // Flecha expand/collapse
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { expanded = !expanded }
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = if (expanded) "Contraer" else "Expandir"
                )
            }
        }
    }
}

@Composable
fun ProductItem(product: ProductSelected) {
    val details = product.productDetails
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(text = "${details.product_name} ${details.measure}", fontSize = 14.sp)
            Text(text = "$${details.price}", fontSize = 13.sp, color = Color.Gray)
        }
        Text(text = "${product.quantity}", fontSize = 14.sp)
    }
}


