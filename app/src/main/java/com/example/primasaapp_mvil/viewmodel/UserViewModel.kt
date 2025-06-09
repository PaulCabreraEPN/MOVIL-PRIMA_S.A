package com.example.primasaapp_mvil.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.primasaapp_mvil.data.dataStore.DataStoreManager
import com.example.primasaapp_mvil.data.repository.AuthRepository
import com.example.primasaapp_mvil.model.ErrorResponse
import com.example.primasaapp_mvil.model.SellerUpdate
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val repository: AuthRepository,
    private val dataStoreManager: DataStoreManager
) : ViewModel() {

    private val _updateResult = MutableStateFlow<Result<String>?>(null)
    val updateResult: StateFlow<Result<String>?> = _updateResult

    val isLoading = MutableStateFlow(false)

    val Name = dataStoreManager.nameFlow.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        ""
    )

    val email = dataStoreManager.emailFlow.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        ""
    )

    val phone = dataStoreManager.phoneFlow.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        ""
    )

    val sales_CITY = dataStoreManager.salesCityFlow.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        ""
    )

    val username = dataStoreManager.usernameFlow.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        ""
    )

    val cedula = dataStoreManager.clFlow.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        0
    )

    fun updateProfile(data: SellerUpdate){
        viewModelScope.launch {
            isLoading.value=true
            try {
                val token = dataStoreManager.tokenFlow.first()
                val id = dataStoreManager.idFlow.first()
                if (!token.isNullOrBlank()){
                    val response = repository.updateProfile(id, token, data)
                    if (response.isSuccessful){
                        val body = response.body()
                        if (body != null) {
                            _updateResult.value = Result.success("Perfil actualizado correctamente")
                            dataStoreManager.savePhone(data.PhoneNumber)
                            dataStoreManager.saveEmail(data.email)

                        } else {
                            _updateResult.value = Result.failure(Exception("Respuesta vacía del servidor"))
                        }
                    }else{
                        val errorBody = response.errorBody()?.string()
                        val errorMessage = try {
                            val errorResponse = Gson().fromJson(errorBody, ErrorResponse::class.java)
                            errorResponse.msg
                        } catch (e: Exception) {
                            "Error en la solicitud: ${response.code()} - ${response.message()}"
                        }
                        _updateResult.value = Result.failure(Exception(errorMessage))
                    }
                }else{
                    _updateResult.value = Result.failure(Exception("Token no disponible"))
                }
            }catch (e: Exception){
                val fullMessage = "Excepción: ${e::class.java.simpleName} - ${e.message}"
                _updateResult.value = Result.failure(Exception(fullMessage, e))
            }finally {
                isLoading.value = false
                _updateResult.value = null
            }

        }
    }

}
