package com.example.primasaapp_mvil.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.primasaapp_mvil.data.dataStore.DataStoreManager
import com.example.primasaapp_mvil.data.repository.AuthRepository
import com.example.primasaapp_mvil.model.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val repository: AuthRepository,
    private val dataStoreManager: DataStoreManager

): ViewModel(){
    private val _successMessage = MutableStateFlow<String?>(null)
    val successMessage: StateFlow<String?> = _successMessage
    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products : StateFlow<List<Product>> = _products
    init {
        fetchProducts()
    }
    private fun fetchProducts(){
        viewModelScope.launch {
            dataStoreManager.tokenFlow.collect{
                    token->
                if (!token.isNullOrBlank()){
                    try {
                        _products.value = repository.getProducts(token)
                        _successMessage.value = "Productos obtenidos con éxito"
                    }catch (e: Exception){
                        e.printStackTrace()
                    }
                }
            }
        }
    }


}
