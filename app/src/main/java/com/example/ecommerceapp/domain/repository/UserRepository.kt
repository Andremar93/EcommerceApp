package com.example.ecommerceapp.domain.repository

import com.example.ecommerceapp.domain.model.User
import com.example.ecommerceapp.domain.use_case.user.LoginResult
import com.example.ecommerceapp.domain.use_case.user.RegisterResult
import com.example.ecommerceapp.domain.use_case.user.UpdateUserResult

interface UserRepository {

    suspend fun registerUser(
        user: User
    ): RegisterResult

    suspend fun login(email: String, password: String): LoginResult

    suspend fun loginOffline(email: String, password: String): Boolean

    suspend fun getActiveUser(): User

    suspend fun updateUser(user: User): UpdateUserResult

    suspend fun logoutUser(userId: String): Boolean
}