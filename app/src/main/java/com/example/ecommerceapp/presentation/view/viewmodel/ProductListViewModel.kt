package com.example.ecommerceapp.presentation.view.viewmodel

import android.util.Log
import com.example.ecommerceapp.domain.model.Product
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommerceapp.data.cart.CartDataSource
import com.example.ecommerceapp.data.products.ProductsDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductListViewModel @Inject constructor(
    private val cartDataSource: CartDataSource,
    private val productsDataSource: ProductsDataSource
) : ViewModel() {

    private val _filteredProducts = mutableStateOf<List<Product>>(emptyList())
    val filteredProducts: State<List<Product>> = _filteredProducts

    private val _isAddingToCart = mutableStateOf(false)
    val isAddingToCart: State<Boolean> = _isAddingToCart

    private var allProducts: List<Product> = emptyList()
    private var currentQuery = ""
    private var currentCategory: String? = null
    private var currentSortAscending = true

    private val _productQuantities: SnapshotStateMap<String, Int> = mutableStateMapOf()
    val productQuantities: Map<String, Int> get() = _productQuantities

    private val _lastAddedProduct = mutableStateOf<Product?>(null)
    val lastAddedProduct: State<Product?> = _lastAddedProduct

    private val _isLoading = mutableStateOf(true)
    val isLoading: State<Boolean> = _isLoading

    init {
        loadProducts()
    }

    private fun loadProducts() {
        _isLoading.value = true
        viewModelScope.launch {
            launch {
                productsDataSource.observeCachedProducts().collect { localProducts ->
                    allProducts = localProducts
                    applyFilters()
                    _isLoading.value = false
                }
            }

            try {
                val apiProducts = productsDataSource.getAllProducts()
                productsDataSource.cacheProducts(apiProducts)
            } catch (e: Exception) {
                Log.e("ProductListViewModel", "API fetch failed", e)
            }
        }
    }


    fun addToCart(product: Product, quantity: Int) {
        viewModelScope.launch {
            Log.d("addfromproduct", "$product")
            _isAddingToCart.value = true
            delay(150L)
            if(cartDataSource.addToCart(product, quantity)){
                _lastAddedProduct.value = product
                _isAddingToCart.value = false
            }

        }
    }

    sealed class NavigationEvent {
        object NavigateToCart : NavigationEvent()
    }

    fun filter(query: String) {
        currentQuery = query
        applyFilters()
    }

    fun filterByCategory(category: String) {
        currentCategory = category
        applyFilters()
    }

    fun sortByPrice(ascending: Boolean) {
        currentSortAscending = ascending
        applyFilters()
    }

    fun applyFilters() {
        var result = allProducts
        if (currentQuery.isNotBlank()) {
            result = result.filter { prod ->
                val matchesText = prod.name.contains(currentQuery, ignoreCase = true)
                        || prod.description.contains(currentQuery, ignoreCase = true)
                        || prod.categories.any { cat ->
                    cat.contains(currentQuery, ignoreCase = true)
                }
                matchesText
            }
        }

        currentCategory?.let { cat ->
            if (cat != "Todas") {
                result = result.filter { prod ->
                    val matchesCat = prod.categories.any { category ->
                        category.equals(cat, ignoreCase = true)
                    }
                    matchesCat
                }
            }
        }

        result = if (currentSortAscending) {
            result.sortedBy { it.price }
        } else {
            result.sortedByDescending { it.price }
        }

        _filteredProducts.value = result
    }

    fun increaseQuantity(productId: String) {
        val current = _productQuantities[productId] ?: 1
        _productQuantities[productId] = current + 1
    }

    fun decreaseQuantity(productId: String) {
        val current = _productQuantities[productId] ?: 1
        if (current > 1) {
            _productQuantities[productId] = current - 1
        }
    }

    fun resetLastAddedProduct() {
        _lastAddedProduct.value = null
    }

}