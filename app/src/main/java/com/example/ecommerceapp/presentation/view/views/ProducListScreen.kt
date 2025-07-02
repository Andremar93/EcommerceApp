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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.ecommerceapp.presentation.view.components.ProductFilterBar
import com.example.ecommerceapp.presentation.view.viewmodel.ProductListViewModel
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import com.example.ecommerceapp.domain.model.Product
import com.example.ecommerceapp.presentation.view.components.ProductItem
import com.example.ecommerceapp.presentation.view.components.layout.MainLayout
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

@Composable
fun ProductListScreen(
    navController: NavHostController,
) {

    val productViewModel: ProductListViewModel = hiltViewModel()

    val products by productViewModel.filteredProducts
    val productQuantities = productViewModel.productQuantities
    val lastAddedProduct by productViewModel.lastAddedProduct
    val isLoading by productViewModel.isLoading
    val context = LocalContext.current
    val addingProductId = productViewModel.addingProductId

    val searchQuery = productViewModel.searchQuery
    val selectedCategory = productViewModel.selectedCategory
    var sortAscending by remember { mutableStateOf(productViewModel.currentSortAscending) }

    val categories by productViewModel.allCategories.collectAsState()

    LaunchedEffect(lastAddedProduct) {
        lastAddedProduct?.let {
            Toast.makeText(context, "Producto ${it.name} agregado al carrito", Toast.LENGTH_SHORT)
                .show()
        }
        productViewModel.resetLastAddedProduct()
    }

    MainLayout(
        navController = navController,
        selectedItem = "products",
        showTopBar = false,
        mainContent = {
            when {
                isLoading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(50.dp),
                            color = MaterialTheme.colorScheme.primary,
                            strokeWidth = 4.dp
                        )
                    }
                }

                products.isEmpty() -> {
                    Column(modifier = Modifier.padding(8.dp)) {
                        ProductFilterBar(
                            searchQuery = searchQuery,
                            selectedCategory = selectedCategory,
                            sortAscending = sortAscending,
                            onSearchQueryChange = {
                                productViewModel.searchQuery = it
                                productViewModel.filter(it)
                            },
                            onCategorySelected = {
                                productViewModel.selectedCategory = it
                                productViewModel.filterByCategory(it)
                            },
                            onSortChange = {
                                sortAscending = it
                                productViewModel.sortByPrice(it)
                            },
                            categories = categories,
                        )

                        Spacer(modifier = Modifier.height(8.dp))
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                "No se encontraron productos",
                                style = MaterialTheme.typography.bodyLarge
                            )

                        }
                    }
                }

                else -> {
                    Column(modifier = Modifier.padding(8.dp)) {
                        ProductFilterBar(
                            searchQuery = searchQuery,
                            selectedCategory = selectedCategory,
                            sortAscending = sortAscending,
                            onSearchQueryChange = {
                                productViewModel.searchQuery = it
                                productViewModel.filter(it)
                            },
                            onCategorySelected = {
                                productViewModel.selectedCategory = it
                                productViewModel.filterByCategory(it)
                            },
                            onSortChange = {
                                sortAscending = it
                                productViewModel.sortByPrice(it)
                            },
                            categories = categories,
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        ProductGridContent(
                            products = products,
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
            }
        }
    )
}


@Composable
fun ProductGridContent(
    products: List<Product>,
    productQuantities: Map<String, Int>,
    onIncrease: (String) -> Unit,
    onDecrease: (String) -> Unit,
    onAddToCart: (Product) -> Unit,
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
            items = products,
            key = { it.id }
        ) { product ->
            val quantity = productQuantities[product.id] ?: 1
            ProductItem(
                product = product,
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
    products: List<Product>,
    productQuantities: Map<String, Int>,
    onIncrease: (String) -> Unit,
    onDecrease: (String) -> Unit,
    onAddToCart: (Product) -> Unit,
    isAddingProductId: String?
) {
    Column(modifier = Modifier.padding(4.dp)) {
        LazyRow {
            items(products) { product ->
                val quantity = productQuantities[product.id] ?: 1
                ProductItem(
                    product = product,
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
    val sampleProduct = Product(
        id = "1",
        name = "Producto Demo",
        description = "Descripción de prueba",
        price = 99.99,
//        category = "Tecnología",
        imageUrl = "https://comedera.com/wp-content/uploads/sites/9/2017/08/tacos-al-pastor-receta.jpg",
        hasDrink = false,
    )

    val sampleQuantities = mapOf(sampleProduct.id to 2)

    ProductListContent(
        products = listOf(sampleProduct, sampleProduct),
        productQuantities = sampleQuantities,
        onIncrease = {},
        onDecrease = {},
        onAddToCart = {},
        isAddingProductId = ""
    )


}
