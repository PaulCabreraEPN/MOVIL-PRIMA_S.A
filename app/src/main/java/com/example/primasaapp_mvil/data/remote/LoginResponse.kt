package com.example.primasaapp_mvil.data.remote

import com.example.primasaapp_mvil.model.Seller
import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    val status: String,
    val code: String,
    val msg: String,
    val data: LoginData
)

@Serializable
data class LoginData(
    val seller: Seller,
    val token: String
)
