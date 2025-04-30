package com.example.primasaapp_mvil.model

import kotlinx.serialization.Serializable

@Serializable
data class Seller(
    val _id: String,
    val username: String,
    val email: String,
    val names: String,
    val lastNames: String,
    val role: String,
    val SalesCity: String
)
