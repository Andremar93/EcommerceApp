package com.example.ecommerceapp.data.model.orders

data class OrderGetRequestDto(
    val userId: String,
    val productIds: List<OrderItemDto>,
    val total: Double,
    val timestamp: Long,
    val orderId: String
)
