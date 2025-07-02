package com.example.ecommerceapp.data.model

import com.example.ecommerceapp.domain.model.Product
import com.google.gson.annotations.SerializedName

data class ProductDto(
    @SerializedName("_id") val id: String,
    val name: String,
    val description: String,
    val price: Double,
    val imageUrl: String,
    val categories: List<String>,
    val hasDrink: Boolean
) {
    fun toDomain() = Product(id, name, description, price, hasDrink, imageUrl, categories)
}