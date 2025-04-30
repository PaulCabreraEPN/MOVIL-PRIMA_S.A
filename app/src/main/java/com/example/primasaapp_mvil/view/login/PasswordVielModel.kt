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
class PasswordViewlModel @Inject constructor(private val repository: AuthRepository) : ViewModel() {
    var email by mutableStateOf("")
    var token by mutableStateOf("")
    var newPassword by mutableStateOf("")
    var confirmPassword by mutableStateOf("")
    val resetToken by mutableStateOf("")
    var isLoading by mutableStateOf(false)
    var success by mutableStateOf(false)

    fun requestPasswordReset(onSuccess: (String) -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            isLoading = true
            try {
                println("llegada $email")

                val response = repository.sendResetEmail(email)

                if (response.isSuccessful) {
                    val body = response.body()

                    if (body != null) {
                        val msg = body.msg

                        println("Mensaje del servidor: $msg")

                        if (msg.contains("Si existe una cuenta", ignoreCase = true)) {
                            // Mensaje genérico → tratar como error informativo
                            onError("Por favor, ingrese un correo registrado.")
                        } else {
                            // Mostrar mensaje real de éxito
                            onSuccess(msg)
                        }

                    } else {
                        onError("Respuesta vacía del servidor.")
                    }
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

    fun resetPasswordWithToken(onSuccess: () -> Unit, onError: (String) -> Unit){
        if (newPassword != confirmPassword){
            onError("Las contraseñas no coinciden")
            return
        }

        viewModelScope.launch {
            isLoading = true
            try {
                repository.resetPasswordToken(resetToken, newPassword, confirmPassword)
                success = true
                onSuccess()
            }catch (e: Exception){
                onError(e.message ?:"Ocurrió un error al enviar el correo.")
            }finally {
                isLoading = false
            }
        }
    }
}