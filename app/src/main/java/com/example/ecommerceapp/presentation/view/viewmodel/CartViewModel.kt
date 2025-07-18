package com.example.ecommerceapp.presentation.view.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommerceapp.domain.repository.CartRepository
import com.example.ecommerceapp.domain.model.OrderItemsItem
import com.example.ecommerceapp.domain.model.OrderItem
import com.example.ecommerceapp.domain.model.ProductItem
import com.example.ecommerceapp.domain.use_case.cart.GetCartItemCountUseCase
import com.example.ecommerceapp.domain.use_case.order.CreateOrderUseCase
import com.example.ecommerceapp.domain.use_case.user.GetActiveUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.collections.sumOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.UUID

@HiltViewModel
class CartViewModel @Inject constructor(
    private val cartRepository: CartRepository,
    private var getActiveUserUseCase: GetActiveUserUseCase,
    private val createOrderUseCase: CreateOrderUseCase,
    private val getCartItemCountUseCase: GetCartItemCountUseCase
) : ViewModel() {

    val cartItems = cartRepository.cartItems

    val cartItemCount: StateFlow<Int> = cartRepository.cartItemCount

    private val _totalProducts = MutableStateFlow(0)
    val totalProducts: StateFlow<Int> = _totalProducts

    private val _totalPrice = MutableStateFlow(0.0)
    val totalPrice: StateFlow<Double> = _totalPrice

    var checkoutSuccess by mutableStateOf(false)


    fun loadCart() {
        viewModelScope.launch {
            cartItems.collect { items ->
//                _cartItemCount.value = items.size
                _totalProducts.value = items.sumOf { it.quantity }
                _totalPrice.value = items.sumOf { it.productItem.price * it.quantity }
            }
        }
    }

    suspend fun getCartItemCount(): Int {
        return getCartItemCountUseCase.invoke()
    }

    fun increaseQuantity(productItem: ProductItem) {
        val current = cartItems.value.find { it.productItem.id == productItem.id }?.quantity ?: 0
        cartRepository.updateQuantity(productItem, current + 1)
    }

    fun decreaseQuantity(productItem: ProductItem) {
        val current = cartItems.value.find { it.productItem.id == productItem.id }?.quantity ?: 0
        cartRepository.updateQuantity(productItem, current - 1)
    }

    fun removeFromCart(productItem: ProductItem) {
        cartRepository.removeFromCart(productItem)
    }

    fun clearCart() {
        cartRepository.clearCart()
    }

    fun finalizeOrder() {
        viewModelScope.launch {
            val items = cartItems.value
            if (items.isEmpty()) return@launch
            val generatedOrderId = UUID.randomUUID().toString()
            val orderItems = items.map { cartItem ->
                OrderItemsItem(
                    orderId = generatedOrderId,
                    productId = cartItem.productItem.id,
                    quantity = cartItem.quantity,
                    price = cartItem.productItem.price,
                    productName = cartItem.productItem.name,
                    avatar = cartItem.productItem.imageUrl,
                    description = cartItem.productItem.description,
                    categories = cartItem.productItem.categories,
                    hasDrink = cartItem.productItem.hasDrink
                )
            }

            val activeUser = getActiveUserUseCase.invoke()
            val userId = activeUser.id

            val order = OrderItem(
                orderId = generatedOrderId,
                date = System.currentTimeMillis(),
                totalAmount = items.sumOf { it.productItem.price * it.quantity },
                totalItems = items.sumOf { it.quantity },
                items = orderItems,
                userId = userId,
            )

            createOrderUseCase.invoke(order, orderItems)
            clearCart()
            checkoutSuccess = true
        }
    }
}
