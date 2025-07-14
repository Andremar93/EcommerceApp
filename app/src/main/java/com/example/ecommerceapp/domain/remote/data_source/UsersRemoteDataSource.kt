package com.example.ecommerceapp.domain.remote.data_source

import com.example.ecommerceapp.domain.model.User

interface UsersRemoteDataSource {
    suspend fun register(user: User): User
    suspend fun login(email: String, password: String): User
    suspend fun updateUser(user: User): User
}