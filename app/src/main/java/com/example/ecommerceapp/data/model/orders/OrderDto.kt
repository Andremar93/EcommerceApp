package com.example.ecommerceapp.data.model.orders

data class OrderDto(
    val orderId: String,
    val userId: String,
    val productIds: List<OrderItemDto>,
    val total: Double,
    val timestamp: Long
)

