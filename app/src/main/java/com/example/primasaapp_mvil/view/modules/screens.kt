package com.example.primasaapp_mvil.view.modules

import android.os.Build
import android.util.Log
import android.util.Patterns
import androidx.annotation.RequiresApi
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.ui.draw.clip
import androidx.navigation.NavController
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import com.example.primasaapp_mvil.model.ClientRequest
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Remove
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.text.input.KeyboardType
import com.example.primasaapp_mvil.model.ClientUpdate
import com.example.primasaapp_mvil.model.CustomerData
import com.example.primasaapp_mvil.model.OrderToSend
import com.example.primasaapp_mvil.model.ProductToSendJSON
import com.example.primasaapp_mvil.view.components.BarraInferiorPedido
import kotlinx.coroutines.delay
import java.math.BigDecimal
import java.math.RoundingMode
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

//DASHBOARD
@Composable
fun HomeScreen(
    navController: NavController,
    orderViewModel: OrderViewModel = hiltViewModel(),
    userViewModel: UserViewModel = hiltViewModel()
) {
    val username by userViewModel.username.collectAsState()
    val name by userViewModel.Name.collectAsState()
    val salesCity by userViewModel.sales_CITY.collectAsState()
    val orders by orderViewModel.orders.collectAsState()
    val cedula by userViewModel.cedula.collectAsState()

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
        PerfilCard(username = username, name = name, city = salesCity, cedula = cedula as Int)

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
        OpcionesGrid(navController)
    }
}


@Composable
fun PerfilCard(username: String, name: String, city: String, cedula: Int) {
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
            Text(text = "CI: XXXXXXX${cedula.toString().takeLast(3)}", fontSize = 13.sp)
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
fun OpcionesGrid(navController: NavController) {
    val opciones = listOf(
        Triple("Tomar Pedido", Icons.Default.Edit, "resgisterOrder"),
        Triple("Registrar Cliente", Icons.Default.PersonAdd, "registerClient"),
        Triple("Consular Inventario", Icons.Default.List, "inventory"),
        Triple("Modificar Pedido", Icons.Default.EditNote, "orders"),
        Triple("Productos Destacados", Icons.Default.Star, "inventory"),
        Triple("Mis Proformas", Icons.Default.Receipt, "orders")
    )

    Column(modifier = Modifier.padding(horizontal = 8.dp)) {
        opciones.chunked(3).forEach { fila ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                fila.forEach { (label, icon, route) ->
                    Button(
                        onClick = { navController.navigate(route) },
                        modifier = Modifier
                            .weight(1f)
                            .aspectRatio(1f),
                        contentPadding = PaddingValues(8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White,
                            contentColor = MaterialTheme.colorScheme.primary
                        ),
                        elevation = ButtonDefaults.buttonElevation(4.dp),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        OpcionShortcut(label = label, icon = icon)
                    }
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
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            modifier = Modifier.size(32.dp),
            tint = Color.Black.copy(alpha = 0.87f)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall.copy(color = Color.Black.copy(alpha = 0.87f)),
            textAlign = TextAlign.Center
        )
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
    var showDialog by remember { mutableStateOf(false) }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .clickable { showDialog = true },
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

    if (showDialog) {
        ProductDetailDialog(product = producto) {
            showDialog = false
        }
    }
}


@Composable
fun ProductDetailDialog(product: Product, onDismiss: () -> Unit) {
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = Color.White,
            tonalElevation = 8.dp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Text(
                    text = product.product_name,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = Color(0xFF222222)
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                AsyncImage(
                    model = product.imgUrl,
                    contentDescription = product.product_name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                        .clip(RoundedCornerShape(12.dp))
                )

                Spacer(modifier = Modifier.height(12.dp))

                DetailRow(label = "Referencia:", value = product.reference)
                DetailRow(label = "Descripción:", value = product.description)
                DetailRow(label = "Precio:", value = "$${String.format("%.2f", product.price)}")
                DetailRow(label = "Stock disponible:", value = "${product.stock}")
                DetailRow(label = "Código:", value = "${product.id}")
                DetailRow(
                    label = "Estado:",
                    value = if (product.stock > 0) "Disponible" else "Agotado",
                    valueColor = if (product.stock > 0) Color(0xFF008000) else Color.Red
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = onDismiss,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Cerrar")
                }
            }
        }
    }
}

@Composable
fun DetailRow(label: String, value: String, valueColor: Color = Color.DarkGray) {
    Column(modifier = Modifier.padding(vertical = 4.dp)) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall.copy(
                fontWeight = FontWeight.Medium,
                color = Color.Gray
            )
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.SemiBold,
                color = valueColor
            )
        )
    }
}



