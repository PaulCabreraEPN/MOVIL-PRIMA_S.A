package com.example.primasaapp_mvil.data.repository
import com.example.primasaapp_mvil.data.dataStore.DataStoreManager
import com.example.primasaapp_mvil.data.remote.ApiService
import com.example.primasaapp_mvil.model.Product
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class ProductRepository @Inject constructor(
    private val apiService: ApiService,
    private val dataStoreManager: DataStoreManager
) {
    suspend fun getAllProducts(): List<Product> {
        val allProducts = mutableListOf<Product>()

        val tokenValue = dataStoreManager.tokenFlow.first()
        val token = "Bearer $tokenValue"

        var currentPage = 1
        var totalPages: Int

        do {
            val response = apiService.getProducts(token, currentPage)
            allProducts.addAll(response.data)
            totalPages = response.info.totalPages
            currentPage++
        } while (currentPage <= totalPages)

        return allProducts
    }
}

