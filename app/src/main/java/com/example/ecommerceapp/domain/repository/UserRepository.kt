package com.example.ecommerceapp.domain.repository

import com.example.ecommerceapp.domain.model.User

interface UserRepository {
    fun getUserInfo(): User
    suspend fun registerUser(user: User): Boolean
    suspend fun login(email: String, password: String): Boolean
}
