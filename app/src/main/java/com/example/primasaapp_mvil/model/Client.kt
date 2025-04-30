package com.example.primasaapp_mvil.model

data class ClientResponse(
    val stattus: String,
    val code: String,
    val msg: String,
    val data: List<Client>
)

data class Client (
    val id: String,
    val Ruc: Long,
    val telephone: Long,
    val email: String,
    val credit: String,
    val state: String,
    val Adress: String,
    val Name: String
)
