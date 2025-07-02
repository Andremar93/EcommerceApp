package com.example.ecommerceapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey val id: String,
    val name: String = "",
    val lastName: String = "",
    val email: String = "",
    val nationality: String = "",
    val avatar: String = "",
    val password: String = ""
)

