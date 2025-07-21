package com.example.ecommerceapp.presentation.view.views

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.ecommerceapp.presentation.view.components.ProductFilterBar
import com.example.ecommerceapp.presentation.view.viewmodel.ProductListViewModel
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import com.example.ecommerceapp.domain.model.ProductItem
import com.example.ecommerceapp.presentation.view.components.ProductItem
import com.example.ecommerceapp.presentation.view.components.layout.MainLayout
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.example.ecommerceapp.presentation.view.components.UIState
import androidx.compose.ui.res.stringResource
import com.example.ecommerceapp.R
import com.example.ecommerceapp.presentation.view.viewmodel.CartViewModel

@Composable
fun ProductListScreen(
    navController: NavHostController,
    cartViewModel: CartViewModel
) {
    val productViewModel: ProductListViewModel = hiltViewModel()

    val productQuantities = productViewModel.productQuantities
    val lastAddedProduct by productViewModel.lastAddedProductItem
    val state = productViewModel.uiState
    val context = LocalContext.current
    val addingProductId = productViewModel.addingProductId
    val categories by productViewModel.allCategories.collectAsState()

    val searchQuery = productViewModel.searchQuery
    val selectedCategory = productViewModel.selectedCategory
    val sortAscending = productViewModel.currentSortAscending

    LaunchedEffect(Unit) {
        productViewModel.loadProducts(refreshData = false)
    }

    LaunchedEffect(lastAddedProduct) {
        lastAddedProduct?.let {
            Toast.makeText(
                context,
                context.getString(R.string.product_added_to_cart, it.name),
                Toast.LENGTH_SHORT
            ).show()
        }
        productViewModel.resetLastAddedProduct()
    }

    MainLayout(
        navController = navController,
        selectedItem = "products",
        cartViewModel = cartViewModel,
        showTopBar = false,
        mainContent = {
            Column(modifier = Modifier.padding(8.dp)) {
                ProductFilterBar(
                    searchQuery = searchQuery,
                    selectedCategory = selectedCategory,
                    sortAscending = sortAscending,
                    categories = categories,
                    onSearchQueryChange = {
                        productViewModel.filter(it)
                    },
                    onCategorySelected = {
                        productViewModel.filterByCategory(it)
                    },
                    onSortChange = {
                        productViewModel.sortByPrice(it)
                    }
                )

                Spacer(modifier = Modifier.height(8.dp))

                when (state) {
                    is UIState.Loading -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(50.dp),
                                color = MaterialTheme.colorScheme.primary,
                                strokeWidth = 4.dp
                            )
                        }
                    }

                    is UIState.Success -> {
                        val products by productViewModel.filteredProducts
                        if (products.isEmpty()) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = stringResource(id = R.string.no_products_found),
                                    style = MaterialTheme.typography.bodyLarge
                                )
                            }
                        } else {
                            ProductGridContent(
                                productItems = products,
                                productQuantities = productQuantities,
                                onIncrease = productViewModel::increaseQuantity,
                                onDecrease = productViewModel::decreaseQuantity,
                                onAddToCart = { product ->
                                    val quantity = productQuantities[product.id] ?: 1
                                    productViewModel.addToCart(product, quantity)
                                },
                                isAddingProductId = addingProductId
                            )
                        }
                    }

                    is UIState.Error -> {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            Text(
                                text = state.message,
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                    }
                }
            }
        }
    )
}



@Composable
fun ProductGridContent(
    productItems: List<ProductItem>,
    productQuantities: Map<String, Int>,
    onIncrease: (String) -> Unit,
    onDecrease: (String) -> Unit,
    onAddToCart: (ProductItem) -> Unit,
    isAddingProductId: String?
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(6.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(
            items = productItems,
            key = { it.id }
        ) { product ->
            val quantity = productQuantities[product.id] ?: 1
            ProductItem(
                productItem = product,
                onIncrease = { onIncrease(product.id) },
                onDecrease = { onDecrease(product.id) },
                onAddToCart = { onAddToCart(product) },
                quantity = quantity,
                isBeingAdded = product.id == isAddingProductId
            )
        }
    }
}


@Composable
fun ProductListContent(
    productItems: List<ProductItem>,
    productQuantities: Map<String, Int>,
    onIncrease: (String) -> Unit,
    onDecrease: (String) -> Unit,
    onAddToCart: (ProductItem) -> Unit,
    isAddingProductId: String?
) {
    Column(modifier = Modifier.padding(4.dp)) {
        LazyRow {
            items(productItems) { product ->
                val quantity = productQuantities[product.id] ?: 1
                ProductItem(
                    productItem = product,
                    onIncrease = { onIncrease(product.id) },
                    onDecrease = { onDecrease(product.id) },
                    onAddToCart = { onAddToCart(product) },
                    quantity = quantity,
                    isBeingAdded = product.id == isAddingProductId
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ProductListContentPreview() {
    val sampleProductItem = ProductItem(
        id = "1",
        name = "Producto Demo",
        description = "Descripción de prueba",
        price = 99.99,
//        category = "Tecnología",
        imageUrl = "https://comedera.com/wp-content/uploads/sites/9/2017/08/tacos-al-pastor-receta.jpg",
        hasDrink = false,
    )

    val sampleQuantities = mapOf(sampleProductItem.id to 2)

    ProductListContent(
        productItems = listOf(sampleProductItem, sampleProductItem),
        productQuantities = sampleQuantities,
        onIncrease = {},
        onDecrease = {},
        onAddToCart = {},
        isAddingProductId = ""
    )


}
