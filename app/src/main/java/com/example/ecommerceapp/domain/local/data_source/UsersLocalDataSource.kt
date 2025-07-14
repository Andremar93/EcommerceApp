package com.example.ecommerceapp.domain.local.data_source

import com.example.ecommerceapp.domain.model.User

interface UsersLocalDataSource {
    suspend fun insertUser(user: User)
    suspend fun getUserByEmail(email: String): User?
    suspend fun setActiveUser(id: String)
    suspend fun getActiveUser(): User?
    suspend fun updateUser(user: User)
}