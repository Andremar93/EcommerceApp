package com.example.ecommerceapp.domain.model

data class CartItem(
    val product: Product,
    var quantity: Int = 1
)