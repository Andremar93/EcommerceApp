package com.example.ecommerceapp.domain.use_case.user

sealed class RegisterResult {
    object Success : RegisterResult()
    data class Error(val message: String) : RegisterResult()
}
