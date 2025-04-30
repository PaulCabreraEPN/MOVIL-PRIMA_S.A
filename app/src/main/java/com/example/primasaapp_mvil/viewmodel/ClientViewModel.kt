package com.example.primasaapp_mvil.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.primasaapp_mvil.data.dataStore.DataStoreManager
import com.example.primasaapp_mvil.data.repository.AuthRepository
import com.example.primasaapp_mvil.model.Client
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ClientViewModel @Inject constructor(
    private val repository: AuthRepository,
    private val dataStoreManager: DataStoreManager
) : ViewModel() {
    private val _clients = MutableStateFlow<List<Client>>(emptyList())
    val clients : StateFlow<List<Client>> = _clients
    init {
        fetchClients()
    }
    private fun fetchClients(){
        viewModelScope.launch {
            dataStoreManager.tokenFlow.collect{
                token ->
                if (!token.isNullOrBlank()){
                    try {
                        _clients.value = repository.getClients(token)
                    }catch (e: Exception){
                        e.printStackTrace()
                    }
                }
            }
        }
    }
}