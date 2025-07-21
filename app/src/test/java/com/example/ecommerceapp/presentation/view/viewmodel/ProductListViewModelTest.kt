package com.example.ecommerceapp.presentation.view.viewmodel

import com.example.ecommerceapp.domain.model.ProductItem
import com.example.ecommerceapp.domain.use_case.cart.AddToCartUseCase
import com.example.ecommerceapp.domain.use_case.product.GetProductsUseCase
import com.example.ecommerceapp.presentation.view.components.UIState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever
import org.junit.Assert.*
import org.mockito.Mock
import org.mockito.MockitoAnnotations

@OptIn(ExperimentalCoroutinesApi::class)
class ProductListViewModelTest {

    private lateinit var viewModel: ProductListViewModel

    @Mock
    private lateinit var getProductsUseCase: GetProductsUseCase

    @Mock
    private lateinit var addToCartUseCase: AddToCartUseCase

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var closeable: AutoCloseable

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        closeable = MockitoAnnotations.openMocks(this)
        viewModel = ProductListViewModel(getProductsUseCase, addToCartUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        closeable.close()
    }

    @Test
    fun `filterByCategory filters products correctly`() = runTest {
        val mockProducts = listOf(
            ProductItem(id = "1", name = "Producto A", description = "desc", price = 10.0, hasDrink = false, categories = listOf("Pan")),
            ProductItem(id = "2", name = "Producto B", description = "desc", price = 20.0, hasDrink = true, categories = listOf("Bebidas")),
            ProductItem(id = "3", name = "Producto C", description = "desc", price = 15.0, hasDrink = false, categories = listOf("Pan"))
        )

        whenever(getProductsUseCase.invoke(any())).thenReturn(mockProducts)

        viewModel.loadProducts(refreshData = true)
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.filterByCategory("Pan")
        val filtered = viewModel.filteredProducts.value

        assertEquals(2, filtered.size)
        assertTrue(filtered.all { it.categories.contains("Pan") })
    }

    @Test
    fun `filter filters products by search query`() = runTest {
        val products = listOf(
            ProductItem("1", "Pan integral", "rico pan", 10.0, false, categories = listOf("Pan")),
            ProductItem("2", "Leche", "fresca", 5.0, true, categories = listOf("Bebidas")),
            ProductItem("3", "Pan dulce", "delicioso", 15.0, false, categories = listOf("Pan"))
        )
        whenever(getProductsUseCase.invoke(any())).thenReturn(products)
        viewModel.loadProducts(true)
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.filter("dulce")
        val filtered = viewModel.filteredProducts.value

        assertEquals(1, filtered.size)
        assertEquals("Pan dulce", filtered[0].name)
    }

    @Test
    fun `sortByPrice sorts ascending and descending`() = runTest {
        val products = listOf(
            ProductItem("1", "A", "desc", 30.0, false),
            ProductItem("2", "B", "desc", 10.0, false),
            ProductItem("3", "C", "desc", 20.0, false),
        )
        whenever(getProductsUseCase.invoke(any())).thenReturn(products)
        viewModel.loadProducts(true)
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.sortByPrice(true) // asc
        var filtered = viewModel.filteredProducts.value
        assertEquals(listOf("2", "3", "1"), filtered.map { it.id })

        viewModel.sortByPrice(false) // desc
        filtered = viewModel.filteredProducts.value
        assertEquals(listOf("1", "3", "2"), filtered.map { it.id })
    }

    @Test
    fun `increaseQuantity and decreaseQuantity update quantities correctly`() {
        val productId = "123"
        viewModel.increaseQuantity(productId)
        assertEquals(2, viewModel.productQuantities[productId])

        viewModel.decreaseQuantity(productId)
        assertEquals(1, viewModel.productQuantities[productId])

        // No debe decrementar menos de 1
        viewModel.decreaseQuantity(productId)
        assertEquals(1, viewModel.productQuantities[productId])
    }


    @Test
    fun `loadProducts updates uiState to Success on success`() = runTest {
        val products = listOf(ProductItem("1", "P", "desc", 10.0, false))
        whenever(getProductsUseCase.invoke(any())).thenReturn(products)

        viewModel.loadProducts(true)
        testDispatcher.scheduler.advanceUntilIdle()

        assertTrue(viewModel.uiState is UIState.Success)
        assertEquals(products, (viewModel.uiState as UIState.Success).data)
    }

    @Test
    fun `addToCart updates lastAddedProductItem on success`() = runTest {
        val product = ProductItem("1", "P", "desc", 10.0, false)
        whenever(addToCartUseCase.invoke(product, 1)).thenReturn(true)

        viewModel.addToCart(product, 1)
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(product, viewModel.lastAddedProductItem.value)
    }

    @Test
    fun `resetLastAddedProduct resets lastAddedProductItem to null`() {
        val product = ProductItem("1", "P", "desc", 10.0, false)
        viewModel.resetLastAddedProduct()
        assertNull(viewModel.lastAddedProductItem.value)
    }




}
