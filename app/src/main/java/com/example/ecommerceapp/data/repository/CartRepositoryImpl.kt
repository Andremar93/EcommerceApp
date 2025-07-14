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

class CartRepositoryImpl(
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

    override fun updateQuantity(productItem: ProductItem, quantity: Int) {
        scope.launch {
            Log.d("UPDATE", "PRODUCT: $productItem, $quantity")
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
            cartDao.insertCartItem(CartItem(productItem, quantity).toEntity())
        }
        return true
    }
}