package com.example.ecommerceapp.data.model.users

import com.example.ecommerceapp.data.local.entity.UserEntity
import com.example.ecommerceapp.domain.model.User
import com.google.gson.annotations.SerializedName

data class UserDto(
    @SerializedName("_id") val id: String,
    val email: String,
    val fullName: String,
    val userImageUrl: String?,
    val encryptedPassword: String,
    val nationality: String
) {
    fun toDomain(): User {
        val nameParts = fullName.trim().split(" ", limit = 2)
        val name = nameParts.getOrNull(0) ?: ""
        val lastName = nameParts.getOrNull(1) ?: ""

        return User(
            id = id,
            name = name,
            lastName = lastName,
            email = email,
            nationality = nationality,
            avatar = userImageUrl ?: "",
            password = encryptedPassword
        )
    }

    fun toEntity(): UserEntity {
        val nameParts = fullName.trim().split(" ", limit = 2)
        val name = nameParts.getOrNull(0) ?: ""
        val lastName = nameParts.getOrNull(1) ?: ""

        return UserEntity(
            id = id,
            name = name,
            lastName = lastName,
            email = email,
            nationality = nationality,
            avatar = userImageUrl ?: "",
            password = encryptedPassword
        )
    }

}