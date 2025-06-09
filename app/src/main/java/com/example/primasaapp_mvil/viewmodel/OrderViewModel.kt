package com.example.primasaapp_mvil.viewmodel

import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.primasaapp_mvil.data.dataStore.DataStoreManager
import com.example.primasaapp_mvil.data.repository.AuthRepository
import com.example.primasaapp_mvil.model.Client
import com.example.primasaapp_mvil.model.Order
import com.example.primasaapp_mvil.model.OrderData
import com.example.primasaapp_mvil.model.OrderDatabyID
import com.example.primasaapp_mvil.model.OrderToSend
import com.example.primasaapp_mvil.model.Product
import com.example.primasaapp_mvil.model.ProductToSend
import com.example.primasaapp_mvil.model.ProductToSendJSON
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.first
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(
    private val repository: AuthRepository,
    private val dataStoreManager: DataStoreManager,
) : ViewModel() {

    private val _orders = MutableStateFlow<List<Order>>(emptyList())
    val orders: StateFlow<List<Order>> = _orders

    private val _selectedOrder = MutableStateFlow<OrderDatabyID?>(null)
    val selectedOrder: StateFlow<OrderDatabyID?> = _selectedOrder

    private val _registerResult = MutableStateFlow("")
    val registerResult: StateFlow<String> = _registerResult.asStateFlow()

    private val _productosSeleccionados =
        MutableStateFlow<Map<Int, Int>>(emptyMap()) // id -> cantidad
    val productosSeleccionados: StateFlow<Map<Int, Int>> = _productosSeleccionados

    private val _productosSeleccionadosData = mutableMapOf<Int, Product>() // id -> producto

    private val _selectedClient = MutableStateFlow<Client?>(null)
    val selectedClient: StateFlow<Client?> = _selectedClient

    private val _selectedDiscount = MutableStateFlow<String?>(null)
    val selectedDiscount: StateFlow<String?> = _selectedDiscount

    private val _selectedID = MutableStateFlow<String?>(null)
    val selectedID: StateFlow<String?> = _selectedID

    private val _isRegistering = MutableStateFlow(false)
    val isRegistering: StateFlow<Boolean> = _isRegistering.asStateFlow()

    private val _isEdites = MutableStateFlow(false)
    val isEdites: StateFlow<Boolean> = _isEdites


    private val _comentario = MutableStateFlow("")
    val comentario: StateFlow<String> = _comentario

    fun setComentario(value: String) {
        _comentario.value = value
    }

    private val _total = MutableStateFlow(0.0)
    val total: StateFlow<Double> get() = _total

    private val _successMessage = MutableStateFlow<String?>(null)
    val successMessage: StateFlow<String?> = _successMessage



    init {
        fetchOrders()
    }

    private fun fetchOrders() {
        viewModelScope.launch {
            val token = dataStoreManager.tokenFlow.first()
            val email = dataStoreManager.emailFlow.first()

            if (!token.isNullOrBlank() && email.isNotBlank()) {
                try {
                    val orders = repository.getOrders(token)
                    val filtered = orders.filter { it.seller.email == email }
                    _orders.value = filtered
                    _successMessage.value = "Órdenes obtenidas con éxito"
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }


    fun registerOrder(order: OrderToSend) {
        viewModelScope.launch {
            _isRegistering.value = true
            try {
                val token = dataStoreManager.tokenFlow.first()
                if (!token.isNullOrBlank()) {
                    val response = repository.registrerOrder(token, order)

                    if (response.isSuccessful) {
                        _registerResult.value =
                            response.body()?.msg ?: "Orden registrada exitosamente."

                    } else {
                        val errorBody = response.errorBody()?.string()
                        _registerResult.value =
                            "Error al registrar orden. Código: ${response.code()}. Detalle: $errorBody"
                        println("Error al registrar orden: $errorBody")
                    }
                } else {
                    _registerResult.value = "Token no disponible."
                }
            } catch (e: Exception) {
                _registerResult.value = "Excepción al registrar orden: ${e.message}"
            } finally {
                _isRegistering.value = false
            }
        }
    }

    fun updateOrder(id: String, order: OrderToSend) {

        viewModelScope.launch {
            _isRegistering.value = true
            try {
                val token = dataStoreManager.tokenFlow.first()
                if (!token.isNullOrBlank()) {
                    val response = repository.updateOrder(id, token, order)
                    println(response)

                    if (response.isSuccessful) {
                        _registerResult.value =
                            response.body()?.msg ?: "Orden actualizada exitosamente."
                            _isEdites.value = false

                    } else {
                        val errorBody = response.errorBody()?.string()
                        _registerResult.value =
                            "Error al actualizar orden. Código: ${response.code()}. Detalle: $errorBody"
                        println("Error al actualizar orden: $errorBody")
                    }
                } else {
                    _registerResult.value = "Token no disponible."
                }
            } catch (e: Exception) {
                _registerResult.value = "Excepción al actualizar orden: ${e.message}"
            } finally {
                _isRegistering.value = false
            }
        }
    }


    fun clearOrderForm() {
        println("Limpiando")
        _selectedClient.value = null
        _selectedDiscount.value = null
        _registerResult.value = ""
        _isRegistering.value = false
        limpiarProductos()
        _selectedOrder.value = null
        _comentario.value = ""

    }


    fun aumentarCantidad(id: Int, producto: Product, productos: List<Product>) {
        val nuevaCantidad = (_productosSeleccionados.value[id] ?: 0) + 1
        _productosSeleccionadosData[id] = producto
        _productosSeleccionados.value = _productosSeleccionados.value.toMutableMap().apply {
            put(id, nuevaCantidad)
        }
        actualizarTotal(productos)
    }

    fun disminuirCantidad(id: Int, productos: List<Product>) {
        val cantidadActual = _productosSeleccionados.value[id] ?: return
        val nuevaCantidad = cantidadActual - 1

        val nuevoMapa = _productosSeleccionados.value.toMutableMap()

        if (nuevaCantidad > 0) {
            nuevoMapa[id] = nuevaCantidad
        } else {
            nuevoMapa.remove(id)
            _productosSeleccionadosData.remove(id)
        }

        _productosSeleccionados.value = nuevoMapa
        actualizarTotal(productos)
    }

    fun actualizarTotal(productos: List<Product>) {
        val nuevoTotal = _productosSeleccionados.value.entries.sumOf { (id, cantidad) ->
            val producto = productos.find { it.id == id }
            (producto?.price ?: 0.0) * cantidad
        }
        _total.value = nuevoTotal
    }

    fun obtenerProductosParaEnviar(productos: List<Product>): List<ProductToSend> {
        return _productosSeleccionados.value.mapNotNull { (id, cantidad) ->
            _productosSeleccionadosData[id]?.let {
                ProductToSend(
                    id = it.id,
                    name = it.product_name,
                    price = it.price,
                    quantity = cantidad
                )
            }
        }
    }

    fun fetchOrderById(id: String) {
        viewModelScope.launch{
            try {
                val token = dataStoreManager.tokenFlow.first()
                if (!token.isNullOrBlank()) {
                    val response = repository.getOrderById(id, token)
                    if (response.isSuccessful) {
                        response.body()?.let {
                            _selectedOrder.value = it.data
                            println("Orden obtenida: ${it.data}")
                        }
                    } else {
                        println("Error en la respuesta: ${response.code()} ${response.message()}")
                    }
                }
            } catch (e: Exception) {
                println("Excepción: ${e.message}")
            }
        }
    }

    fun setSelectedClient(client: Client) {
        _selectedClient.value = client
    }

    fun setSelectedDiscount(discount: String) {
        _selectedDiscount.value = discount
    }

    fun setSelectedId(id: String) {
        _selectedID.value = id
    }


    fun limpiarProductos() {
        _productosSeleccionados.value = emptyMap()
        _productosSeleccionadosData.clear()
        _total.value = 0.0
    }

    fun setRegister(){
        _isEdites.value = false
        println("Se cambia a registro")
        clearOrderForm()
    }

    fun setUpdater(){
        _isEdites.value = true
        println("Se cambia a Actualizar")
    }

    fun precargarDatosDeOrden(order: OrderDatabyID) {

        // Cliente
        _selectedClient.value = Client(
            id = order.customer._id,
            Name = order.customer.Name,
            ComercialName = order.customer.ComercialName,
            Ruc = order.customer.Ruc,
            Address = order.customer.Address,
            telephone = order.customer.telephone,
            email = order.customer.email,
            state = order.customer.state
        )

        val opcionSeleccionada = obtenerOpcionDescuento(order.discountApplied, order.credit)

        _selectedDiscount.value = opcionSeleccionada

        // Productos
        val productosMap = mutableMapOf<Int, Int>()
        _productosSeleccionadosData.clear()

        order.products.forEach { productSelected ->
            val id = productSelected.productDetails.id
            val cantidad = productSelected.quantity
            productosMap[id] = cantidad

            _productosSeleccionadosData[id] = Product(
                id = id,
                product_name = productSelected.productDetails.product_name ?: "",
                price = productSelected.productDetails.price ?: 0.0,
                reference = productSelected.productDetails.reference ?: "",
                description = "",
                stock = 0,
                imgUrl = ""
            )
        }

        _productosSeleccionados.value = productosMap

        _comentario.value = order.comment


        // Total
        _total.value = order.totalWithTax
    }

    fun obtenerOpcionDescuento(discountApplied: Int, credit: String): String? {
        val dias = Regex("""\d+""").find(credit)?.value ?: return null

        return when {
            credit.contains("Contado", ignoreCase = true) -> "Contado $dias día${if (dias != "1") "s" else ""} ${discountApplied}% Desc."
            credit.contains("Crédito", ignoreCase = true) -> "Crédito $dias día${if (dias != "1") "s" else ""} ${discountApplied}% Desc."
            else -> null
        }
    }




}



