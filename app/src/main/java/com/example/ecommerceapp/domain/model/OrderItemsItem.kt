package com.example.ecommerceapp.domain.model

data class OrderItemsItem(
    val orderId: String,
    val productId: String,
    val quantity: Int,
    val price: Double,
    val productName: String,
    val avatar: String,
    val description: String,
    val hasDrink: Boolean,
    val categories: List<String>
)


