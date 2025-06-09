package com.example.primasaapp_mvil.view.modules

import com.example.primasaapp_mvil.MainDispatcherRule
import com.example.primasaapp_mvil.data.dataStore.DataStoreManager
import com.example.primasaapp_mvil.data.repository.AuthRepository
import com.example.primasaapp_mvil.model.Product
import com.example.primasaapp_mvil.viewmodel.ProductViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class ProductViewModelTest {

    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    private val repository: AuthRepository = Mockito.mock()
    private val dataStoreManager: DataStoreManager = Mockito.mock()
    private lateinit var viewModel: ProductViewModel

    private val fakeToken = "fake_token"
    private val fakeProducts = listOf(
        Product(
            id = 1,
            reference = "REF001",
            product_name = "Producto 1",
            description = "Descripción del producto 1",
            price = 10.5,
            stock = 100,
            imgUrl = "http://example.com/image1.jpg"
        ),
        Product(
            id = 2,
            reference = "REF002",
            product_name = "Producto 2",
            description = "Descripción del producto 2",
            price = 20.0,
            stock = 50,
            imgUrl = "http://example.com/image2.jpg"
        )
    )

    @Before
    fun setup() {
        // Mockeamos el tokenFlow para que emita un token válido
        whenever(dataStoreManager.tokenFlow).thenReturn(flowOf(fakeToken))
        // Mockeamos getProducts para devolver la lista falsa
        runBlocking {
            whenever(repository.getProducts(fakeToken)).thenReturn(fakeProducts)
        }

        viewModel = ProductViewModel(repository, dataStoreManager)
    }

    @Test
    fun `fetchProducts sets products from repository when token is valid`() = runTest {
        val result = viewModel.products.first()
        assertEquals(fakeProducts, result)

    }

    @Test
    fun `successMessage is emitted after fetching products`() = runTest {
        val message = viewModel.successMessage.first { it != null }
        println("Productos obtenidos Y cargados con éxito")
    }

}