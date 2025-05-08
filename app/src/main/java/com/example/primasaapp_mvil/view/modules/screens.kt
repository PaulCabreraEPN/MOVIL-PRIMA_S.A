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
import androidx.compose.material3.FloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.primasaapp_mvil.ui.theme.PRIMASAAPPMóvilTheme
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
import androidx.compose.material.icons.filled.Add
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
import com.example.primasaapp_mvil.model.Order
import com.example.primasaapp_mvil.model.ProductSelected
import com.example.primasaapp_mvil.ui.theme.Blue50
import com.example.primasaapp_mvil.viewmodel.OrderViewModel
import com.example.primasaapp_mvil.viewmodel.UserViewModel
import androidx.compose.material.icons.filled.Search
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.ui.draw.clip


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
            .padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Mi Perfil",
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                color = Color(0xFF005BBB)
            )
        )

        Spacer(modifier = Modifier.height(8.dp))
        PerfilCard(username = username, name = name, city = salesCity)

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Mis ventas",
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                color = Color(0xFF005BBB)
            )
        )

        Spacer(modifier = Modifier.height(8.dp))
        VentasCard(totalPedidos = totalOrders, totalVentas = totalGeneral)

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "¿Qué deseas hacer?",
            style = MaterialTheme.typography.titleSmall.copy(
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF005BBB)
            )
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
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
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
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "$totalPedidos", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                Text(
                    text = "Pedidos\ngenerados",
                    textAlign = TextAlign.Center,
                    fontSize = 12.sp
                )
            }

            Divider(
                color = Color.LightGray,
                modifier = Modifier
                    .height(40.dp)
                    .width(1.dp)
            )

            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "$${String.format("%.2f", totalVentas)}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
                Text(
                    text = "Vendidos",
                    textAlign = TextAlign.Center,
                    fontSize = 12.sp
                )
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
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
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


//INVENTARIO
fun clasificarProductos(productos: List<Product>): Map<String, List<Product>> {
    val categorias = mapOf(
        "Rodillos" to listOf("Rodillo"),
        "Brochas" to listOf("Brocha"),
        "Espatulas" to listOf("Espátula"),
        "Llanas" to listOf("Llana"),
    )

    val clasificados = mutableMapOf<String, MutableList<Product>>()
    val yaClasificados = mutableSetOf<Product>()

    for ((categoria, keywords) in categorias) {
        val encontrados = productos.filter { producto ->
            keywords.any { keyword ->
                producto.product_name.contains(keyword, ignoreCase = true)
            }
        }
        clasificados[categoria] = encontrados.toMutableList()
        yaClasificados.addAll(encontrados)
    }

    // Todos los no clasificados van a "Accesorios"
    val noClasificados = productos.filterNot { it in yaClasificados }
    if (noClasificados.isNotEmpty()) {
        clasificados["Accesorios"] = noClasificados.toMutableList()
    }

    return clasificados.filterValues { it.isNotEmpty() }
}


@Composable
fun InventarioView(
    viewModel: ProductViewModel = hiltViewModel(),
    onCategoriaClick: (String) -> Unit
) {
    val productos by viewModel.products.collectAsState()
    val productosPorCategoria = clasificarProductos(productos)

    InventarioCategoriasScreen(
        productosPorCategoria = productosPorCategoria,
        onCategoriaClick = onCategoriaClick
    )
}

@Composable
fun InventarioCategoriasScreen(
    productosPorCategoria: Map<String, List<Product>>,
    onCategoriaClick: (String) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "Inventario",
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                color = Color(0xFF005BBB)
            ),
            modifier = Modifier.padding(16.dp)
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            productosPorCategoria.forEach { (categoria, productos) ->
                val imageUrl = productos.firstOrNull()?.imgUrl.orEmpty()
                item {
                    CategoriaCard(
                        categoria = categoria,
                        imageUrl = imageUrl,
                        onClick = { onCategoriaClick(categoria) }
                    )
                }
            }
        }
    }
}

