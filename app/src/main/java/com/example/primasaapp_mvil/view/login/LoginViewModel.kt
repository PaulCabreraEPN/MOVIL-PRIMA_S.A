package com.example.primasaapp_mvil.view.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.primasaapp_mvil.data.dataStore.DataStoreManager
import com.example.primasaapp_mvil.data.remote.LoginResponse
import com.example.primasaapp_mvil.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: AuthRepository,
    private val dataStoreManger: DataStoreManager
) : ViewModel() {
    var loginState by mutableStateOf<Result<LoginResponse>?>(null)
        private set
    fun login(username: String, password: String) {
        viewModelScope.launch {
            try {
                val response = repository.login(username, password)
                if (response.isSuccessful) {
                    val data = response.body()?.data
                    loginState = Result.success(response.body()!!)
                    data?.let {
                        val name = it.seller.names + " " + it.seller.lastNames
                        dataStoreManger.saveToken(it.token)
                        dataStoreManger.saveUsername(it.seller.username)
                        dataStoreManger.saveEmail(it.seller.email)
                        dataStoreManger.saveSalesCity(it.seller.SalesCity)
                        dataStoreManger.saveName(name)
                    }
                } else {
                    loginState = Result.failure(Exception("Credenciales inválidas"))
                }
            }catch (e:Exception){
                loginState = Result.failure(e)
            }
        }
    }
}