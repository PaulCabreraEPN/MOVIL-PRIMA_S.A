package com.example.primasaapp_mvil.view.login

import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.example.primasaapp_mvil.data.repository.AuthRepository
import kotlinx.coroutines.launch
import java.lang.Error

@HiltViewModel
class PasswordViewlModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {

    var email by mutableStateOf("")
    var token by mutableStateOf("")
    var newPassword by mutableStateOf("")
    var confirmPassword by mutableStateOf("")
    var resetToken by mutableStateOf("")
    var isLoading by mutableStateOf(false)
    var success by mutableStateOf(false)

    // Manejo de estado del token con mensaje plano
    var tokenVerificationMessage by mutableStateOf<String?>(null)
    var tokenVerified by mutableStateOf(false)

    fun requestPasswordReset(onSuccess: (String) -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            isLoading = true
            try {
                val response = repository.sendResetEmail(email)
                val body = response.body()
                val msg = body?.msg.orEmpty()

                if (response.isSuccessful && !msg.contains("Si existe una cuenta", ignoreCase = true)) {
                    onSuccess(msg)
                } else {
                    onError("Por favor, ingrese un correo registrado.")
                }
            } catch (e: Exception) {
                onError(e.message ?: "Ocurrió un error al enviar el correo.")
            } finally {
                isLoading = false
            }
        }
    }

    fun verifyToken(token: String, onSuccess: (String) -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            isLoading = true
            try {
                val response = repository.verifyToken(token)
                val body = response.body()

                if (response.isSuccessful && body?.status == "success") {
                    tokenVerified = true
                    resetToken = token
                    tokenVerificationMessage = body.msg
                    onSuccess("Ya puede cambiar tu contraseña")
                } else {
                    tokenVerified = false
                    tokenVerificationMessage = body?.msg ?: "Token inválido."
                    onError("Códgo Incorrecto")
                }
            } catch (e: Exception) {
                tokenVerified = false
                tokenVerificationMessage = "Error inesperado: ${e.localizedMessage}"
            } finally {
                isLoading = false
            }
        }
    }

    fun resetPasswordWithToken(onSuccess: () -> Unit, onError: (String) -> Unit) {
        // Validar campos vacíos
        if (newPassword.isBlank() || confirmPassword.isBlank()) {
            val msg = "Los campos no pueden estar vacíos."
            println(msg)
            onError(msg)
            return
        }

        // Validar que coincidan
        if (newPassword != confirmPassword) {
            val msg = "Las contraseñas no coinciden."
            println(msg)
            onError(msg)
            return
        }

        // Validar contraseña fuerte
        val passwordRegex = Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[\\W_]).{8,}$")
        if (!passwordRegex.matches(newPassword)) {
            val msg = "La contraseña debe tener al menos 8 caracteres, una mayúscula, una minúscula, un número y un símbolo."
            println(msg)
            onError(msg)
            return
        }

        viewModelScope.launch {
            isLoading = true
            try {
                println("Enviando solicitud para cambiar la contraseña con token: $resetToken")

                println("$resetToken, $newPassword, $confirmPassword")
                val response = repository.resetPasswordToken(resetToken, newPassword, confirmPassword)
                val body = response.body()

                if (response.isSuccessful && body?.status == "success") {
                    println("Cambio de contraseña exitoso: ${body.msg}")
                    success = true
                    onSuccess()
                } else {
                    val errorMsg = body?.msg ?: "Error al cambiar la contraseña."
                    println("Error en la respuesta: $errorMsg")
                    onError(errorMsg)
                }

                println(response)

            } catch (e: Exception) {
                val exceptionMsg = e.message ?: "Ocurrió un error al cambiar la contraseña."
                println("Excepción: $exceptionMsg")
                onError(exceptionMsg)
            } finally {
                isLoading = false
                println("Proceso de cambio de contraseña finalizado.")
            }
        }
    }


}
