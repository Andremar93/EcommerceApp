package com.example.ecommerceapp.domain.model

data class OrderItem(
    val id: Int,
    val productId: String,
    val quantity: Int,
    val price: Double,
    val productName: String
)


