package com.example.primasaapp_mvil.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.primasaapp_mvil.data.dataStore.DataStoreManager
import com.example.primasaapp_mvil.data.repository.AuthRepository
import com.example.primasaapp_mvil.model.Order
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(
    private val repository: AuthRepository,
    private val dataStoreManager: DataStoreManager
): ViewModel() {
    private val _orders = MutableStateFlow<List<Order>>(emptyList())
    val orders : StateFlow<List<Order>> = _orders
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


}