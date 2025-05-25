package com.example.primasaapp_mvil.model

data class ClientRequest(
    val Name: String,
    val ComercialName: String,
    val Ruc: String,
    val Address: String,
    val telephone: String,
    val email: String,
    val state: String
)

data class ClientUpdate(
    val Address: String,
    val telephone: String,
    val email: String,
    val state: String
)

data class ClientResponse(
    val stattus: String,
    val code: String,
    val msg: String,
    val data: List<Client>
)

data class ClientRegisterResponse(
    val status: String,
    val code: String,
    val msg: String,
    val data: Client
)

data class Client (
    val id: String,
    val Ruc: Long,
    val telephone: Long,
    val email: String,
    val credit: String,
    val state: String,
    val Address: String,
    val Name: String,
    val ComercialName: String,
)

data class ErrorResponse(
    val status: String,
    val code: String,
    val msg: String
)