//CLIENTES
@Composable
fun ClientsScreen(navController: NavController, viewModel: ClientViewModel = hiltViewModel()) {
    val clients by viewModel.clients.collectAsState()
    var searchQuery by remember { mutableStateOf("") }
    var selectedInitial by remember { mutableStateOf<Char?>(null) }

    val filteredClients = clients
        .filter {
            it.ComercialName.contains(searchQuery, ignoreCase = true) ||
                    it.Ruc.toString().contains(searchQuery)
        }
        .sortedBy { it.ComercialName.lowercase() }

    val displayedClients = selectedInitial?.let { initial ->
        filteredClients.filter { it.ComercialName.first().uppercaseChar() == initial }
    } ?: filteredClients

    val groupedClients = displayedClients.groupBy { it.ComercialName.first().uppercaseChar() }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate("registerClient")
                },
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
            Text(
                text = "Clientes",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF005BBB)
                ),
                modifier = Modifier.padding(start = 16.dp, top = 16.dp, bottom = 8.dp)
            )

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

            Row(modifier = Modifier.fillMaxSize()) {
                LazyColumn(
                    modifier = Modifier
                        .padding(start = 8.dp, end = 8.dp)
                        .width(36.dp)
                        .fillMaxHeight(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    contentPadding = PaddingValues(vertical = 8.dp)
                ) {
                    item {
                        LetterFilterButton(
                            label = "•",
                            isSelected = selectedInitial == null,
                            onClick = {
                                selectedInitial = null
                                searchQuery = ""
                            }
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }

                    items(('A'..'Z').toList()) { letter ->
                        LetterFilterButton(
                            label = letter.toString(),
                            isSelected = selectedInitial == letter,
                            onClick = {
                                selectedInitial = letter
                                searchQuery = ""
                            }
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }

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
                            ClientCard(client, navController)
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
fun ClientCard(
    client: Client,
    navController: NavController
) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { navController.navigate("clientDetail/${client.Ruc}") } // 👈 Redirige al hacer clic en la tarjeta
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = client.ComercialName,
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                    Text(
                        text = "RUC: ${client.Ruc}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                IconButton(
                    onClick = {
                        expanded = !expanded
                    }
                ) {
                    Icon(
                        imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                        contentDescription = if (expanded) "Mostrar menos" else "Mostrar más"
                    )
                }
            }

            AnimatedVisibility(visible = expanded) {
                Column(modifier = Modifier.padding(top = 8.dp)) {
                    Text(text = "Nombre del propietario: ${client.Name}", fontSize = 14.sp)
                    Text(text = "Teléfono: ${client.telephone}", fontSize = 14.sp)
                    Text(text = "Email: ${client.email}", fontSize = 14.sp)
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClientDetailScreen(
    clientRuc: String,
    clientViewModel: ClientViewModel,
    onBack: () -> Unit
) {
    val client by clientViewModel.selectedClient.collectAsState()
    val registerResult by clientViewModel.registerResult.collectAsState()
    val isLoadingClient by clientViewModel.isLoadingClient.collectAsState()
    val isLoading by clientViewModel.isLoading.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }

    var telephone by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var state by remember { mutableStateOf("") }

    var telephoneError by remember { mutableStateOf<String?>(null) }
    var emailError by remember { mutableStateOf<String?>(null) }
    var addressError by remember { mutableStateOf<String?>(null) }

    var stateMenuExpanded by remember { mutableStateOf(false) }
    val stateOptions = listOf("al día", "en deuda")

    var hasInitialized by remember { mutableStateOf(false) }

    LaunchedEffect(clientRuc) {
        clientViewModel.fetchClientById(clientRuc)
    }

    LaunchedEffect(registerResult) {
        registerResult?.onSuccess { message ->
            snackbarHostState.showSnackbar(
                message = message,
                withDismissAction = true,
                duration = SnackbarDuration.Short
            )
            delay(1000)
            onBack()
            clientViewModel.clearRegisterResult()
        }?.onFailure { error ->
            snackbarHostState.showSnackbar(
                message = error.message ?: "Error desconocido",
                withDismissAction = true,
                duration = SnackbarDuration.Short
            )
            clientViewModel.clearRegisterResult()
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState) { data ->
                val isError = data.visuals.message.contains("error", ignoreCase = true)
                Snackbar(
                    snackbarData = data,
                    containerColor = if (isError) Color.Red else Color(0xFF005BBB),
                    contentColor = Color.White
                )
            }
        },
        containerColor = Color(0xFFF6F6F6)
    ) { padding ->

        if (isLoadingClient || client == null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = Color(0xFF005BBB))
            }
        } else {
            val currentClient = client!!


                telephone = currentClient.telephone
                email = currentClient.email ?: ""
                address = currentClient.Address ?: ""
                state = currentClient.state ?: ""
                hasInitialized = true


            val name = currentClient.Name ?: ""
            val ruc = currentClient.Ruc
            val comercialName = currentClient.ComercialName ?: ""

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(padding)
                    .padding(10.dp)
            ) {
                Text(
                    text = "Clientes",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF005BBB)
                    ),
                    modifier = Modifier.padding(start = 16.dp, top = 16.dp, bottom = 8.dp)
                )

                Card(
                    shape = RoundedCornerShape(12.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("Información del Cliente", style = MaterialTheme.typography.labelLarge)
                            IconButton(onClick = onBack) {
                                Icon(Icons.Default.Delete, contentDescription = "Eliminar", tint = Color.Red)
                            }
                        }

                        OutlinedTextField(
                            value = name,
                            onValueChange = {},
                            label = { Text("Nombre del Cliente") },
                            readOnly = true,
                            modifier = Modifier.fillMaxWidth(),
                            enabled = false,
                            colors = OutlinedTextFieldDefaults.colors(
                                disabledTextColor = Color.Gray,
                                disabledBorderColor = Color.LightGray,
                                disabledLabelColor = Color.Gray
                            )
                        )

                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(
                            value = comercialName,
                            onValueChange = {},
                            label = { Text("Nombre Comercial") },
                            readOnly = true,
                            modifier = Modifier.fillMaxWidth(),
                            enabled = false,
                            colors = OutlinedTextFieldDefaults.colors(
                                disabledTextColor = Color.Gray,
                                disabledBorderColor = Color.LightGray,
                                disabledLabelColor = Color.Gray
                            )
                        )

                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(
                            value = ruc,
                            onValueChange = {},
                            label = { Text("RUC") },
                            readOnly = true,
                            modifier = Modifier.fillMaxWidth(),
                            enabled = false,
                            colors = OutlinedTextFieldDefaults.colors(
                                disabledTextColor = Color.Gray,
                                disabledBorderColor = Color.LightGray,
                                disabledLabelColor = Color.Gray
                            )
                        )

                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(
                            value = telephone,
                            onValueChange = {
                                if (!isLoading) {
                                    telephone = it
                                    telephoneError = if (!it.matches(Regex("^09\\d{8}\$"))) "Teléfono inválido" else null
                                }
                            },
                            label = { Text("Teléfono") },
                            isError = telephoneError != null,
                            modifier = Modifier.fillMaxWidth(),
                            enabled = !isLoading
                        )
                        telephoneError?.let {
                            Text(text = it, color = Color.Red, style = MaterialTheme.typography.labelSmall)
                        }

                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(
                            value = email,
                            onValueChange = {
                                if (!isLoading) {
                                    email = it
                                    emailError = if (!Patterns.EMAIL_ADDRESS.matcher(it).matches()) "Correo inválido" else null
                                }
                            },
                            label = { Text("Correo electrónico") },
                            isError = emailError != null,
                            modifier = Modifier.fillMaxWidth(),
                            enabled = !isLoading
                        )
                        emailError?.let {
                            Text(text = it, color = Color.Red, style = MaterialTheme.typography.labelSmall)
                        }

                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(
                            value = address,
                            onValueChange = {
                                if (!isLoading) {
                                    address = it
                                    addressError = if (it.length !in 8..200) "Dirección debe tener entre 8 y 200 caracteres" else null
                                }
                            },
                            label = { Text("Dirección") },
                            isError = addressError != null,
                            modifier = Modifier.fillMaxWidth(),
                            enabled = !isLoading
                        )
                        addressError?.let {
                            Text(text = it, color = Color.Red, style = MaterialTheme.typography.labelSmall)
                        }

                        Spacer(modifier = Modifier.height(8.dp))
                        ExposedDropdownMenuBox(
                            expanded = stateMenuExpanded,
                            onExpandedChange = {
                                if (!isLoading) {
                                    stateMenuExpanded = !stateMenuExpanded
                                }
                            }
                        ) {
                            OutlinedTextField(
                                value = state,
                                onValueChange = {},
                                readOnly = true,
                                label = { Text("Estado") },
                                trailingIcon = {
                                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = stateMenuExpanded)
                                },
                                modifier = Modifier.menuAnchor().fillMaxWidth(),
                                enabled = !isLoading
                            )

                            ExposedDropdownMenu(
                                expanded = stateMenuExpanded,
                                onDismissRequest = { stateMenuExpanded = false },
                                modifier = Modifier.background(Color.White)
                            ) {
                                stateOptions.forEachIndexed { index, option ->
                                    DropdownMenuItem(
                                        onClick = {
                                            state = option
                                            stateMenuExpanded = false
                                        },
                                        text = { Text(option) }
                                    )
                                    if (index < stateOptions.lastIndex) {
                                        Divider(thickness = 0.5.dp, color = Color.LightGray)
                                    }
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = {
                                telephoneError = if (!telephone.matches(Regex("^09\\d{8}\$"))) "Teléfono inválido" else null
                                emailError = if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) "Correo inválido" else null
                                addressError = if (address.length !in 8..200) "Dirección debe tener entre 8 y 200 caracteres" else null

                                if (telephoneError == null && emailError == null && addressError == null) {
                                    val updatedClient = ClientUpdate(
                                        Address = address,
                                        telephone = telephone,
                                        email = email,
                                        state = state
                                    )
                                    clientViewModel.updateClient(clientRuc, updatedClient)
                                }
                            },
                            enabled = !isLoading,
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1E3A8A))
                        ) {
                            if (isLoading) {
                                CircularProgressIndicator(
                                    color = Color.White,
                                    strokeWidth = 2.dp,
                                    modifier = Modifier.size(20.dp)
                                )
                            } else {
                                Text("Guardar Cambios", color = Color.White)
                            }
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun RegisterClientScreen(
    viewModel: ClientViewModel = hiltViewModel(),
    onSuccess: () -> Unit = {}
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val registerResult by viewModel.registerResult.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    var name by remember { mutableStateOf("") }
    var nameError by remember { mutableStateOf<String?>(null) }

    var commercialName by remember { mutableStateOf("") }
    var commercialNameError by remember { mutableStateOf<String?>(null) }

    var ruc by remember { mutableStateOf("") }
    var rucError by remember { mutableStateOf<String?>(null) }

    var email by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf<String?>(null) }

    var address by remember { mutableStateOf("") }
    var addressError by remember { mutableStateOf<String?>(null) }

    var phone by remember { mutableStateOf("") }
    var phoneError by remember { mutableStateOf<String?>(null) }

    var state by remember { mutableStateOf("al día") }
    var credit by remember { mutableStateOf("Crédito") } // no usado aquí, lo dejé por si quieres usarlo

    val context = LocalContext.current

    // Validación para habilitar el botón
    val isFormValid = listOf(
        nameError,
        commercialNameError,
        rucError,
        emailError,
        addressError,
        phoneError
    ).all { it == null } &&
            name.isNotBlank() &&
            commercialName.isNotBlank() &&
            ruc.length == 13 &&
            email.isNotBlank() &&
            Patterns.EMAIL_ADDRESS.matcher(email).matches() &&
            address.isNotBlank() &&
            phone.length == 10

    // Manejo del resultado de registro (mostrar Snackbar)
    LaunchedEffect(registerResult) {
        registerResult?.onSuccess { message ->
            snackbarHostState.showSnackbar(
                message = "Cliente registrado con éxito",
                withDismissAction = true,
                duration = SnackbarDuration.Short
            )
            onSuccess()
            viewModel.clearRegisterResult()
        }?.onFailure { error ->
            snackbarHostState.showSnackbar(
                message = error.message ?: "Error desconocido",
                withDismissAction = true,
                duration = SnackbarDuration.Short
            )
            viewModel.clearRegisterResult()
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState) { data ->
                val isError = data.visuals.message.contains("error", ignoreCase = true)
                Snackbar(
                    snackbarData = data,
                    containerColor = if (isError) Color.Red else Color(0xFF005BBB),
                    contentColor = Color.White
                )
            }
        },
        containerColor = Color(0xFFF6F6F6)
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF6F6F6))
                .padding(padding)
        ) {
            Text(
                text = "Nuevo Cliente",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF005BBB)
                ),
                modifier = Modifier.padding(16.dp)
            )

            Card(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    // Nombre
                    OutlinedTextField(
                        value = name,
                        onValueChange = {
                            name = it
                            nameError = if (it.isBlank()) "El nombre es obligatorio" else null
                        },
                        label = { Text("Nombre del Cliente") },
                        isError = nameError != null,
                        modifier = Modifier.fillMaxWidth()
                    )
                    nameError?.let { Text(it, color = Color.Red, style = MaterialTheme.typography.labelSmall) }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Nombre Comercial
                    OutlinedTextField(
                        value = commercialName,
                        onValueChange = {
                            commercialName = it
                            commercialNameError = if (it.isBlank()) "El nombre comercial es obligatorio" else null
                        },
                        label = { Text("Nombre Comercial") },
                        isError = commercialNameError != null,
                        modifier = Modifier.fillMaxWidth()
                    )
                    commercialNameError?.let { Text(it, color = Color.Red, style = MaterialTheme.typography.labelSmall) }

                    Spacer(modifier = Modifier.height(8.dp))

                    // RUC
                    OutlinedTextField(
                        value = ruc,
                        onValueChange = {
                            ruc = it
                            rucError = if (it.length != 13 || !it.all { ch -> ch.isDigit() }) "El RUC debe tener 13 dígitos numéricos" else null
                        },
                        label = { Text("RUC del Cliente") },
                        isError = rucError != null,
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                    rucError?.let { Text(it, color = Color.Red, style = MaterialTheme.typography.labelSmall) }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Email
                    OutlinedTextField(
                        value = email,
                        onValueChange = {
                            email = it
                            emailError = if (!Patterns.EMAIL_ADDRESS.matcher(it).matches()) "Correo electrónico no válido" else null
                        },
                        label = { Text("Correo electrónico") },
                        isError = emailError != null,
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                    )
                    emailError?.let { Text(it, color = Color.Red, style = MaterialTheme.typography.labelSmall) }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Dirección
                    OutlinedTextField(
                        value = address,
                        onValueChange = {
                            address = it
                            addressError = if (it.isBlank()) "La dirección es obligatoria" else null
                        },
                        label = { Text("Dirección") },
                        isError = addressError != null,
                        modifier = Modifier.fillMaxWidth()
                    )
                    addressError?.let { Text(it, color = Color.Red, style = MaterialTheme.typography.labelSmall) }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Teléfono
                    OutlinedTextField(
                        value = phone,
                        onValueChange = {
                            phone = it
                            phoneError = if (it.length != 10 || !it.all { ch -> ch.isDigit() }) "El teléfono debe tener 10 dígitos numéricos" else null
                        },
                        label = { Text("Teléfono") },
                        isError = phoneError != null,
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
                    )
                    phoneError?.let { Text(it, color = Color.Red, style = MaterialTheme.typography.labelSmall) }

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = {
                            // Validar todos los campos al hacer clic en Registrar
                            nameError = if (name.isBlank()) "El nombre es obligatorio" else null
                            commercialNameError = if (commercialName.isBlank()) "El nombre comercial es obligatorio" else null
                            rucError = if (ruc.length != 13 || !ruc.all { ch -> ch.isDigit() }) "El RUC debe tener 13 dígitos numéricos" else null
                            emailError = if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) "Correo electrónico no válido" else null
                            addressError = if (address.isBlank()) "La dirección es obligatoria" else null
                            phoneError = if (phone.length != 10 || !phone.all { ch -> ch.isDigit() }) "El teléfono debe tener 10 dígitos numéricos" else null

                            val hasError = listOf(
                                nameError, commercialNameError, rucError, emailError, addressError, phoneError
                            ).any { it != null }

                            if (!hasError) {
                                val client = ClientRequest(
                                    Name = name,
                                    ComercialName = commercialName,
                                    Ruc = ruc,
                                    email = email,
                                    Address = address,
                                    telephone = phone,
                                    state = state
                                )
                                viewModel.registerClient(client)
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                        shape = RoundedCornerShape(24.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isFormValid) Color(0xFF1E519D) else Color.Gray,
                            contentColor = Color.White
                        ),
                        enabled = isFormValid && !isLoading
                    ) {
                        if (isLoading) {
                            CircularProgressIndicator(
                                color = Color.White,
                                strokeWidth = 2.dp,
                                modifier = Modifier.size(20.dp)
                            )
                        } else {
                            Text("Registrar")
                        }
                    }
                }
            }
        }
    }
}




