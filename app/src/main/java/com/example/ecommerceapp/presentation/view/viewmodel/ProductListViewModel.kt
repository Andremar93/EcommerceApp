package com.example.ecommerceapp.presentation.view.viewmodel

import android.util.Log
import com.example.ecommerceapp.domain.model.Product
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommerceapp.domain.repository.CartRepository
import com.example.ecommerceapp.domain.use_case.product.GetProductsFromApiUseCase
import com.example.ecommerceapp.domain.use_case.product.GetProductsFromLocalUseCase
import com.example.ecommerceapp.domain.use_case.product.SaveProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@HiltViewModel
class ProductListViewModel @Inject constructor(
    private val getProductsFromApi: GetProductsFromApiUseCase,
    private val getProductsFromLocal: GetProductsFromLocalUseCase,
    private val saveProducts: SaveProductsUseCase,
    private val cartRepository: CartRepository
) : ViewModel() {

    var searchQuery by mutableStateOf("")
    var selectedCategory by mutableStateOf("Todas")
    var sortAscending by mutableStateOf(true)

    private val _allCategories = MutableStateFlow<List<String>>(emptyList())
    val allCategories: StateFlow<List<String>> = _allCategories

    var addingProductId by mutableStateOf<String?>(null)

    private val _filteredProducts = mutableStateOf<List<Product>>(emptyList())
    val filteredProducts: State<List<Product>> = _filteredProducts

    private var allProducts: List<Product> = emptyList()
    private var currentQuery = ""
    private var currentCategory: String? = null
    var currentSortAscending = true

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
            allProducts = getProductsFromLocal()
            _allCategories.value = listOf("Todas") + allProducts.flatMap { it.categories }.distinct()
            applyFilters()
            _isLoading.value = false

            try {
                val remoteProducts = getProductsFromApi()
                Log.d("API", "Productos desde API: ${remoteProducts.size}")
                saveProducts(remoteProducts)
                allProducts = remoteProducts
                _allCategories.value = listOf("Todas") + allProducts.flatMap { it.categories }.distinct()
                applyFilters()
            } catch (e: Exception) {
                Log.e("ProductListViewModel", "API fetch failed", e)
            }
        }
    }


    fun addToCart(product: Product, quantity: Int) {
        viewModelScope.launch {
            addingProductId = product.id
            delay(300)
            if (cartRepository.addToCart(product, quantity)) {
                _lastAddedProduct.value = product
            }
        }
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