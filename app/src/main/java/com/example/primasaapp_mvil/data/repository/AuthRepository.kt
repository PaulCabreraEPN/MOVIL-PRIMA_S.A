package com.example.primasaapp_mvil.data.repository

import com.example.primasaapp_mvil.data.remote.ApiService
import com.example.primasaapp_mvil.data.remote.EmailRequest
import com.example.primasaapp_mvil.data.remote.LoginRequest
import com.example.primasaapp_mvil.data.remote.LoginResponse
import com.example.primasaapp_mvil.data.remote.RecoveryPasswordResponse
import com.example.primasaapp_mvil.data.remote.ResetPasswordRequest
import com.example.primasaapp_mvil.model.Client
import com.example.primasaapp_mvil.model.Product
import com.example.primasaapp_mvil.model.Order
import retrofit2.Response
import javax.inject.Inject

class AuthRepository @Inject constructor(private val api:ApiService){

    //Login
    suspend fun login (username: String, password: String): Response<LoginResponse>{
        return api.login(LoginRequest(username, password))
    }

    //Recuperar contraseña
    suspend fun sendResetEmail(email: String): Response<RecoveryPasswordResponse> {
        return api.sendResetEmail(EmailRequest(email))
    }

    suspend fun resetPasswordToken(token: String, password: String, confirmpassword: String){
        val response = api.resetPassword(token, ResetPasswordRequest(password, confirmpassword))
        if (!response.isSuccessful){
            throw Exception("No se pudo restablecer la contraseña")
        }
    }

    //Traer clientes
    suspend fun getClients(token: String): List<Client> {
        val response = api.getClients("Bearer $token")
        return response.data
    }

    //Traer productos
    suspend fun getProducts(token: String): List<Product> {
        val response = api.getProducts("Bearer $token")
        return  response.data
    }

    //Traer Ordenes
    suspend fun getOrders(token: String): List<Order>{
        val response = api.getOrders("Bearer $token")
        return response.data
    }
}
