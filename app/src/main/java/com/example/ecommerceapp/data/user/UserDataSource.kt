package com.example.ecommerceapp.data.user

import com.example.ecommerceapp.domain.model.User

interface UserDataSource {
    fun getUserInfo(): User

    suspend fun registerUser(
        user: User
    ): Boolean

    suspend fun login(email: String, password: String): Boolean

}