@Composable
fun CustomTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        modifier = Modifier
            .fillMaxWidth(0.95f)
            .height(60.dp)
            .padding(vertical = 4.dp),
        shape = RoundedCornerShape(12.dp),
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color.White,
            focusedContainerColor = Color.White
        )
    )
}


//ORDERS
@Composable
fun OrdersScreen(
    navController: NavHostController,
    orderViewModel: OrderViewModel
) {
    val orders by orderViewModel.orders.collectAsState()

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF6F6F6)), // Color de fondo de toda la pantalla
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    orderViewModel.clearOrderForm()
                    navController.navigate("orderEdit/0000") },
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
                .background(Color(0xFFF6F6F6)) // Asegura que el Column también tenga el fondo
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

            if (orders.isEmpty()) {
                Text(
                    text = "No hay pedidos registrados.",
                    color = Color.Gray,
                    style = MaterialTheme.typography.bodyMedium
                )
            } else {
                LazyColumn {
                    items(orders) { order ->
                        OrderCard(
                            order = order,
                            onClick = {
                                navController.navigate("orderDetail/${order._id}")
                            }
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }
        }
    }
}



@Composable
fun OrderCard(
    order: Order,
    onClick: (Order) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val customer = order.customer
    val products = order.products
    val firstProduct = products.firstOrNull()

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick(order) }, // navegación aquí
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
                    text = "Orden #${order._id.toString().takeLast(5)}",
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
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                IconButton(onClick = { expanded = !expanded }) {
                    Icon(
                        imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                        contentDescription = if (expanded) "Contraer" else "Expandir",
                        tint = Color(0xFF005BBB)
                    )
                }
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
            Text(text = "${details.product_name} ${details.price}", fontSize = 14.sp)
            Text(text = "$${details.price}", fontSize = 13.sp, color = Color.Gray)
        }
        Text(text = "${product.quantity}", fontSize = 14.sp)
    }
}



