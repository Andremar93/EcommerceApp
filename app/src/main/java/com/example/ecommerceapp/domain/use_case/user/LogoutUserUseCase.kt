package com.example.ecommerceapp.domain.use_case.user

import com.example.ecommerceapp.domain.model.User
import com.example.ecommerceapp.domain.repository.UserRepository
import javax.inject.Inject

class LogoutUserUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke(
        userId: String
    ): Boolean {
        return repository.logoutUser(userId)
    }
}