@Composable
fun CategoriaCard(categoria: String, imageUrl: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = imageUrl,
                contentDescription = categoria,
                modifier = Modifier
                    .height(100.dp)
                    .fillMaxWidth(),
                contentScale = ContentScale.Fit
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = categoria,
                style = MaterialTheme.typography.titleSmall.copy(
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF005BBB)
                ),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun ProductoCard(
    producto: Product,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            // Nombre y referencia
            Text(
                text = producto.product_name,
                style = MaterialTheme.typography.titleSmall.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = Color(0xFF333333)
                ),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = producto.reference,
                style = MaterialTheme.typography.bodySmall.copy(
                    fontSize = 13.sp,
                    color = Color.Gray
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Imagen y datos alineados
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                AsyncImage(
                    model = producto.imgUrl,
                    contentDescription = producto.product_name,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .size(80.dp)
                        .clip(RoundedCornerShape(8.dp))
                )

                Spacer(modifier = Modifier.width(12.dp))

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    Text(
                        text = "Cantidad Disponible: ${producto.stock}",
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                    Text(
                        text = "Precio: $${String.format("%.2f", producto.price)}",
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontSize = 13.sp
                        )
                    )
                    Text(
                        text = "Código: ${producto.id}",
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontSize = 13.sp,
                            color = Color.Gray
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            // Estado "Disponible"
            Text(
                text = if (producto.stock > 0) "Disponible" else "Agotado",
                style = MaterialTheme.typography.bodySmall.copy(
                    fontSize = 12.sp,
                    color = if (producto.stock > 0) Color(0xFF008000) else Color.Red,
                    fontWeight = FontWeight.Medium
                ),
                modifier = Modifier.align(Alignment.End)
            )
        }
    }
}


