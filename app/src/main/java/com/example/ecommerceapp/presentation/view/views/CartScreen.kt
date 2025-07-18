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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.ecommerceapp.R

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

    LaunchedEffect(Unit) {
        cartViewModel.loadCart()
    }

    LaunchedEffect(checkoutSuccess) {
        if (checkoutSuccess) {
            onCheckoutSuccess()
        }
    }

    MainLayout(
        navController = navController,
        selectedItem = "cart",
        topBarMessage =  stringResource(id = R.string.buy_cart),
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
                                        cartItem.productItem,
                                    )
                                },
                                onDecrease = {
                                    cartViewModel.decreaseQuantity(
                                        cartItem.productItem,
                                    )
                                },
                                isDeleting = false,
                                onDeleteProduct = { cartViewModel.removeFromCart(cartItem.productItem) }
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
                                    text = stringResource(id = R.string.cart_summary_title),
                                    style = MaterialTheme.typography.titleMedium
                                )
                                Spacer(Modifier.height(8.dp))
                                Text(stringResource(id = R.string.total_products, totalProducts))
                                Text(stringResource(id = R.string.total_price, totalPrice))
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                                .align(Alignment.End),
                            horizontalArrangement = Arrangement.SpaceAround,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Button(onClick = { cartViewModel.clearCart() }, modifier = Modifier) {
                                Text(text = stringResource(id = R.string.clear_cart))
                            }

                            Button(
                                onClick = { cartViewModel.finalizeOrder() },
                                modifier = Modifier
                            ) {
                                Text(text = stringResource(id = R.string.buy_cart))
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
            .fillMaxSize(),
//            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = stringResource(id = R.string.empty_cart_message)
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
                Text(text = stringResource(id = R.string.go_to_shop))
            }
        }
    }
}

