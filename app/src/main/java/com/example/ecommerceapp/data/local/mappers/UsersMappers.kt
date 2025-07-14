package com.example.ecommerceapp.data.local.mappers

import com.example.ecommerceapp.data.local.entity.UserEntity
import com.example.ecommerceapp.domain.model.User

fun User.toEntity(): UserEntity {
    return UserEntity(
        id = id,
        name = name,
        lastName = lastName,
        password = password,
        email = email,
        nationality = nationality,
        avatar = avatar
    )
}

fun User.toDto(): UserEntity {
    return UserEntity(
        id = id,
        name = name,
        lastName = lastName,
        password = password,
        email = email,
    )
}

fun UserEntity.toDomain(): User {
    return User(
        id = id,
        name = name,
        lastName = lastName,
        password = password,
        email = email,
        nationality = nationality,
        avatar = avatar
    )
}