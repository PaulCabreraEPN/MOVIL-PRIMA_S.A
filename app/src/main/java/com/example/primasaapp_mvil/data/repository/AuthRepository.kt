package com.example.primasaapp_mvil.data.repository

import com.example.primasaapp_mvil.data.remote.ApiService
import com.example.primasaapp_mvil.data.remote.EmailRequest
import com.example.primasaapp_mvil.data.remote.LoginRequest
import com.example.primasaapp_mvil.data.remote.LoginResponse
import com.example.primasaapp_mvil.data.remote.RecoveryPasswordResponse
import com.example.primasaapp_mvil.data.remote.ResetPasswordRequest
import com.example.primasaapp_mvil.model.Client
import retrofit2.Response
import javax.inject.Inject

class AuthRepository @Inject constructor(private val api:ApiService){

    //Para Login
    suspend fun login (username: String, password: String): Response<LoginResponse>{
        return api.login(LoginRequest(username, password))
    }

    //Para recuperar contraseña

    suspend fun sendResetEmail(email: String): Response<RecoveryPasswordResponse> {
        return api.sendResetEmail(EmailRequest(email))
    }

    suspend fun resetPasswordToken(token: String, password: String, confirmpassword: String){
        val response = api.resetPassword(token, ResetPasswordRequest(password, confirmpassword))
        if (!response.isSuccessful){
            throw Exception("No se pudo restablecer la contraseña")
        }
    }

    //Para cargar clientes
    suspend fun getClients(token: String): List<Client> {
        val response = api.getClients("Bearer $token")
        return response.data
    }
}
