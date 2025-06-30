package com.example.ecommerceapp.presentation.view.views

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
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

@Composable
fun ProductListScreen(
    navController: NavHostController,
) {

    val productViewModel: ProductListViewModel = hiltViewModel()

    val products by productViewModel.filteredProducts
    var searchQuery by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("Todas") }
    var sortAscending by remember { mutableStateOf(true) }

    val productQuantities = productViewModel.productQuantities

    val categories = listOf(
        "Todas",
        "Mexicana",
        "Desayunos",
        "Americana",
        "Italiana",
        "Japonesa",
        "Plato Principal",
        "Ensaladas",
        "Mariscos"
    )

    val lastAddedProduct by productViewModel.lastAddedProduct
    val context = LocalContext.current

    val isLoading by productViewModel.isLoading
    val rest = products.drop(12)

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
            if (isLoading) {
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

            } else {
                LazyColumn {
                    item {
                        ProductFilterBar(
                            searchQuery = searchQuery,
                            selectedCategory = selectedCategory,
                            sortAscending = sortAscending,
                            onSearchQueryChange = {
                                searchQuery = it
                                productViewModel.filter(it)
                            },
                            onCategorySelected = {
                                selectedCategory = it
                                productViewModel.filterByCategory(it)
                            },
                            onSortChange = {
                                sortAscending = it
                                productViewModel.sortByPrice(it)
                            },
                            categories = categories,
                        )
                        ProductListContent(
                            products = products,
                            productQuantities = productQuantities,
                            onIncrease = productViewModel::increaseQuantity,
                            onDecrease = productViewModel::decreaseQuantity,
                            onAddToCart = { product ->
                                val quantity = productQuantities[product.id] ?: 1
                                productViewModel.addToCart(product, quantity)
                            },
                            isAdding = productViewModel.isAddingToCart.value
                        )

                        //Esta sección no deberia ser construida de esta manera es solo para ejemplificar
                        // otras secciones en esta screen
                        Text(
                            text = "Cerca de ti",
                            fontWeight = FontWeight.Bold,
                            fontSize = 30.sp,
                            modifier = Modifier.padding(top = 12.dp)
                        )
                        ProductListContent(
                            products = rest,
                            productQuantities = productQuantities,
                            onIncrease = productViewModel::increaseQuantity,
                            onDecrease = productViewModel::decreaseQuantity,
                            onAddToCart = { product ->
                                val quantity = productQuantities[product.id] ?: 1
                                productViewModel.addToCart(product, quantity)
                            },
                            isAdding = productViewModel.isAddingToCart.value
                        )
                    }
                }
            }

        }
    )
}

@Composable
fun ProductListContent(
    products: List<Product>,
    productQuantities: Map<String, Int>,
    onIncrease: (String) -> Unit,
    onDecrease: (String) -> Unit,
    onAddToCart: (Product) -> Unit,
    isAdding: Boolean
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
                    isAdding = isAdding
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
        isAdding = false
    )


}
