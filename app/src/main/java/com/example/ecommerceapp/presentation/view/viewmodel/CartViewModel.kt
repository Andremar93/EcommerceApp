package com.example.ecommerceapp.presentation.view.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommerceapp.data.cart.CartDataSource
import com.example.ecommerceapp.data.database.entities.OrderEntity
import com.example.ecommerceapp.data.database.entities.OrderItemEntity
import com.example.ecommerceapp.data.orders.OrdersDataSource
import com.example.ecommerceapp.domain.model.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.collections.sumOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@HiltViewModel
class CartViewModel @Inject constructor(
    private val cartDataSource: CartDataSource,
    private val ordersDataSource: OrdersDataSource
) : ViewModel() {

    val cartItems = cartDataSource.cartItems

    private val _cartItemCount = MutableStateFlow(0)
    val cartItemCount: StateFlow<Int> = _cartItemCount

    private val _totalProducts = MutableStateFlow(0)
    val totalProducts: StateFlow<Int> = _totalProducts

    private val _totalPrice = MutableStateFlow(0.0)
    val totalPrice: StateFlow<Double> = _totalPrice

    var checkoutSuccess by mutableStateOf(false)

    init {
        viewModelScope.launch {
            cartItems.collect { items ->
                _cartItemCount.value = items.size
                _totalProducts.value = items.sumOf { it.quantity }
                _totalPrice.value = items.sumOf { it.product.price * it.quantity }
            }
        }
    }

    fun increaseQuantity(product: Product) {
        val current = cartItems.value.find { it.product.id == product.id }?.quantity ?: 0
        cartDataSource.updateQuantity(product, current + 1)
    }

    fun decreaseQuantity(product: Product) {
        val current = cartItems.value.find { it.product.id == product.id }?.quantity ?: 0
        cartDataSource.updateQuantity(product, current - 1)
    }

    fun removeFromCart(product: Product) {
        cartDataSource.removeFromCart(product)
    }

    fun clearCart() {
        cartDataSource.clearCart()
    }

    fun finalizeOrder() {
        viewModelScope.launch {
            val items = cartItems.value
            if (items.isEmpty()) return@launch

            val order = OrderEntity(
                orderDate = System.currentTimeMillis(),
                totalAmount = items.sumOf { it.product.price * it.quantity },
                totalItems = items.sumOf { it.quantity }
            )

            val orderItems = items.map { cartItem ->
                OrderItemEntity(
                    orderId = 0L,
                    productId = cartItem.product.id,
                    quantity = cartItem.quantity,
                    price = cartItem.product.price,
                    productName = cartItem.product.name
                )
            }

            ordersDataSource.createOrder(order, orderItems)
            clearCart()
            checkoutSuccess = true
        }
    }
}
