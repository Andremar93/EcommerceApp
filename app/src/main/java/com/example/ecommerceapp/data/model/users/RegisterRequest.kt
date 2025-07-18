package com.example.ecommerceapp.data.model.users

data class RegisterRequest(
    val email: String,
    val encryptedPassword: String,
    val fullName: String,
    val userImageUrl: String,
    val nationality: String
)