@RequiresApi(Build.VERSION_CODES.O)
fun formatProformaDate(dateString: String): String {
    val zonedDateTime = ZonedDateTime.parse(dateString)
    val formatter = DateTimeFormatter.ofPattern("d 'de' MMMM 'de' yyyy, h:mm a", Locale("es", "ES"))
    return zonedDateTime.format(formatter)
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun OrderDetailScreen(
    orderId: String,
    orderViewModel: OrderViewModel,
    navController: NavController,
    onBack: () -> Unit
) {
    val order by orderViewModel.selectedOrder.collectAsState()


    LaunchedEffect(orderId) {
        orderViewModel.fetchOrderById(orderId)
    }

    Scaffold(
        topBar = {
            Surface(
                color = Color(0xFFF5F5F5),
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(64.dp)
                        .padding(horizontal = 16.dp)
                        .background(Color(0xFFF5F5F5)),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Proforma",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF005BBB)
                        )
                    )
                    IconButton(onClick = { navController.navigate("orderEdit/${orderId}") }) {
                        Icon(
                            imageVector = Icons.Filled.Edit,
                            contentDescription = "Editar",
                            tint = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                }
            }
        },
        containerColor = Color(0xFFF5F5F5)
    ) { innerPadding ->
        order?.let {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState())
                    .background(Color(0xFFF5F5F5))
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    shape = MaterialTheme.shapes.medium,
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {

                        val formattedDate = formatProformaDate(order!!.createdAt.toString())

                        Text(
                            text = "Fecha de emisión: $formattedDate",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.End
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        // ID
                        val orderIdSafe = order!!._id.takeLast(5)
                        Text(
                            text = "Orden #$orderIdSafe",
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.onSurface
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        ClientInfoSection(order!!.customer)

                        Spacer(modifier = Modifier.height(16.dp))

                        // Productos
                        Text(
                            text = "Productos:",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Divider(color = MaterialTheme.colorScheme.outlineVariant)

                        if (order!!.products.isEmpty()) {
                            Text("No hay productos", color = Color.Gray)
                        } else {
                            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                order!!.products.forEach { product ->
                                    ProductItemRow(product, order!!.discountApplied )
                                }
                            }
                        }

                        Divider(
                            color = MaterialTheme.colorScheme.outlineVariant,
                            thickness = 1.dp,
                            modifier = Modifier.padding(vertical = 12.dp)
                        )

                        val descu = order!!.netTotal - (order!!.netTotal * (order!!.discountApplied.toDouble() / 100))

                        println(descu)
                        // Resumen de totales
                        SummarySection(
                            discountPercent = order!!.discountApplied,
                            discountAmount = (order!!.netTotal * (order!!.discountApplied.toDouble() / 100)),
                            netTotal = order!!.netTotal,
                            totalWithTax = order!!.totalWithTax
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = "Condición y forma de pago: ${order!!.credit}",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp),
                            textAlign = TextAlign.Center
                        )

                    }
                }
            }
        }
    }
}

