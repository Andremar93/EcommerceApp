package com.example.ecommerceapp.data.model.orders

import com.google.gson.annotations.SerializedName

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
