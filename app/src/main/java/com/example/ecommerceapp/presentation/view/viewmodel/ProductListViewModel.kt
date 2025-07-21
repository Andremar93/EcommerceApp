package com.example.ecommerceapp.presentation.view.viewmodel

import android.util.Log
import com.example.ecommerceapp.domain.model.ProductItem
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.example.ecommerceapp.domain.use_case.cart.AddToCartUseCase
import com.example.ecommerceapp.domain.use_case.product.GetProductsUseCase
import com.example.ecommerceapp.presentation.view.components.UIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import okio.IOException
import retrofit2.HttpException

@HiltViewModel
class ProductListViewModel @Inject constructor(
    private val getProductsUseCase: GetProductsUseCase,
    private val addToCartUseCase: AddToCartUseCase,
) : ViewModel() {

    var searchQuery by mutableStateOf("")
        private set
    var selectedCategory by mutableStateOf("Todas")
        private set

    private val _allCategories = MutableStateFlow<List<String>>(emptyList())
    val allCategories: StateFlow<List<String>> = _allCategories

    var addingProductId by mutableStateOf<String?>(null)
        private set

    private val _filteredProducts = mutableStateOf<List<ProductItem>>(emptyList())
    val filteredProducts: State<List<ProductItem>> = _filteredProducts

    private var allProductItems: List<ProductItem> = emptyList()
    var currentSortAscending by mutableStateOf(true)
        private set

    private val _productQuantities: SnapshotStateMap<String, Int> = mutableStateMapOf()
    val productQuantities: Map<String, Int> get() = _productQuantities

    private val _lastAddedProductItem = mutableStateOf<ProductItem?>(null)
    val lastAddedProductItem: State<ProductItem?> = _lastAddedProductItem

    var uiState by mutableStateOf<UIState<List<ProductItem>>>(UIState.Loading)
        private set

    fun loadProducts(refreshData: Boolean) {

        viewModelScope.launch {
            uiState = UIState.Loading
            try {
                val products = getProductsUseCase.invoke(refreshData)
                allProductItems = products
                _allCategories.value = listOf("Todas") + allProductItems.flatMap { it.categories }.distinct()
                applyFilters()
                uiState = UIState.Success(products)
            } catch (e: IOException) {
                uiState = UIState.Error("Sin conexión a Internet")
                Log.e("ProductListViewModel", "Sin conexión a Internet, error al cargar productos", e)
            } catch (e: HttpException) {
                uiState = UIState.Error("Error al cargar productos: ${e.message()}")
            } catch (e: Exception) {
                uiState = UIState.Error("Error inesperado: ${e.message ?: "Desconocido"}")
                Log.e("ProductListViewModel", "Error inesperado al cargar productos", e)
            }
        }
    }

    fun addToCart(productItem: ProductItem, quantity: Int) {
        viewModelScope.launch {
            addingProductId = productItem.id
            val success = addToCartUseCase.invoke(productItem, quantity)
            if (success) {
                _lastAddedProductItem.value = productItem
            }
            addingProductId = null // limpiar el estado para UI
        }
    }

    fun filter(query: String) {
        searchQuery = query
        applyFilters()
    }

    fun filterByCategory(category: String) {
        selectedCategory = category
        applyFilters()
    }

    fun sortByPrice(ascending: Boolean) {
        currentSortAscending = ascending
        applyFilters()
    }

    private fun applyFilters() {
        var result = allProductItems

        if (searchQuery.isNotBlank()) {
            result = result.filter { prod ->
                prod.name.contains(searchQuery, ignoreCase = true) ||
                        prod.description.contains(searchQuery, ignoreCase = true) ||
                        prod.categories.any { cat -> cat.contains(searchQuery, ignoreCase = true) }
            }
        }

        if (selectedCategory != "Todas") {
            result = result.filter { prod ->
                prod.categories.any { category ->
                    category.equals(selectedCategory, ignoreCase = true)
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
        val current = _productQuantities[productId] ?: 0
        _productQuantities[productId] = current + 1
    }

    fun decreaseQuantity(productId: String) {
        val current = _productQuantities[productId] ?: 1
        if (current > 1) {
            _productQuantities[productId] = current - 1
        }
    }

    fun resetLastAddedProduct() {
        _lastAddedProductItem.value = null
    }
}