@Composable
fun ClientInfoSection(customer: CustomerData) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = Color.LightGray.copy(alpha = 0.1f),
                shape = RoundedCornerShape(8.dp)
            )
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        ClientInfoRow("Cliente:", customer.Name)
        ClientInfoRow("RUC:", customer.Ruc.toString())
        ClientInfoRow("Dirección:", customer.Address)
        ClientInfoRow("Teléfono:", customer.telephone.toString())
        ClientInfoRow("Email:", customer.email)
    }
}

@Composable
fun ClientInfoRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.End
        )
    }
}



@Composable
private fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
private fun ProductItemRow(product: ProductSelected, discountPercent: Int) {
    val details = product.productDetails
    val quantity = product.quantity
    val name = details.product_name ?: "Sin nombre"
    val reference = details.reference ?: ""
    val unitPrice = details.price ?: 0.0
    val total = unitPrice * quantity
    val discountAmount = discountPercent / 100.0

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp)
            ) {
                Text(
                    text = "${product.quantity} X ${name}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "$${"%.2f".format(unitPrice)} X ${discountPercent}%  (${"%.2f".format(unitPrice*discountAmount*product.quantity)})",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                    modifier = Modifier.padding(start = 16.dp)
                )
            }

            Text(
                text = "$${"%.2f".format((quantity * unitPrice)-(unitPrice  *discountAmount * quantity))}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
        Divider(modifier = Modifier.padding(vertical = 4.dp))
    }
}

