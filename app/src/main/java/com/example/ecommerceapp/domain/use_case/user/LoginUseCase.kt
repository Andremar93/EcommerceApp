package com.example.ecommerceapp.domain.use_case.user

import com.example.ecommerceapp.domain.repository.UserRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke(email: String, password: String): LoginResult {
        return repository.login(email, password)
    }
}
