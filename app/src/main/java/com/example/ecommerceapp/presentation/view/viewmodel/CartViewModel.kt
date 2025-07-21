package com.example.ecommerceapp.presentation.view.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommerceapp.domain.model.CartItem
import com.example.ecommerceapp.domain.repository.CartRepository
import com.example.ecommerceapp.domain.model.OrderItemsItem
import com.example.ecommerceapp.domain.model.OrderItem
import com.example.ecommerceapp.domain.model.ProductItem
import com.example.ecommerceapp.domain.use_case.order.CreateOrderUseCase
import com.example.ecommerceapp.domain.use_case.user.GetActiveUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.collections.sumOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.stateIn
import java.util.UUID

@HiltViewModel
class CartViewModel @Inject constructor(
    private val cartRepository: CartRepository,
    private var getActiveUserUseCase: GetActiveUserUseCase,
    private val createOrderUseCase: CreateOrderUseCase,
) : ViewModel() {

    val cartItemCount: StateFlow<Int> =
        cartRepository.cartItemCount
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    val cartItems: StateFlow<List<CartItem>> =
        cartRepository.cartItems
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())


    var checkoutSuccess by mutableStateOf(false)
        private set

    var isCheckingOut by mutableStateOf(false)
        private set

    var checkoutStep by mutableIntStateOf(0)
        private set

    private val _totalProducts = MutableStateFlow(0)
    val totalProducts: StateFlow<Int> = _totalProducts

    private val _totalPrice = MutableStateFlow(0.0)
    val totalPrice: StateFlow<Double> = _totalPrice

//    var checkoutSuccess by mutableStateOf(false)

    fun loadCart() {
        viewModelScope.launch {
            cartItems.collect { items ->
                _totalProducts.value = items.sumOf { it.quantity }
                _totalPrice.value = items.sumOf { it.productItem.price * it.quantity }
            }
        }
    }

    fun increaseQuantity(productItem: ProductItem) {
        viewModelScope.launch {
            val existing =
                cartRepository.cartItems.firstOrNull()?.find { it.productItem.id == productItem.id }
            val newQuantity = (existing?.quantity ?: 0) + 1
            cartRepository.updateQuantity(productItem, newQuantity)
        }
    }

    fun decreaseQuantity(productItem: ProductItem) {
        viewModelScope.launch {
            val existing =
                cartRepository.cartItems.firstOrNull()?.find { it.productItem.id == productItem.id }
            val newQuantity = (existing?.quantity ?: 0) - 1
            cartRepository.updateQuantity(productItem, newQuantity)
        }
    }

    fun removeFromCart(productItem: ProductItem) {
        viewModelScope.launch {
            cartRepository.removeFromCart(productItem)
        }
    }

    fun clearCart() {
        viewModelScope.launch {
            cartRepository.clearCart()
        }
    }

    fun finalizeOrder() {
        viewModelScope.launch {
            val items = cartItems.value
            if (items.isEmpty()) return@launch

            // Iniciar loader
            isCheckingOut = true
            checkoutStep = 0
            checkoutSuccess = false

            val stepsCount = 4
            val delayPerStep = 2000L // 2 segundos por paso

            while (checkoutStep < stepsCount - 1) {
                delay(delayPerStep)
                checkoutStep++
            }


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

            // Finalizar loader
            checkoutStep = stepsCount - 1
            delay(1500)
            isCheckingOut = false
            checkoutSuccess = true
        }
    }
}