@Composable
private fun SummarySection(
    discountPercent: Int,
    discountAmount: Double,
    netTotal: Double,
    totalWithTax: Double
) {
    val summaryColor = Color(0xFF6F9EDC)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = summaryColor.copy(alpha = 0.2f),
                shape = RoundedCornerShape(8.dp)
            )
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Fila de descuento aplicado
        SummaryRowStyled("Total Neto:", "$${"%.2f".format(netTotal)}", summaryColor)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Descuento Aplicado:",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = "${discountPercent.toInt()}%",
                style = MaterialTheme.typography.bodyMedium,
                color = summaryColor,
                fontWeight = FontWeight.Bold
            )
        }
        // Resto de filas
        SummaryRowStyled("Valor Desc.Aplicado:", "- $${"%.2f".format(discountAmount)}", summaryColor)
        SummaryRowStyled(
            "Total con IVA 15%:",
            "$${"%.2f".format(totalWithTax)}",
            summaryColor,
            isBold = true
        )
    }
}

@Composable
private fun SummaryRowStyled(
    label: String,
    value: String,
    accentColor: Color,
    isBold: Boolean = false
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = if (isBold) MaterialTheme.typography.bodyLarge else MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
        )
        Text(
            text = value,
            style = if (isBold) MaterialTheme.typography.bodyLarge else MaterialTheme.typography.bodyMedium,
            color = if (isBold) accentColor else MaterialTheme.colorScheme.onSurface,
            fontWeight = if (isBold) FontWeight.Bold else FontWeight.Normal
        )
    }
}

@Composable
fun EditOrder(
    orderId: String,
    orderViewModel: OrderViewModel,
    navController: NavController,
    onBack: () -> Unit
) {
    val selectedOrder by orderViewModel.selectedOrder.collectAsState()
    val isRegistering by orderViewModel.isRegistering.collectAsState()
    val context = LocalContext.current
    val registerResult by orderViewModel.registerResult.collectAsState()

    // Buscar la orden solo una vez
    LaunchedEffect(orderId) {
        orderViewModel.fetchOrderById(orderId)
    }

    // Precargar datos cuando llega la orden
    LaunchedEffect(selectedOrder) {
        selectedOrder?.let {
            orderViewModel.precargarDatosDeOrden(it)
        }
    }

    // Mostrar resultado si se registra/actualiza
    LaunchedEffect(registerResult) {
        if (registerResult.isNotBlank()) {
            if (registerResult.contains("exitosamente", true)) {
                navController.popBackStack() // o navega a donde necesites
            }
        }
    }

    if (orderId == "0000"){
        orderViewModel.setRegister()
        orderViewModel.clearOrderForm()
        RegisterOrderScreen(
            orderId = orderId,
            orderViewModel = orderViewModel,
            navController = navController,
            onBack = onBack,)
    }else(
            if (selectedOrder == null) {
                // Loading UI mientras se carga la orden
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }

            } else {
                orderViewModel.setUpdater()
                orderViewModel.setSelectedId(orderId)
                RegisterOrderScreen(
                    orderId = orderId,
                    orderViewModel = orderViewModel,
                    navController = navController,
                    onBack = onBack,

                    )
            }
    )



}



