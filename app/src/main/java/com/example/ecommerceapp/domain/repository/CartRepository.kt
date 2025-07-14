package com.example.ecommerceapp.domain.repository

import com.example.ecommerceapp.domain.model.CartItem
import com.example.ecommerceapp.domain.model.ProductItem
import kotlinx.coroutines.flow.StateFlow

interface CartRepository {
    val cartItems: StateFlow<List<CartItem>>
    fun updateQuantity(productItem: ProductItem, quantity: Int)
    fun removeFromCart(productItem: ProductItem)
    fun clearCart()
    fun addToCart(productItem: ProductItem, quantity: Int): Boolean
}