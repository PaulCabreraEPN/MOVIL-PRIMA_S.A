package com.example.primasaapp_mvil.model

import com.example.primasaapp_mvil.data.remote.LoginData
import kotlinx.serialization.Serializable

@Serializable
data class Seller(
    val _id: String,
    val username: String,
    val email: String,
    val names: String,
    val lastNames: String,
    val role: String,
    val SalesCity: String,
    val cedula: Int,
    val PhoneNumber: String
)

data class SellerUpdate(
    val email: String,
    val PhoneNumber: String
)

data class ProfileResponse(
    val status: String,
    val code: String,
    val data: Seller,
    val info: UpdateInfo,
)


data class UpdateInfo(
    val updatedFields: List<String>
)