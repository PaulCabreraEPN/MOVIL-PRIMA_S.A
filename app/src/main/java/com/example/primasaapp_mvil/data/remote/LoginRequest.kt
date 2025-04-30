package com.example.primasaapp_mvil.data.remote

data class LoginRequest (
    val username: String,
    val password: String
)

data class EmailRequest (
    val email: String
)

data class ResetPasswordRequest (
    val password: String,
    val confirmpassword: String
)
