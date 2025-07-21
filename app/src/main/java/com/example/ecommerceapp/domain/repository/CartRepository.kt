package com.example.ecommerceapp.domain.repository

import com.example.ecommerceapp.data.local.entity.CartEntity
import com.example.ecommerceapp.domain.model.CartItem
import com.example.ecommerceapp.domain.model.ProductItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface CartRepository {
    val cartItems: Flow<List<CartItem>>
    val cartItemCount: Flow<Int>
    suspend fun updateQuantity(productItem: ProductItem, quantity: Int)
    suspend fun removeFromCart(productItem: ProductItem)
    suspend fun clearCart()
    suspend fun addToCart(productItem: ProductItem, quantity: Int): Boolean
    suspend fun getCartItemCount(): Int
}