@Composable
fun RegisterOrderScreen(
    orderId: String,
    navController: NavController,
    viewModel: ProductViewModel = hiltViewModel(),
    orderViewModel: OrderViewModel,
    clientViewModel: ClientViewModel = hiltViewModel(),
    onBack: () -> Unit
) {

    val isEditMode = orderViewModel.isEdites.collectAsState()

    val orderID by orderViewModel.selectedID.collectAsState()

    println(isEditMode.value)

    val productos by viewModel.products.collectAsState()
    val clients by clientViewModel.clients.collectAsState()
    val isRegistering by orderViewModel.isRegistering.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }
    val registerResult by orderViewModel.registerResult.collectAsState()

    var expandedClient by remember { mutableStateOf(false) }
    val selectedClient by orderViewModel.selectedClient.collectAsState()
    val selectedDiscount by orderViewModel.selectedDiscount.collectAsState()

    val selectedProducts = orderViewModel.obtenerProductosParaEnviar(productos)


    var expandedDiscount by remember { mutableStateOf(false) }
    val discountOptions = listOf(
        "Contado 1 día 20% Desc.",
        "Crédito 1 día 15% Desc.",
        "Crédito 30 días 15% Desc.",
    )

    val comment = orderViewModel.comentario.collectAsState().value


    val discountPercent = when {
        selectedDiscount?.contains("20%") == true -> 20
        selectedDiscount?.contains("15%") == true -> 15
        else -> 0
    }

    val discountAmount = discountPercent / 100.0

    val totalNetoRounded = selectedProducts.sumOf { product ->
        val price = BigDecimal.valueOf(product.price)
        val quantity = BigDecimal(product.quantity)
        val discountRate = BigDecimal.valueOf(discountAmount)

        val discountedPrice = price.multiply(BigDecimal.ONE.subtract(discountRate))
        discountedPrice.multiply(quantity)
    }.setScale(2, RoundingMode.HALF_UP)



    val taxRate = BigDecimal("1.15") // 15% impuesto
    val totalWithTax = totalNetoRounded.multiply(taxRate).setScale(2, RoundingMode.HALF_UP)


    val isCommentValid = comment.length >= 10 || comment == "Sin novedades"
    val isFormValid = selectedClient != null && selectedDiscount != null && selectedProducts.isNotEmpty() && isCommentValid

    LaunchedEffect(registerResult) {
        if (registerResult.isNotBlank()) {
            val result = snackbarHostState.showSnackbar(
                message = registerResult,
                withDismissAction = true,
                duration = SnackbarDuration.Short
            )
            if (result == SnackbarResult.Dismissed || result == SnackbarResult.ActionPerformed) {
                orderViewModel.clearOrderForm()
                navController.navigate("orders")
            }
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState) { data ->
                val isError = data.visuals.message.contains("error", ignoreCase = true)
                Snackbar(
                    snackbarData = data,
                    containerColor = if (isError) Color.Red else Color(0xFF005BBB),
                    contentColor = Color.White
                )
            }
        },
        containerColor = Color(0xFFF6F6F6)
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF6F6F6))
                .verticalScroll(rememberScrollState())
                .padding(padding)
                .padding(16.dp)
        ) {
            if(isEditMode.value){
                Text(
                    text = "Actualizar Pedido",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF005BBB)
                    )
                )
            }else{
                Text(
                    text = "Registrar Pedido",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF005BBB)
                    )
                )
            }



            Spacer(modifier = Modifier.height(16.dp))

            // CLIENTE
            Text("Cliente", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)

            Box(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = selectedClient?.ComercialName ?: "Seleccionar",
                    onValueChange = {},
                    readOnly = true,
                    enabled = false,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { expandedClient = true },
                    trailingIcon = {
                        Icon(Icons.Default.ArrowDropDown, contentDescription = null)
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        disabledTextColor = Color.Black,
                        disabledContainerColor = Color(0xFFF3F4F6)
                    )
                )

                DropdownMenu(
                    expanded = expandedClient,
                    onDismissRequest = { expandedClient = false },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                ) {
                    clients.sortedBy { it.ComercialName }.forEach { client ->
                        DropdownMenuItem(
                            onClick = {
                                orderViewModel.setSelectedClient(client)
                                expandedClient = false
                            },
                            text = {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 4.dp)
                                ) {
                                    Text(client.ComercialName)
                                    Text("RUC: ${client.Ruc}", style = MaterialTheme.typography.bodySmall)
                                    Divider(modifier = Modifier.padding(top = 8.dp))
                                }
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // PRODUCTOS
            Text("Productos", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)

            if (selectedProducts.isEmpty()) {
                Button(
                    onClick = { navController.navigate("productSelecter") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF60A5FA))
                ) {
                    Text("Seleccionar productos")
                }
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFFF3F4F6), RoundedCornerShape(12.dp))
                        .padding(12.dp)
                ) {
                    selectedProducts.forEach { product ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column(
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(end = 8.dp)
                            ) {
                                Text(
                                    text = "${product.quantity} X ${product.name}",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                                Text(
                                    text = "$${"%.2f".format(product.price)} X ${discountPercent}%  (${"%.2f".format(product.price*discountAmount*product.quantity)})",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                                    modifier = Modifier.padding(start = 16.dp)
                                )
                            }

                            Text(
                                text = "$${"%.2f".format((product.quantity * product.price)-(product.price*discountAmount*product.quantity))}",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                        Divider(modifier = Modifier.padding(vertical = 4.dp))
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Button(
                        onClick = { navController.navigate("productSelecter") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF60A5FA))
                    ) {
                        Text("Cambiar productos")
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // DESCUENTO
            Text("Descuento", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)

            Box(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = selectedDiscount ?: "Seleccionar descuento",
                    onValueChange = {},
                    readOnly = true,
                    enabled = false,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { expandedDiscount = true },
                    trailingIcon = {
                        Icon(Icons.Default.ArrowDropDown, contentDescription = null)
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        disabledTextColor = Color.Black,
                        disabledContainerColor = Color(0xFFF3F4F6)
                    )
                )

                DropdownMenu(
                    expanded = expandedDiscount,
                    onDismissRequest = { expandedDiscount = false },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                ) {
                    discountOptions.forEach { discount ->
                        DropdownMenuItem(
                            onClick = {
                                orderViewModel.setSelectedDiscount(discount)
                                expandedDiscount = false
                            },
                            text = { Text(discount) }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // COMENTARIO
            Text("Comentario", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)

            OutlinedTextField(
                value = comment,
                onValueChange = {
                    if (it.length <= 30) {
                        orderViewModel.setComentario(it)
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Escribe un comentario") },
                singleLine = true,
                supportingText = {
                    Text("${comment.length}/30")
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // TOTAL
            Text("Subtotal", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
            OutlinedTextField(
                value = "$ ${totalNetoRounded.toPlainString()}",
                onValueChange = {},
                readOnly = true,
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    disabledTextColor = Color.Black,
                    disabledContainerColor = Color(0xFFF3F4F6)
                )
            )

            Spacer(modifier = Modifier.height(16.dp))
            Text("Total con IVA", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
            OutlinedTextField(
                value = "$ ${totalWithTax.toPlainString()}",
                onValueChange = {},
                readOnly = true,
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    disabledTextColor = Color.Black,
                    disabledContainerColor = Color(0xFFF3F4F6)
                )
            )

            Spacer(modifier = Modifier.height(24.dp))

            // BOTÓN FINAL
            Button(
                onClick = {
                    val parsedDiscount = selectedDiscount?.let { parseDiscountOption(it) }

                    val order = OrderToSend(
                        customer = selectedClient!!.Ruc.toString(),
                        discountApplied = parsedDiscount?.discountApplied ?: 0,
                        credit = parsedDiscount?.credit ?: "",
                        comment = comment,
                        netTotal = totalNetoRounded,
                        totalWithTax = totalWithTax,
                        products = selectedProducts.map {
                            ProductToSendJSON(
                                id = it.id.toString(),
                                quantity = it.quantity
                            )
                        }
                    )

                    println(order)

                    if (isEditMode.value){
                        orderViewModel.updateOrder(orderID.toString(), order)
                    }else{
                        orderViewModel.registerOrder(order)
                    }


                },
                enabled = isFormValid && !isRegistering,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(50),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isFormValid) Color(0xFF005BBB) else Color.Gray,
                    contentColor = Color.White
                )
            ) {
                if (isRegistering) {
                    CircularProgressIndicator(
                        color = Color.White,
                        modifier = Modifier.size(24.dp),
                        strokeWidth = 2.dp
                    )
                } else {
                    Text(
                        "Generar Orden",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                }
            }
        }
    }
}

data class DiscountInfo(
    val discountApplied: Int,
    val credit: String
)

fun parseDiscountOption(option: String): DiscountInfo {
    val discountRegex = Regex("(\\d+)%")
    val discount = discountRegex.find(option)?.groupValues?.get(1)?.toIntOrNull() ?: 0

    val creditRegex = Regex("(Contado|Crédito)\\s(\\d+)")
    val match = creditRegex.find(option)
    val creditType = match?.groupValues?.get(1) ?: ""
    val days = match?.groupValues?.get(2) ?: ""

    // Determinar si es singular o plural
    val dayLabel = if (days == "1") "día" else "días"

    val credit = "$creditType $days $dayLabel"

    return DiscountInfo(discountApplied = discount, credit = credit)
}

@Composable
fun ProductoCardToSend(
    producto: Product,
    pedidoViewModel: OrderViewModel,
    productViewModel: ProductViewModel = hiltViewModel()
) {
    val productosSeleccionados by pedidoViewModel.productosSeleccionados.collectAsState()
    val cantidad = productosSeleccionados[producto.id] ?: 0
    val productos by productViewModel.products.collectAsState()

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White) // Fondo blanco
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(12.dp)
        ) {
            AsyncImage(
                model = producto.imgUrl,
                contentDescription = producto.product_name,
                modifier = Modifier
                    .size(64.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Fit // Muestra la imagen completa sin recortarla
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(producto.product_name, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                Text("Disponibles: ${producto.stock}", fontSize = 12.sp)
                Text("$ ${producto.price}", fontSize = 12.sp)
                Text("${producto.reference}", fontSize = 12.sp)
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(
                    onClick = {
                        pedidoViewModel.disminuirCantidad(producto.id, productos)
                    },
                    enabled = cantidad > 0
                ) {
                    Icon(Icons.Default.Remove, contentDescription = "Quitar")
                }

                Text(
                    "$cantidad",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.width(24.dp)
                )

                IconButton(
                    onClick = {
                        if (cantidad < producto.stock) {
                            pedidoViewModel.aumentarCantidad(producto.id, producto, productos)
                        }
                    },
                    enabled = cantidad < producto.stock
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Agregar")
                }
            }
        }
    }
}



@Composable
fun ProductSelectorView(
    viewModel: ProductViewModel = hiltViewModel(),
    orderViewModel: OrderViewModel,
    onCategoriaClick: (String) -> Unit,
    onBack: () -> Unit
) {
    val productos by viewModel.products.collectAsState()
    val productosPorCategoria = clasificarProductos(productos)
    val total by orderViewModel.total.collectAsState()
    val productosSeleccionados = orderViewModel.productosSeleccionados.collectAsState().value

    Scaffold(
        bottomBar = {
            BarraInferiorPedido(
                total = total.toFloat(),
                productosSeleccionados = productosSeleccionados,
                productos = productos,
                onFinalizarClick = {
                    val seleccion = orderViewModel.obtenerProductosParaEnviar(productos)
                    Log.d("Pedido", seleccion.toString())
                    onBack()
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color(0xFFF6F6F6)) // Fondo aplicado aquí
        ) {
            InventarioCategoriasScreen(
                productosPorCategoria = productosPorCategoria,
                onCategoriaClick = onCategoriaClick
            )
        }
    }
}














