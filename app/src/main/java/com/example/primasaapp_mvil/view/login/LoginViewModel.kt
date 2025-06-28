package com.example.primasaapp_mvil.view.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.primasaapp_mvil.data.dataStore.DataStoreManager
import com.example.primasaapp_mvil.data.remote.LoginResponse
import com.example.primasaapp_mvil.data.repository.AuthRepository
import com.example.primasaapp_mvil.model.Client
import com.example.primasaapp_mvil.model.Seller
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: AuthRepository,
    private val dataStoreManager: DataStoreManager
) : ViewModel() {

    // Estado del login
    var loginState by mutableStateOf<Result<LoginResponse>?>(null)
        private set

    val isLoading = MutableStateFlow(false)

    fun login(username: String, password: String) {
        viewModelScope.launch {
            isLoading.value = true
            try {
                val response = repository.login(username, password)
                if (response.isSuccessful) {
                    val loginData = response.body()?.data
                    val seller = loginData?.seller
                    loginState = Result.success(response.body()!!)
                    isLoading.value = false

                    if (loginData != null && seller != null) {

                        val fullName = "${seller.names} ${seller.lastNames}"
                        dataStoreManager.saveToken(loginData.token)
                        dataStoreManager.saveid(seller._id)
                        dataStoreManager.saveUsername(seller.username)
                        dataStoreManager.saveEmail(seller.email)
                        dataStoreManager.saveSalesCity(seller.SalesCity)
                        dataStoreManager.saveCL(seller.cedula)
                        dataStoreManager.saveName(fullName)
                        dataStoreManager.savePhone(seller.PhoneNumber)

                    }
                } else {
                    loginState = Result.failure(Exception("Credenciales inválidas"))
                }
            } catch (e: Exception) {
                loginState = Result.failure(e)
            } finally {
                isLoading.value = false
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            try {
                // Limpiar datos del usuario
                dataStoreManager.clearAll()

                // Resetear el estado de login (opcional, depende de cómo lo uses en la UI)
                loginState = Result.failure(Exception("Sesión cerrada"))

            } catch (e: Exception) {
                // Manejar errores si ocurren al limpiar datos
                loginState = Result.failure(e)
            }
        }
    }

}
