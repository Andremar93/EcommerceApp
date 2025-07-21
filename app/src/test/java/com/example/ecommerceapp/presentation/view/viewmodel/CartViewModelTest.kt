package com.example.ecommerceapp.presentation.view.viewmodel

import com.example.ecommerceapp.domain.model.CartItem
import com.example.ecommerceapp.domain.model.ProductItem
import com.example.ecommerceapp.domain.repository.CartRepository
import com.example.ecommerceapp.domain.use_case.cart.GetCartItemCountUseCase
import com.example.ecommerceapp.domain.use_case.order.CreateOrderUseCase
import com.example.ecommerceapp.domain.use_case.user.GetActiveUserUseCase
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CartViewModelTest {
    private lateinit var viewModel: CartViewModel

    @Mock
    private lateinit var cartRepository: CartRepository

    @Mock
    private lateinit var getActiveUserUseCase: GetActiveUserUseCase

    @Mock
    private lateinit var createOrderUseCase: CreateOrderUseCase

    @Mock
    private lateinit var getCartItemCountUseCase: GetCartItemCountUseCase

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var closeable: AutoCloseable

    // Flow mutable para simular cartItems
    private val _cartItemsFlow = MutableStateFlow<List<CartItem>>(emptyList())

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        closeable = MockitoAnnotations.openMocks(this)

        // Mock cartItems flow
        whenever(cartRepository.cartItems).thenReturn(_cartItemsFlow)

        // Mock cartItemCount flow
        whenever(cartRepository.cartItemCount).thenReturn(MutableStateFlow(0))

        viewModel = CartViewModel(
            cartRepository,
            getActiveUserUseCase,
            createOrderUseCase
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        closeable.close()
    }

    @Test
    fun `loadCart updates totalProducts and totalPrice correctly`() = runTest {
        val product1 = ProductItem("p1", "Pan", "desc", 10.0, false)
        val product2 = ProductItem("p2", "Leche", "desc", 5.0, true)

        val cartItems = listOf(
            CartItem(product1, quantity = 2),
            CartItem(product2, quantity = 3)
        )

        // Emitimos los items en el flow simulado
        _cartItemsFlow.value = cartItems

        viewModel.loadCart()
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(5, viewModel.totalProducts.value)
        assertEquals(2*10.0 + 3*5.0, viewModel.totalPrice.value, 0.01)
    }

}