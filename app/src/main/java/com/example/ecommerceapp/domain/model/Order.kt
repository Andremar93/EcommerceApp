package com.example.ecommerceapp.domain.model

data class Order(
    val id: Long = 0L,
    val date: Long = System.currentTimeMillis(),
    val totalAmount: Double,
    val totalItems: Int,
    val items: List<OrderItem>
)