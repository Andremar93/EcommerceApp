package com.example.ecommerceapp.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "orders",
    foreignKeys = [
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.Companion.CASCADE
        )],
    indices = [
        Index(value = ["userId"])
    ]
)
data class OrderEntity(
    @PrimaryKey val id: String,
    val orderDate: Long = System.currentTimeMillis(),
    val totalAmount: Double,
    val totalItems: Int,
    val userId: String,
)