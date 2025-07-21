package com.example.ecommerceapp.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "cart_items",
    indices = [Index(value = ["productId"])],
    foreignKeys = [ForeignKey(
        ProductEntity::class,
        parentColumns = ["id"],
        childColumns = ["productId"],
    )]

)
data class CartEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val productId: String,
    var quantity: Int = 1
)