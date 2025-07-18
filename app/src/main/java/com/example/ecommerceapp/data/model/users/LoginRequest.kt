package com.example.ecommerceapp.data.model.users

data class LoginRequest(
    val email: String,
    val encryptedPassword: String
)