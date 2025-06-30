package com.example.ecommerceapp.domain.model

import com.example.ecommerceapp.data.database.entities.ProductEntity

data class Product(
    val id: String,
    val name: String,
    val description: String,
    val price: Double,
    val hasDrink: Boolean,
//    val quantity: Int = 1,
    val imageUrl: String = "https://via.placeholder.com/600x400.png?text=Producto",
    val categories: List<String> = emptyList()
)

data class ProductFromAPI(
    val _id: String,
    val name: String,
    val price: Double,
    val imageUrl: String,
    val description: String,
    val hasDrink: Boolean,
    val categories: List<String>
)
