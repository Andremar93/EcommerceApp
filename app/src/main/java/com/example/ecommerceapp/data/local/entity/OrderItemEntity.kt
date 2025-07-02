package com.example.ecommerceapp.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "orders")
data class OrderEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val orderDate: Long = System.currentTimeMillis(),
    val totalAmount: Double,
    val totalItems: Int
)

@Entity(
    tableName = "order_items",
    foreignKeys = [
        ForeignKey(
            entity = OrderEntity::class,
            parentColumns = ["id"],
            childColumns = ["orderId"],
            onDelete = ForeignKey.Companion.CASCADE
        ),
        ForeignKey(
            entity = ProductEntity::class,
            parentColumns = ["id"],
            childColumns = ["productId"],
            onDelete = ForeignKey.Companion.NO_ACTION
        )
    ],
    indices = [
        Index(value = ["orderId"]),
        Index(value = ["productId"])
    ]
)
data class OrderItemEntity(
    @PrimaryKey(autoGenerate = true) val orderItemId: Int = 0,
    var orderId: Long = 0,
    val productId: String,
    val productName: String,
    val quantity: Int,
    val price: Double
)