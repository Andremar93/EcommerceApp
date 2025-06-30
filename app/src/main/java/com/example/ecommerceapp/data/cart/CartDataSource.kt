package com.example.ecommerceapp.data.cart

import com.example.ecommerceapp.domain.model.CartItem
import com.example.ecommerceapp.domain.model.Product
import kotlinx.coroutines.flow.StateFlow


interface CartDataSource {
    val cartItems: StateFlow<List<CartItem>>
    fun updateQuantity(product: Product, quantity: Int)
    fun removeFromCart(product: Product)
    fun clearCart()
    fun addToCart(product: Product, quantity: Int): Boolean
}
