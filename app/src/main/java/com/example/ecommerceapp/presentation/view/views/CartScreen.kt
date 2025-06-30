package com.example.ecommerceapp.presentation.view.views

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.ecommerceapp.presentation.view.viewmodel.CartViewModel
import androidx.compose.runtime.getValue
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import com.example.ecommerceapp.presentation.view.components.ProductItemOnCart
import com.example.ecommerceapp.presentation.view.components.layout.MainLayout

@Composable
fun CartScreen(
    onBack: () -> Unit,
    navController: NavHostController,
    onCheckoutSuccess: () -> Unit
) {

    val cartViewModel: CartViewModel = hiltViewModel()
    val cartItems by cartViewModel.cartItems.collectAsState()
    val totalProducts by cartViewModel.totalProducts.collectAsState()
    val totalPrice by cartViewModel.totalPrice.collectAsState()

    var checkoutSuccess = cartViewModel.checkoutSuccess

    LaunchedEffect(checkoutSuccess) {
        if (checkoutSuccess) {
            onCheckoutSuccess()
        }
    }

    MainLayout(
        navController = navController,
        selectedItem = "cart",
        topBarMessage = "Tu Carrito",
        mainContent = {
            if (totalProducts == 0) {
                EmptyCartScreen(navController)
            } else {
                Column(
                    Modifier
                        .padding(horizontal = 12.dp)
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.SpaceBetween,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                    ) {
                        items(cartItems) { cartItem ->
                            ProductItemOnCart(
                                cartItem = cartItem,
                                onIncrease = {
                                    cartViewModel.increaseQuantity(
                                        cartItem.product,
                                    )
                                },
                                onDecrease = {
                                    cartViewModel.decreaseQuantity(
                                        cartItem.product,
                                    )
                                },
                                isDeleting = false,
                                onDeleteProduct = { cartViewModel.removeFromCart(cartItem.product) }
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }

                    Column {
                        Spacer(modifier = Modifier.height(16.dp))

                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                                .border(
                                    width = 1.dp,
                                    color = MaterialTheme.colorScheme.outline,
                                    shape = MaterialTheme.shapes.medium
                                ),
                            elevation = CardDefaults.cardElevation(4.dp),
                            shape = MaterialTheme.shapes.medium,
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surface,
                                contentColor = MaterialTheme.colorScheme.onSurface
                            ),
                        ) {
                            Column(Modifier.padding(16.dp)) {
                                Text(
                                    "Resumen de la compra",
                                    style = MaterialTheme.typography.titleMedium
                                )
                                Spacer(Modifier.height(8.dp))
                                Text("Total de productos: $totalProducts")
                                Text("Total general: $${"%.2f".format(totalPrice)}")
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                                .align(Alignment.End),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Button(onClick = { cartViewModel.clearCart() }, modifier = Modifier) {
                                Text("Limpiar Carrito")
                            }

                            Button(
                                onClick = { cartViewModel.finalizeOrder() },
                                modifier = Modifier
                            ) {
                                Text("Comprar Carrito")
                            }

                        }
                    }
                }
            }
        }
    )
}

@Composable
fun EmptyCartScreen(navController: NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "No hay productos en tu carrito"
            )
            Button(onClick = {
                navController.navigate("products") {
                    launchSingleTop = true
                    restoreState = true
                    popUpTo(navController.graph.startDestinationId) {
                        saveState = true
                    }
                }

            }) {
                Text("Ir a comprar")
            }
        }

    }
}
