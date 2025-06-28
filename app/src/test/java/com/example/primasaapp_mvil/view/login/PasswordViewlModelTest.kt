package com.example.primasaapp_mvil.view.login

import org.junit.Assert.*

import com.example.primasaapp_mvil.MainDispatcherRule
import com.example.primasaapp_mvil.data.remote.RecoveryPasswordResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.*
import retrofit2.Response
import com.example.primasaapp_mvil.data.repository.AuthRepository
/*

@OptIn(ExperimentalCoroutinesApi::class)
class PasswordViewModelTest {

    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    private val repository: AuthRepository = mock()
    private lateinit var viewModel: PasswordViewlModel // Corrige el nombre si está mal

    @Before
    fun setup() {
        viewModel = PasswordViewlModel(repository)
    }

    @Test
    fun `requestPasswordReset with known email returns logical error message`() = runTest {
        // Arrange
        val email = "test@example.com"
        viewModel.email = email
        val backendMsg = "Si existe una cuenta con ese correo..."
        val expectedError = "Por favor, ingrese un correo registrado."

        whenever(repository.sendResetEmail(email)).thenReturn(
            Response.success(
                RecoveryPasswordResponse(
                    status = "error",
                    code = "401",
                    msg = backendMsg,
                    info = null
                )
            )
        )

        var successCalled = false
        var errorMsg: String? = null

        // Act
        viewModel.requestPasswordReset(
            onSuccess = { successCalled = true },
            onError = { errorMsg = it }
        )

        advanceUntilIdle()

        // Assert
        assertFalse(successCalled)
        assertEquals(expectedError, errorMsg)
        assertFalse(viewModel.isLoading)
    }


    @Test
    fun `requestPasswordReset with real success message calls onSuccess`() = runTest {
        val email = "another@example.com"
        viewModel.email = email
        val expectedSuccess = "Correo enviado correctamente"
        whenever(repository.sendResetEmail(email)).thenReturn(
            Response.success(
                RecoveryPasswordResponse(
                    status = "success",
                    code = "200",
                    msg = expectedSuccess,
                    info = null
                )
            )
        )
        var successMsg: String? = null
        var errorCalled = false

        viewModel.requestPasswordReset(
            onSuccess = { successMsg = it },
            onError = { errorCalled = true }
        )
        advanceUntilIdle()
        assertEquals(expectedSuccess, successMsg)
        assertFalse(errorCalled)
        assertFalse(viewModel.isLoading)
    }




}*/