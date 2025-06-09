package com.example.primasaapp_mvil.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.primasaapp_mvil.data.dataStore.DataStoreManager
import com.example.primasaapp_mvil.data.repository.AuthRepository
import com.example.primasaapp_mvil.model.Client
import com.example.primasaapp_mvil.model.ClientRequest
import com.example.primasaapp_mvil.model.ClientRegisterResponse
import com.example.primasaapp_mvil.model.ClientUpdate
import com.example.primasaapp_mvil.model.ErrorResponse
import com.example.primasaapp_mvil.model.OrderDatabyID
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ClientViewModel @Inject constructor(
    private val repository: AuthRepository,
    private val dataStoreManager: DataStoreManager
) : ViewModel() {

    private val _clients = MutableStateFlow<List<Client>>(emptyList())
    val clients: StateFlow<List<Client>> = _clients

    private val _registerResult = MutableStateFlow<Result<String>?>(null)
    val registerResult: StateFlow<Result<String>?> = _registerResult

    private val _selectedClient = MutableStateFlow<Client?>(null)
    val selectedClient: StateFlow<Client?> = _selectedClient

    init {
        fetchClients()
    }

    val isLoadingClient = MutableStateFlow(false)
    val isLoading = MutableStateFlow(false)

    private fun fetchClients() {
        viewModelScope.launch {

            dataStoreManager.tokenFlow.collect { token ->
                if (!token.isNullOrBlank()) {
                    try {
                        val response = repository.getClients(token) // Debe devolver List<Client>
                        _clients.value = response
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
        }
    }

    fun registerClient(client: ClientRequest) {
        viewModelScope.launch {
            isLoading.value = true
            try {
                val token = dataStoreManager.tokenFlow.first()
                if (!token.isNullOrBlank()) {
                    val response = repository.registerClient(token, client)
                    if (response.isSuccessful) {
                        val body = response.body()
                        if (body != null) {
                            _registerResult.value = Result.success("Cliente registrado: ${body.data.Name}")
                            fetchClients() // Refrescar lista después del registro
                        } else {
                            _registerResult.value = Result.failure(Exception("Respuesta vacía del servidor"))
                        }
                    } else {
                        val errorBody = response.errorBody()?.string()
                        val errorMessage = try {
                            val errorResponse = Gson().fromJson(errorBody, ErrorResponse::class.java)
                            errorResponse.msg
                        } catch (e: Exception) {
                            "Error en la solicitud: ${response.code()} - ${response.message()}"
                        }
                        _registerResult.value = Result.failure(Exception(errorMessage))
                    }
                } else {
                    _registerResult.value = Result.failure(Exception("Token no disponible"))
                }
            } catch (e: Exception) {
                val fullMessage = "Excepción: ${e::class.java.simpleName} - ${e.message}"
                _registerResult.value = Result.failure(Exception(fullMessage, e))
            } finally {
                isLoading.value = false
            }
        }
    }

    fun fetchClientById(id: String) {
        viewModelScope.launch {
            isLoadingClient.value = true
            try {
                val token = dataStoreManager.tokenFlow.first()
                if (!token.isNullOrBlank()) {
                    val response = repository.getClientById(id, token)
                    println("Buscando Cliente con ID: $id")

                    if (response.isSuccessful) {
                        val clientResponse = response.body()
                        if (clientResponse != null) {
                            _selectedClient.value = clientResponse.data
                            println("Cliente obtenido: ${clientResponse.data}")
                        } else {
                            println("Cliente no encontrado o sin datos.")
                        }
                    } else {
                        println("Error al obtener Cliente: ${response.code()} - ${response.message()}")
                    }
                } else {
                    println("Token no disponible.")
                }
            } catch (e: Exception) {
                println("Excepción al obtener cliente por ID: ${e.message}")
                e.printStackTrace()
            }finally {
                isLoadingClient.value = false
            }
        }
    }

    fun updateClient(id: String, client: ClientUpdate) {
        viewModelScope.launch {
            isLoading.value = true
            try {
                val token = dataStoreManager.tokenFlow.first()
                if (!token.isNullOrBlank()) {
                    val response = repository.updateClient(id, token, client)
                    if (response.isSuccessful) {
                        val body = response.body()
                        if (body != null) {
                            _registerResult.value = Result.success("Cliente actualizado con éxito")
                            fetchClients()
                        } else {
                            _registerResult.value = Result.failure(Exception("Respuesta vacía del servidor"))
                        }
                    } else {
                        val errorBody = response.errorBody()?.string()
                        val errorMessage = try {
                            val errorResponse = Gson().fromJson(errorBody, ErrorResponse::class.java)
                            errorResponse.msg
                        } catch (e: Exception) {
                            "Error en la solicitud: ${response.code()} - ${response.message()}"
                        }
                        _registerResult.value = Result.failure(Exception(errorMessage))
                    }
                } else {
                    _registerResult.value = Result.failure(Exception("Token no disponible"))
                }
            } catch (e: Exception) {
                val fullMessage = "Excepción: ${e::class.java.simpleName} - ${e.message}"
                _registerResult.value = Result.failure(Exception(fullMessage, e))
            }finally {
                isLoading.value = false
            }

            println(_registerResult.value)
        }
    }

    fun clearRegisterResult() {
        _registerResult.value = null
    }




}
