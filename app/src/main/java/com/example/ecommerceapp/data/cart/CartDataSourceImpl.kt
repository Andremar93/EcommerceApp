package com.example.ecommerceapp.data.cart

import android.util.Log
import com.example.ecommerceapp.data.database.dao.CartDao
import com.example.ecommerceapp.data.database.dao.ProductDao
import com.example.ecommerceapp.data.database.mappers.toDomain
import com.example.ecommerceapp.data.database.mappers.toEntity
import com.example.ecommerceapp.domain.model.CartItem
import com.example.ecommerceapp.domain.model.Product
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class CartDataSourceImpl @Inject constructor(
    private val cartDao: CartDao,
    private val productDao: ProductDao
) : CartDataSource {

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

    override fun updateQuantity(product: Product, quantity: Int) {
        scope.launch {
            Log.d("UPDATE", "PRODUCT: $product, $quantity")
            val exists = cartDao.getCartItemByProductId(product.id)
            if (quantity > 0) {
                if (exists != null) {
                    val updated = exists.copy(quantity = quantity)
                    cartDao.updateCartItem(updated)
                } else {
                    cartDao.insertCartItem(CartItem(product, quantity).toEntity())
                }
            } else {
                cartDao.deleteByProductId(product.id)
            }
        }
    }

    override fun removeFromCart(product: Product) {
        scope.launch {
            cartDao.deleteByProductId(product.id)
        }
    }

    override fun clearCart() {
        scope.launch {
            cartDao.clearCart()
        }
    }

    override fun addToCart(product: Product, quantity: Int): Boolean {
        scope.launch {
            cartDao.insertCartItem(CartItem(product, quantity).toEntity())
        }
        return true
    }
}
