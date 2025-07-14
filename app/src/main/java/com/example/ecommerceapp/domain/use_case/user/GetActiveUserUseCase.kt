package com.example.ecommerceapp.domain.use_case.user

import com.example.ecommerceapp.domain.model.User
import com.example.ecommerceapp.domain.repository.UserRepository

class GetActiveUserUseCase(
    private val userRepository: UserRepository
) {

    suspend operator fun invoke(): User {
        return userRepository.getActiveUser()
    }
}