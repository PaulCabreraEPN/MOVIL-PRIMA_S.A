package com.example.primasaapp_mvil.data.remote

data class RecoveryPasswordResponse(
    val status: String,
    val code: String,
    val msg: String,
    val info: Info? = null
)

data class Info(
    val emailDetails: EmailDetails? = null
)

data class EmailDetails(
    val sent: Boolean,
    val message: String
)

data class passwordReset(
    val paswword: String,
    val confirmpass: String
)

