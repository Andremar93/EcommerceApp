package com.example.ecommerceapp.domain.use_case.user

import com.example.ecommerceapp.domain.model.User
import com.example.ecommerceapp.domain.repository.UserRepository
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke(
        user: User
    ): RegisterResult {
        return repository.registerUser(user)
    }
}