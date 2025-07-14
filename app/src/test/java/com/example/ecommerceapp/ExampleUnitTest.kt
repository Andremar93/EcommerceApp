package com.example.ecommerceapp

import com.example.ecommerceapp.domain.model.ProductItem
import com.example.ecommerceapp.domain.repository.CartRepository
import org.junit.Test

import org.junit.Assert.*
import org.mockito.Mockito.mock
import com.example.ecommerceapp.presentation.view.viewmodel.ProductListViewModel
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
//class ProductListViewModelTest {
//
//    private val testDispatcher = StandardTestDispatcher()
//
//    private lateinit var productListViewModel: ProductListViewModel
//
//    val allProductItems = listOf(
//        ProductItem("1", "Hamburguesa Clásica", "Pan, carne, queso", 5.99, true, "comida"),
//        ProductItem("2", "Camiseta Azul", "100% algodón", 9.99, false, "ropa"),
//        ProductItem("3", "Smartphone XYZ", "Android 12", 249.99, false, "tecnología"),
//        ProductItem("4", "Pantalón Jeans", "Talle M", 19.99, true, "ropa"),
//        ProductItem("5", "Laptop ABC", "16GB RAM", 899.99, true, "tecnología")
//    )
//
//    private val getProductsFromApiUseCase: GetProductsFromApiUseCase = mock()
//    private val getProductsFromLocal: GetProductsFromLocalUseCase = mock()
//    private val saveProducts: SaveProductsUseCase = mock()
//    private val cartRepository: CartRepository = mock()
//
//    @Test
//    fun standardTest() = runTest {
//        productListViewModel = ProductListViewModel(
//            getProductsFromApiUseCase,
//            getProductsFromLocal,
//            saveProducts,
//            cartRepository
//        )
//
//        assertFalse(productListViewModel.isLoading.value)
//
//    }
//}