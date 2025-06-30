package com.example.ecommerceapp.domain.model

data class User(
    val name: String = "",
    val lastName: String = "",
    val email: String = "",
    val nationality: String = "",
    val avatar: String = "",
    val password: String = ""
)

data class RegisterRequest(
    val name: String,
    val lastName: String,
    val email: String,
    val nationality: String,
    val encryptedPassword: String
)
