package com.example.ecommerceapp.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "order_items",
    foreignKeys = [
        ForeignKey(
            entity = OrderEntity::class,
            parentColumns = ["id"],
            childColumns = ["orderId"],
            onDelete = ForeignKey.Companion.CASCADE
        )
    ],
    indices = [
        Index(value = ["orderId"]),
    ]
)
data class OrderItemsEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    var orderId: String,
    val productId: String,
    val productName: String,
    val quantity: Int,
    val price: Double
)