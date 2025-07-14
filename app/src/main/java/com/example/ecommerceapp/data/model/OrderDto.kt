package com.example.ecommerceapp.data.model

import com.google.gson.annotations.SerializedName

data class OrderDto(
    val orderId: String,
    val userId: String,
    val productIds: List<OrderItemDto>,
    val total: Double,
    val timestamp: Long
)

data class OrderRequestDto(
    val userId: String,
    val productIds: List<OrderItemDto>,
    val total: Double,
    val timestamp: Long,
    val orderId: String
)

data class OrderGetRequestDto(
    val userId: String,
    val productIds: List<OrderItemDto>,
    val total: Double,
    val timestamp: Long,
    val orderId: String
)

data class OrderItemDto(
    @SerializedName("_id") val productId: String,
    val name: String,
    val description: String,
    val imageUrl: String,
    val price: Double,
    val hasDrink: Boolean,
    val quantity: Int,
    val categories: List<String>
)


