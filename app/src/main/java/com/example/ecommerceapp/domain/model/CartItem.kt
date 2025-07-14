package com.example.ecommerceapp.domain.model

data class CartItem(
    val productItem: ProductItem,
    var quantity: Int = 1
)