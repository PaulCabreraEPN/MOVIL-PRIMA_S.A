package com.example.primasaapp_mvil.data.remote

import com.example.primasaapp_mvil.model.ClientRegisterResponse
import com.example.primasaapp_mvil.model.ClientRequest
import com.example.primasaapp_mvil.model.ClientResponse
import com.example.primasaapp_mvil.model.ClientUpdate
import com.example.primasaapp_mvil.model.Order
import com.example.primasaapp_mvil.model.OrderResponsetoID
import com.example.primasaapp_mvil.model.OrderResponsetoSend
import com.example.primasaapp_mvil.model.OrderToSend
import com.example.primasaapp_mvil.model.OrdersResponse
import com.example.primasaapp_mvil.model.ProductResponse
import com.example.primasaapp_mvil.model.ProfileResponse
import com.example.primasaapp_mvil.model.Seller
import com.example.primasaapp_mvil.model.SellerUpdate
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @POST("login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @POST("recovery-password")
    suspend fun sendResetEmail(@Body body: EmailRequest): Response<RecoveryPasswordResponse>

    @GET("recovery-password/{token}")
    suspend fun verifyToken(
        @Path("token") token: String,
    ): Response<RecoveryPasswordResponse>

    @POST("recovery-password/{token}")
    suspend fun resetPassword(
        @Path("token") token: String,
        @Body body: ResetPasswordRequest
    ): Response<RecoveryPasswordResponse>

    @PATCH("updateMyProfile/{id}")
    suspend fun updateProfile(
        @Path("id") id: String,
        @Header("Authorization") token: String,
        @Body sellerUpdate: SellerUpdate
    ):  Response<ProfileResponse>

    @GET("clients")
    suspend fun getClients(
        @Header("Authorization") token: String
    ): ClientResponse

    @GET("clients/{id}")
    suspend fun getClientById(
        @Path("id") id: String,
        @Header("Authorization") token: String,
    ): Response<ClientRegisterResponse>

    @POST("clients/register")
    suspend fun registerClient(
        @Header("Authorization") token: String,
        @Body client: ClientRequest
    ): Response<ClientRegisterResponse>

    @PATCH("clients/update/{id}")
    suspend fun updateClient(
        @Path("id") id: String,
        @Header("Authorization") token: String,
        @Body client: ClientUpdate
    ): Response<ClientRegisterResponse>

    @GET("products")
    suspend fun getProducts(
        @Header("Authorization") token: String,
    ): ProductResponse

    @GET("orders")
    suspend fun  getOrders(
        @Header("Authorization") token: String
    ): OrdersResponse

    @GET("orders/{id}")
    suspend fun getOrderById(
        @Path("id") id: String,
        @Header("Authorization") token: String,
    ): Response<OrderResponsetoID>

    @PATCH("orders/update/{id}")
    suspend fun updateOrder(
        @Path("id") id: String,
        @Header("Authorization") token: String,
        @Body order: OrderToSend
    ): Response<OrderResponsetoSend>

    @POST("orders/create")
    suspend fun registerOrder(
        @Header("Authorization") token: String,
        @Body order: OrderToSend
    ): Response<OrderResponsetoSend>

    @DELETE("orders/{id}")
    suspend fun deleteOrder(
        @Path("id") id: String,
        @Header("Authorization") token: String
    )

}