//CLIENTES
@Composable
fun ClientsScreen(viewModel: ClientViewModel = hiltViewModel()) {
    val clients by viewModel.clients.collectAsState()
    var searchQuery by remember { mutableStateOf("") }
    var selectedInitial by remember { mutableStateOf<Char?>(null) }

    val filteredClients = clients
        .filter {
            it.Name.contains(searchQuery, ignoreCase = true) ||
                    it.Ruc.toString().contains(searchQuery)
        }
        .sortedBy { it.Name.lowercase() }

    val displayedClients = selectedInitial?.let { initial ->
        filteredClients.filter { it.Name.first().uppercaseChar() == initial }
    } ?: filteredClients

    val groupedClients = displayedClients.groupBy { it.Name.first().uppercaseChar() }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* Acción al hacer clic */ },
                containerColor = Blue50,
                contentColor = Color.White,
                shape = CircleShape
            ) {
                Icon(Icons.Default.Add, contentDescription = "Agregar")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color(0xFFF6F6F6))
        ) {
            // Título
            Text(
                text = "Clientes",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1E3A8A)
                ),
                modifier = Modifier.padding(start = 16.dp, top = 16.dp, bottom = 8.dp)
            )

            // Buscador
            OutlinedTextField(
                value = searchQuery,
                onValueChange = {
                    searchQuery = it
                    selectedInitial = null
                },
                placeholder = { Text("Buscar cliente") },
                trailingIcon = {
                    Icon(Icons.Default.Search, contentDescription = "Buscar")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
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

            Spacer(modifier = Modifier.height(8.dp))

            // Fila con filtro a la izquierda y lista de clientes a la derecha
            Row(modifier = Modifier.fillMaxSize()) {

                // Barra lateral de letras a la izquierda
                Column(
                    modifier = Modifier
                        .padding(start = 8.dp, end = 8.dp)
                        .width(36.dp)
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    LetterFilterButton(
                        label = "•",
                        isSelected = selectedInitial == null,
                        onClick = {
                            selectedInitial = null
                            searchQuery = ""
                        }
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    ('A'..'Z').forEach { letter ->
                        LetterFilterButton(
                            label = letter.toString(),
                            isSelected = selectedInitial == letter,
                            onClick = {
                                selectedInitial = letter
                                searchQuery = ""
                            }
                        )
                    }
                }

                // Lista principal a la derecha
                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 16.dp)
                ) {
                    item {
                        val headerText = if (selectedInitial == null) "Todos los clientes" else "Clientes con '${selectedInitial}'"
                        Text(
                            text = headerText,
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color(0xFFF0F0F0))
                                .padding(vertical = 8.dp, horizontal = 8.dp)
                        )
                    }

                    groupedClients.forEach { (initial, clientsForLetter) ->
                        item {
                            Text(
                                text = initial.toString(),
                                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(Color.LightGray)
                                    .padding(8.dp)
                            )
                        }

                        items(clientsForLetter) { client ->
                            ClientCard(client)
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }

                    if (displayedClients.isEmpty()) {
                        item {
                            Text(
                                "No se encontraron clientes.",
                                modifier = Modifier.padding(16.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}


// Botón de letra reutilizable con diseño mejorado
@Composable
fun LetterFilterButton(
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val background = if (isSelected) Blue50 else Color.Transparent
    val contentColor = if (isSelected) Color.White else Color.DarkGray

    Box(
        modifier = Modifier
            .size(28.dp)
            .clip(CircleShape)
            .background(background)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = label,
            fontSize = 12.sp,
            color = contentColor,
            fontWeight = FontWeight.Bold
        )
    }
}


@Composable
fun ClientCard(client: Client) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = client.Name,
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                    Text(
                        text = "RUC: ${client.Ruc}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                IconButton(onClick = { expanded = !expanded }) {
                    Icon(
                        imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                        contentDescription = if (expanded) "Mostrar menos" else "Mostrar más"
                    )
                }
            }

            AnimatedVisibility(visible = expanded) {
                Column(modifier = Modifier.padding(top = 8.dp)) {
                    Text(text = "Teléfono: ${client.telephone}", fontSize = 14.sp)
                    Text(text = "Email: ${client.email}", fontSize = 14.sp)
                    Text(text = "Crédito: ${client.credit}", fontSize = 14.sp)
                    Text(text = "Dirección: ${client.Address}", fontSize = 14.sp)
                    Text(
                        text = client.state,
                        modifier = Modifier.align(Alignment.End),
                        style = MaterialTheme.typography.bodySmall.copy(color = Color.Gray)
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ClientScreenPreview() {
    PRIMASAAPPMóvilTheme {
        ClientsScreen()
    }
}

//ORDERS
@Composable
fun OrdersScreen(orderViewModel: OrderViewModel = hiltViewModel()) {
    val orders by orderViewModel.orders.collectAsState()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* TODO: Navegar a nuevo pedido */ },
                containerColor = Color(0xFF005BBB),
                contentColor = Color.White,
                shape = CircleShape
            ) {
                Icon(Icons.Default.Add, contentDescription = "Agregar orden")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Mis Pedidos",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF005BBB)
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn {
                items(orders) { order ->
                    OrderCard(order = order)
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
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
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Encabezado
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
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

            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Cliente: ${customer.Name}", fontSize = 14.sp)
            Text(
                text = "Total: $${String.format("%.2f", order.totalWithTax)}",
                color = Color(0xFF007BFF),
                fontWeight = FontWeight.SemiBold,
                fontSize = 15.sp
            )

            Spacer(modifier = Modifier.height(8.dp))
            Text("Productos:", fontWeight = FontWeight.SemiBold, fontSize = 13.sp)

            // Primer producto
            if (firstProduct != null) {
                ProductItem(firstProduct)
                Divider(color = Color.LightGray)
            }

            // Productos extra (expandidos)
            AnimatedVisibility(visible = expanded) {
                Column {
                    products.drop(1).forEach { product ->
                        ProductItem(product)
                        Divider(color = Color.LightGray)
                    }
                }
            }

            // Flecha expandir/contraer
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { expanded = !expanded }
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = if (expanded) "Contraer" else "Expandir",
                    tint = Color(0xFF005BBB)
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


