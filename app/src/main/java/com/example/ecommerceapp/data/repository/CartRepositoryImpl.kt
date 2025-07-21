package com.example.ecommerceapp.data.repository

import com.example.ecommerceapp.data.local.dao.CartDao
import com.example.ecommerceapp.data.local.mappers.toDomain
import com.example.ecommerceapp.data.local.mappers.toEntity
import com.example.ecommerceapp.domain.model.CartItem
import com.example.ecommerceapp.domain.model.ProductItem
import com.example.ecommerceapp.domain.repository.CartRepository
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CartRepositoryImpl @Inject constructor(
    private val cartDao: CartDao,
) : CartRepository {

    override val cartItems: Flow<List<CartItem>> =
        cartDao.getAllCartItemsWithProduct()
            .onEach { list ->
            }
            .map { list -> list.map { it.toDomain() } }


    override val cartItemCount: Flow<Int> =
        cartDao.getCartItemCountFlow()
            .map { it ?: 0 }
            .distinctUntilChanged()

    override suspend fun updateQuantity(productItem: ProductItem, quantity: Int) {
        val existing = cartDao.getCartItemByProductId(productItem.id)

        if (quantity > 0) {
            if (existing != null) {
                val updated = existing.copy(quantity = quantity)
                cartDao.updateCartItem(updated)
            } else {
                val entity = CartItem(productItem, quantity).toEntity()
                cartDao.insertCartItem(entity)
            }
        } else {
            cartDao.deleteByProductId(productItem.id)
        }
    }


    override suspend fun removeFromCart(productItem: ProductItem) {
        cartDao.deleteByProductId(productItem.id)
    }

    override suspend fun clearCart() {
        cartDao.clearCart()
    }

    override suspend fun addToCart(productItem: ProductItem, quantity: Int): Boolean {
        val existingItem = cartDao.getCartItemByProductId(productItem.id)
        if (existingItem != null) {
            val updatedItem = existingItem.copy(quantity = existingItem.quantity + quantity)
            cartDao.updateCartItem(updatedItem)
        } else {
            cartDao.insertCartItem(CartItem(productItem, quantity).toEntity())
        }
        return true
    }

    override suspend fun getCartItemCount(): Int {
        return cartDao.getCartItemCount() ?: 0
    }
}
