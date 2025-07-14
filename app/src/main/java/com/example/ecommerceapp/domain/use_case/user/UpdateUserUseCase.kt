package com.example.ecommerceapp.domain.use_case.user

import com.example.ecommerceapp.domain.model.User
import com.example.ecommerceapp.domain.repository.UserRepository
import javax.inject.Inject

sealed class UpdateUserResult {
    object Success : UpdateUserResult()
    data class Error(val message: String) : UpdateUserResult()
}

data class UpdateUserRequest(
    val email: String,
    val fullName: String,
    val userImageUrl: String?,
    val nationality: String
)

class UpdateUserUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(user: User): UpdateUserResult {
        return userRepository.updateUser(user)
    }
}