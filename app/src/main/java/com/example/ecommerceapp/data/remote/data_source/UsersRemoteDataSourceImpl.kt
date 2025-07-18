package com.example.ecommerceapp.data.remote.data_source

import com.example.ecommerceapp.data.model.users.LoginRequest
import com.example.ecommerceapp.data.model.users.RegisterRequest
import com.example.ecommerceapp.data.remote.UserApiService
import com.example.ecommerceapp.domain.model.User
import com.example.ecommerceapp.domain.remote.data_source.UsersRemoteDataSource
import com.example.ecommerceapp.domain.use_case.user.UpdateUserRequest
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import javax.inject.Inject

class UsersRemoteDataSourceImpl @Inject constructor(
    private val apiService: UserApiService
) : UsersRemoteDataSource {

    override suspend fun register(user: User): User {
        val response = apiService.registerUser(
            RegisterRequest(
                email = user.email,
                nationality = user.nationality,
                encryptedPassword = user.password,
                userImageUrl = user.avatar,
                fullName = "${user.name} ${user.lastName}"
            )
        )
        return response.user.toDomain()
    }

    override suspend fun login(email: String, password: String): User {
        val response = apiService.login(LoginRequest(email, password))
        return response.user.toDomain()
    }

    override suspend fun updateUser(user: User): User {
        val encodedEmail = URLEncoder.encode(user.email, StandardCharsets.UTF_8.toString())
        return apiService.updateUser(
            encodedEmail, UpdateUserRequest(
                email = user.email,
                nationality = user.nationality,
                userImageUrl = user.avatar,
                fullName = "${user.name} ${user.lastName}"
            )
        ).toDomain()
    }
}
