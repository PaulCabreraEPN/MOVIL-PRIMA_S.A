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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.first
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(
    private val repository: AuthRepository,
    private val dataStoreManager: DataStoreManager
) : ViewModel() {

    private val _orders = MutableStateFlow<List<Order>>(emptyList())
    val orders: StateFlow<List<Order>> = _orders

    private val _selectedOrder = MutableStateFlow<OrderDatabyID?>(null)
    val selectedOrder: StateFlow<OrderDatabyID?> = _selectedOrder

    private val _registerResult = MutableStateFlow("")
    val registerResult: StateFlow<String> = _registerResult.asStateFlow()

    private val _productosSeleccionados = MutableStateFlow<Map<Int, Int>>(emptyMap()) // id -> cantidad
    val productosSeleccionados: StateFlow<Map<Int, Int>> = _productosSeleccionados

    private val _productosSeleccionadosData = mutableMapOf<Int, Product>() // id -> producto

    private val _selectedClient = MutableStateFlow<Client?>(null)
    val selectedClient: StateFlow<Client?> = _selectedClient

    private val _selectedDiscount = MutableStateFlow<String?>(null)
    val selectedDiscount: StateFlow<String?> = _selectedDiscount

    private val _total = MutableStateFlow(0.0)
    val total: StateFlow<Double> get() = _total

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
                    _orders.value = orders.filter { it.seller.email == email }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    fun registerOrder(order: OrderToSend) {
        viewModelScope.launch {
            try {
                val token = dataStoreManager.tokenFlow.first()
                if (!token.isNullOrBlank()) {
                    val response = repository.registrerOrder(token, order)

                    if (response.isSuccessful) {
                        val message = response.body()?.msg ?: "Orden registrada sin mensaje."
                        _registerResult.value = message
                    } else {
                        val errorCode = response.code()
                        val errorBody = response.errorBody()?.string()
                        val fullError = """
                        Error al registrar orden.
                        Código: $errorCode
                        Cuerpo del error: ${errorBody ?: "Sin detalles"}
                    """.trimIndent()
                        _registerResult.value = fullError
                        println(fullError)
                    }
                } else {
                    _registerResult.value = "Token no disponible."
                }
            } catch (e: Exception) {
                val detailedError = """
                Excepción al registrar orden.
                Mensaje: ${e.message}
                Tipo: ${e::class.simpleName}
            """.trimIndent()
                _registerResult.value = detailedError
                e.printStackTrace()
            }
            println("Resultado: ${_registerResult.value}")
        }
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
        viewModelScope.launch {
            try {
                val token = dataStoreManager.tokenFlow.first()
                if (!token.isNullOrBlank()) {
                    val response = repository.getOrderById(id, token)
                    println("Buscando orden con ID: $id")

                    if (response.isSuccessful) {
                        val orderResponse = response.body()
                        if (orderResponse != null) {
                            _selectedOrder.value = orderResponse.data
                            println("Orden obtenida: ${orderResponse.data}")
                        } else {
                            println("Orden no encontrada o sin datos.")
                        }
                    } else {
                        println("Error al obtener orden: ${response.code()} - ${response.message()}")
                    }
                } else {
                    println("Token no disponible.")
                }
            } catch (e: Exception) {
                println("Excepción al obtener orden por ID: ${e.message}")
                e.printStackTrace()
            }
        }
    }

    fun setSelectedClient(client: Client) {
        _selectedClient.value = client
    }

    fun setSelectedDiscount(discount: String) {
        _selectedDiscount.value = discount
    }


    fun limpiarProductos() {
        _productosSeleccionados.value = emptyMap()
        _productosSeleccionadosData.clear()
        _total.value = 0.0
    }
}



