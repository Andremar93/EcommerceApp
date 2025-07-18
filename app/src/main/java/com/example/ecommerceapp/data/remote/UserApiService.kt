package com.example.ecommerceapp.data.remote

import com.example.ecommerceapp.data.model.users.LoginRequest
import com.example.ecommerceapp.data.model.users.LoginResponse
import com.example.ecommerceapp.data.model.users.RegisterRequest
import com.example.ecommerceapp.data.model.users.RegisterResponse
import com.example.ecommerceapp.data.model.users.UserDto
import com.example.ecommerceapp.domain.use_case.user.UpdateUserRequest
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface UserApiService {

    @POST("users/login")
    suspend fun login(@Body loginRequest: LoginRequest): LoginResponse

    @POST("users/register")
    suspend fun registerUser(@Body registerRequest: RegisterRequest): RegisterResponse

    @PUT("/users/update/{email}")
    suspend fun updateUser(
        @Path("email", encoded = true) email: String,
        @Body request: UpdateUserRequest
    ): UserDto

}