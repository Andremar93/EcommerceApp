package com.example.ecommerceapp.data.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.ecommerceapp.domain.model.ProductItem

@Entity(
    tableName = "products",
    indices = [Index(value = ["id"], unique = true)]
)
data class ProductEntity(
    @PrimaryKey val id: String,
    val name: String,
    val description: String,
    val price: Double,
    val hasDrink: Boolean,
    val imageUrl: String,
    val categories: List<String>,
    val isActive: Boolean = true
)
