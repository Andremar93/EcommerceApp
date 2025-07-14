package com.example.ecommerceapp.domain.model

data class OrderItem(
    val orderId: String = "",
    val date: Long = System.currentTimeMillis(),
    val totalAmount: Double,
    val totalItems: Int,
    val items: List<OrderItemsItem>,
    val userId: String
)