package com.example.ecommerceapp.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "user")
data class UsersEntity(
    @PrimaryKey val id: String,
    val name: String = "",
    val lastName: String = "",
    val email: String = "",
    val nationality: String = "",
    val avatar: String = "",
//    val password: String = ""
)

