package com.example.ecommerceapp.presentation.view.viewmodel

import android.util.Log
import com.example.ecommerceapp.domain.model.ProductItem
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommerceapp.domain.repository.CartRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.example.ecommerceapp.domain.use_case.product.GetProductsUseCase
import com.example.ecommerceapp.presentation.view.components.UIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import okio.IOException
import retrofit2.HttpException

@HiltViewModel
class ProductListViewModel @Inject constructor(
    private val getProductsUseCase: GetProductsUseCase,
    private val cartRepository: CartRepository
) : ViewModel() {

    var searchQuery by mutableStateOf("")
    var selectedCategory by mutableStateOf("Todas")
    var sortAscending by mutableStateOf(true)

    private val _allCategories = MutableStateFlow<List<String>>(emptyList())
    val allCategories: StateFlow<List<String>> = _allCategories

    var addingProductId by mutableStateOf<String?>(null)

    private val _filteredProducts = mutableStateOf<List<ProductItem>>(emptyList())
    val filteredProducts: State<List<ProductItem>> = _filteredProducts

    private var allProductItems: List<ProductItem> = emptyList()
    private var currentQuery = ""
    private var currentCategory: String? = null
    var currentSortAscending = true

    private val _productQuantities: SnapshotStateMap<String, Int> = mutableStateMapOf()
    val productQuantities: Map<String, Int> get() = _productQuantities

    private val _lastAddedProductItem = mutableStateOf<ProductItem?>(null)
    val lastAddedProductItem: State<ProductItem?> = _lastAddedProductItem

    var uiState by mutableStateOf<UIState<List<ProductItem>>>(UIState.Loading)

    fun loadProducts(refreshData: Boolean) {
        viewModelScope.launch {
            uiState = UIState.Loading
            try {

                val products = getProductsUseCase.invoke(refreshData)

                _allCategories.value =
                    listOf("Todas") + allProductItems.flatMap { it.categories }.distinct()

                applyFilters()
                uiState = UIState.Success(products)
                allProductItems = getProductsUseCase()


            } catch (e: IOException) {
                uiState = UIState.Error("Sin conexión a Internet")
                Log.e(
                    "ProductListViewModel",
                    "Sin conexión a Internet, error al cargar los productos",
                    e
                )
            } catch (e: HttpException) {
                uiState = UIState.Error("Error al cargar los productos: ${e.message}")
            } catch (e: Exception) {
                uiState = UIState.Error("Error inesperado: ${e.message ?: "Desconocido"}")
                Log.e("ProductListViewModel", "Error inesperado al cargar los productos", e)
            }
        }
    }

    fun addToCart(productItem: ProductItem, quantity: Int) {
        viewModelScope.launch {
            addingProductId = productItem.id
            delay(300)
            if (cartRepository.addToCart(productItem, quantity)) {
                _lastAddedProductItem.value = productItem
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
        var result = allProductItems
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
        _lastAddedProductItem.value = null
    }

}