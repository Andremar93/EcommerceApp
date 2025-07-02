package com.example.ecommerceapp.domain.model

data class Product(
    val id: String,
    val name: String,
    val description: String,
    val price: Double,
    val hasDrink: Boolean,
    val imageUrl: String = "https://via.placeholder.com/600x400.png?text=Producto",
    val categories: List<String> = emptyList()
)
