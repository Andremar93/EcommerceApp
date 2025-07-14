package com.example.ecommerceapp.data.model

data class CartDto(
    val name: String,
    val description: String,
    val imageUrl: String,
    val price: Double,
    val hasDrink: Boolean,
    val quantity: Int,
    val categories: List<String>
)
