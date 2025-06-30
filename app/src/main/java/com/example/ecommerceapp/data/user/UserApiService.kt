package com.example.ecommerceapp.data.user

import com.example.ecommerceapp.domain.model.User
import retrofit2.http.Body
import retrofit2.http.POST

data class LoginRequest(
    val email: String,
    val encryptedPassword: String
)

data class LoginResponse(
    val message: String,
    val user: User
)

data class RegisterResponse(
    val message: String,
    val user: User
)

data class RegisterRequest(
    val email: String,
    val encryptedPassword: String,
    val fullName: String,
    val userImageUrl: String,
    val nationality: String
)

interface UserApiService {
    @POST("users/login")
    suspend fun login(@Body loginRequest: LoginRequest): LoginResponse

    @POST("users/register")
    suspend fun registerUser(@Body registerRequest: RegisterRequest): RegisterResponse

}