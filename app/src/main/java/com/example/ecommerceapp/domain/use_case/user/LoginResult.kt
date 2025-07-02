package com.example.ecommerceapp.domain.use_case.user

sealed class LoginResult {
    object Success : LoginResult()
    data class Error(val message: String) : LoginResult()
}
