package com.example.ecommerceapp.domain.use_case

import com.example.ecommerceapp.data.user.UserDataSource
import com.example.ecommerceapp.domain.model.User
import javax.inject.Inject


class RegisterUseCase @Inject constructor(
    private val repository: UserDataSource
) {
    suspend operator fun invoke(
        user: User
    ): Boolean {
        return repository.registerUser(user)
    }
}



