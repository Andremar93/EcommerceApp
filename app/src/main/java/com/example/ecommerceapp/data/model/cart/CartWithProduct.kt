package com.example.ecommerceapp.data.model.cart

import androidx.room.Embedded
import androidx.room.Relation
import com.example.ecommerceapp.data.local.entity.CartEntity
import com.example.ecommerceapp.data.local.entity.ProductEntity

data class CartWithProduct(
    @Embedded val cartItem: CartEntity,
    @Relation(
        parentColumn = "productId",
        entityColumn = "id"
    )
    val product: ProductEntity
)