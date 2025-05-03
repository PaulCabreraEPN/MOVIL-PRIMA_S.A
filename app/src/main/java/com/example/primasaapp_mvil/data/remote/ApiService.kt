package com.example.primasaapp_mvil.data.remote

import com.example.primasaapp_mvil.model.ClientResponse
import com.example.primasaapp_mvil.model.OrdersResponse
import com.example.primasaapp_mvil.model.ProductsResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @POST("login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @POST("recovery-password")
    suspend fun sendResetEmail(@Body body: EmailRequest): Response<RecoveryPasswordResponse>

    @POST("auth/reset-password/{token}")
    suspend fun resetPassword(
        @Path("token") token: String,
        @Body body: ResetPasswordRequest
    ): Response<Unit>

    @GET("clients")
    suspend fun getClients(
        @Header("Authorization") token: String
    ): ClientResponse

    @GET("products")
    suspend fun getProducts(
        @Header("Authorization") token: String
    ): ProductsResponse

    @GET("orders")
    suspend fun  getOrders(
        @Header("Authorization") token: String
    ): OrdersResponse
}