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
import com.example.ecommerceapp.presentation.view.viewmodel.CartViewModel
import androidx.compose.runtime.getValue
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import com.example.ecommerceapp.presentation.view.components.ProductItemOnCart
import com.example.ecommerceapp.presentation.view.components.layout.MainLayout
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.ecommerceapp.R

@Composable
fun CartScreen(
    navController: NavHostController,
    onCheckoutSuccess: () -> Unit,
    cartViewModel: CartViewModel
) {
    val cartItems by cartViewModel.cartItems.collectAsState()
    val totalProducts by cartViewModel.totalProducts.collectAsState()
    val totalPrice by cartViewModel.totalPrice.collectAsState()
    val checkoutSuccess = cartViewModel.checkoutSuccess

    LaunchedEffect(Unit) { cartViewModel.loadCart() }
    LaunchedEffect(checkoutSuccess) { if (checkoutSuccess) onCheckoutSuccess() }

    MainLayout(
        navController = navController,
        selectedItem = "cart",
        cartViewModel = cartViewModel,
        topBarMessage = stringResource(id = R.string.cart_title),
        mainContent = {
            if (totalProducts == 0) {
                EmptyCartScreen(navController)
            } else {
                Column(
                    Modifier
                        .fillMaxSize()
                        .padding(
                            start = 10.dp,
                            end = 10.dp,
                            bottom = 6.dp
                        ),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                    ) {
                        items(cartItems) { cartItem ->
                            ProductItemOnCart(
                                cartItem = cartItem,
                                onIncrease = { cartViewModel.increaseQuantity(cartItem.productItem) },
                                onDecrease = { cartViewModel.decreaseQuantity(cartItem.productItem) },
                                isDeleting = false,
                                onDeleteProduct = { cartViewModel.removeFromCart(cartItem.productItem) }
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 4.dp),
                        elevation = CardDefaults.cardElevation(4.dp),
                        shape = MaterialTheme.shapes.medium,
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceContainerLow,
                            contentColor = MaterialTheme.colorScheme.onSurface
                        )
                    ) {
                        Column(Modifier.padding(16.dp)) {
                            Text(
                                text = stringResource(id = R.string.cart_summary_title),
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Medium,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Spacer(Modifier.height(10.dp))
                            Text(
                                text = stringResource(id = R.string.total_products, totalProducts),
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Spacer(Modifier.height(5.dp))
                            Text(
                                text = stringResource(id = R.string.total_price, totalPrice),
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        FilledTonalButton(
                            onClick = { cartViewModel.clearCart() },
                            modifier = Modifier.weight(1f),
                            shape = MaterialTheme.shapes.medium
                        ) {
                            Text(
                                text = stringResource(id = R.string.clear_cart),
                                letterSpacing = 0.1.sp
                            )
                        }

                        Button(
                            onClick = { cartViewModel.finalizeOrder() },
                            modifier = Modifier.weight(1f),
                            shape = MaterialTheme.shapes.medium
                        ) {
                            Text(text = stringResource(id = R.string.buy_cart))
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
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = stringResource(id = R.string.empty_cart_message),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    navController.navigate("products") {
                        launchSingleTop = true
                        restoreState = true
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                    }
                },
                shape = MaterialTheme.shapes.medium
            ) {
                Text(text = stringResource(id = R.string.go_to_shop))
            }
        }
    }
}


