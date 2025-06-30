package com.example.ecommerceapp.domain.use_case

import com.example.ecommerceapp.data.user.UserDataSource
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: UserDataSource
) {
    suspend operator fun invoke(email: String, password: String): Boolean {
        return repository.login(email, password)
    }
}
