package com.example.ecommerceapp.data.repository

import android.util.Log
import com.example.ecommerceapp.data.local.dao.CartDao
import com.example.ecommerceapp.data.local.dao.ProductDao
import com.example.ecommerceapp.data.local.mappers.toDomain
import com.example.ecommerceapp.data.local.mappers.toEntity
import com.example.ecommerceapp.domain.model.CartItem
import com.example.ecommerceapp.domain.model.ProductItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import com.example.ecommerceapp.domain.repository.CartRepository
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class CartRepositoryImpl @Inject constructor(
    private val cartDao: CartDao,
    private val productDao: ProductDao
) : CartRepository {

    private val scope = CoroutineScope(Dispatchers.IO)

    private val _cartItems: StateFlow<List<CartItem>> by lazy {
        cartDao.getAllCartItems()
            .map { cartEntities ->
                cartEntities.mapNotNull { entity ->
                    val product = productDao.getProductById(entity.productId)?.toDomain()
                    product?.let { entity.toDomain(it) }
                }
            }
            .stateIn(scope = scope, started = SharingStarted.Eagerly, initialValue = emptyList())
    }
    override val cartItems: StateFlow<List<CartItem>> get() = _cartItems


    override val cartItemCount: StateFlow<Int> =
        cartDao.getCartItemCountFlow()
            .map { it ?: 0 }
            .stateIn(scope, SharingStarted.WhileSubscribed(5000), 0)

    override fun updateQuantity(productItem: ProductItem, quantity: Int) {
        scope.launch {
            val exists = cartDao.getCartItemByProductId(productItem.id)
            if (quantity > 0) {
                if (exists != null) {
                    val updated = exists.copy(quantity = quantity)
                    cartDao.updateCartItem(updated)
                } else {
                    cartDao.insertCartItem(CartItem(productItem, quantity).toEntity())
                }
            } else {
                cartDao.deleteByProductId(productItem.id)
            }
        }
    }

    override fun removeFromCart(productItem: ProductItem) {
        scope.launch {
            cartDao.deleteByProductId(productItem.id)
        }
    }

    override fun clearCart() {
        scope.launch {
            cartDao.clearCart()
        }
    }

    override fun addToCart(productItem: ProductItem, quantity: Int): Boolean {
        scope.launch {
            val existingItem = cartDao.getCartItemByProductId(productItem.id)
            if (existingItem != null) {
                val updatedItem = existingItem.copy(quantity = existingItem.quantity + quantity)
                cartDao.updateCartItem(updatedItem)
                return@launch
            } else {
                cartDao.insertCartItem(CartItem(productItem, quantity).toEntity())
            }
        }
        return true
    }

    override suspend fun getCartItemCount(): Int {
        return cartDao.getCartItemCount() ?: 0
    }
}