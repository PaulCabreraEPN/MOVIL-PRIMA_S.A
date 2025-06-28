package com.example.primasaapp_mvil.data.repository

import com.example.primasaapp_mvil.data.remote.ApiService
import com.example.primasaapp_mvil.data.remote.EmailRequest
import com.example.primasaapp_mvil.data.remote.LoginRequest
import com.example.primasaapp_mvil.data.remote.LoginResponse
import com.example.primasaapp_mvil.data.remote.RecoveryPasswordResponse
import com.example.primasaapp_mvil.data.remote.ResetPasswordRequest
import com.example.primasaapp_mvil.data.remote.passwordReset
import com.example.primasaapp_mvil.model.Client
import com.example.primasaapp_mvil.model.ClientRegisterResponse
import com.example.primasaapp_mvil.model.ClientRequest
import com.example.primasaapp_mvil.model.ClientUpdate
import com.example.primasaapp_mvil.model.Order
import com.example.primasaapp_mvil.model.OrderResponsetoID
import com.example.primasaapp_mvil.model.OrderResponsetoSend
import com.example.primasaapp_mvil.model.OrderToSend
import com.example.primasaapp_mvil.model.Product
import com.example.primasaapp_mvil.model.ProductResponse
import com.example.primasaapp_mvil.model.ProfileResponse
import com.example.primasaapp_mvil.model.SellerUpdate
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

    //Verificar el token
    suspend fun verifyToken(token: String): Response<RecoveryPasswordResponse> {
        return api.verifyToken(token)
    }

    suspend fun resetPasswordToken(token: String, password: String, confirmpassword: String): Response<RecoveryPasswordResponse> {
        val passwords = ResetPasswordRequest(
            password = password,
            confirmpassword = confirmpassword
        )
        return api.resetPassword(token, passwords)
    }

    //Actualizar información Perfil
    suspend fun updateProfile(id: String, token: String, data:SellerUpdate): Response<ProfileResponse>{
        return api.updateProfile(id, "Bearer $token", data)
    }

    //Traer clientes
    suspend fun getClients(token: String): List<Client> {
        val response = api.getClients("Bearer $token")
        return response.data
    }

    //Traer Order por ID
    suspend fun getClientById(id: String, token: String): Response<ClientRegisterResponse> {
        return api.getClientById(id,"Bearer $token")
    }

    //Resgistrar Clientes
    suspend fun registerClient(token: String, client: ClientRequest): Response<ClientRegisterResponse>{
        return api.registerClient("Bearer $token", client)
    }
    //Actualizar Clientes
    suspend fun updateClient(id: String, token: String, client: ClientUpdate): Response<ClientRegisterResponse>{
        return api.updateClient(id ,"Bearer $token", client)
    }

    //Traer Ordenes
    suspend fun getOrders(token: String): List<Order>{
        val response = api.getOrders("Bearer $token")
        return response.data
    }

    //Traer Order por ID
    suspend fun getOrderById(id: String, token: String): Response<OrderResponsetoID> {
        return api.getOrderById(id,"Bearer $token")
    }

    //Traer Productos
    suspend fun getProducts(token: String): List<Product> {
        val response = api.getProducts("Bearer $token")
        return  response.data
    }

    //Registrar Orden
    suspend fun registrerOrder(token: String, order: OrderToSend): Response<OrderResponsetoSend>{
        return api.registerOrder("Bearer $token", order)
    }

    //Actualizar Orden
    suspend fun updateOrder(id: String, token: String, order: OrderToSend): Response<OrderResponsetoSend>{
        return api.updateOrder(id, "Bearer $token", order)
    }

    //Eliminar Orden
    suspend fun deleteOrder(id: String, token: String): Response<OrderResponsetoSend> {
        return api.deleteOrder(id, "Bearer $token")
    }


}
