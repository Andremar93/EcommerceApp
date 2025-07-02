package com.example.ecommerceapp.domain.repository

import com.example.ecommerceapp.domain.model.User
import com.example.ecommerceapp.domain.use_case.user.LoginResult
import com.example.ecommerceapp.domain.use_case.user.RegisterResult

interface UserRepository {

    fun getUserInfo(): User

    suspend fun registerUser(
        user: User
    ): RegisterResult

    suspend fun login(email: String, password: String): LoginResult
}
