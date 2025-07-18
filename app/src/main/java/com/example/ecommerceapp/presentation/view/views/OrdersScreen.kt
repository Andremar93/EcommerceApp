package com.example.ecommerceapp.presentation.view.views

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.ecommerceapp.R
import com.example.ecommerceapp.domain.model.OrderItem
import com.example.ecommerceapp.presentation.view.components.UIState
import com.example.ecommerceapp.presentation.view.components.layout.MainLayout
import com.example.ecommerceapp.presentation.view.viewmodel.OrderViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun OrdersScreen(navController: NavHostController) {

    val orderViewModel: OrderViewModel = hiltViewModel()

    val state = orderViewModel.uiState

    LaunchedEffect(Unit) {
        orderViewModel.getOrders()
    }

    MainLayout(
        navController = navController,
        topBarMessage = stringResource(id = R.string.my_orders),
        showTopBar = true,
        showBottomBar = true,
        mainContent = {
            when (state) {
                is UIState.Loading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(
                            text = stringResource(R.string.loading_orders),
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(16.dp)
                        )
                        CircularProgressIndicator(
                            modifier = Modifier.size(50.dp),
                            color = MaterialTheme.colorScheme.primary,
                            strokeWidth = 4.dp
                        )
                    }
                }

                is UIState.Success -> {
                    val orders = state.data
                    if (orders.isEmpty()) {
                        Text(
                            text = stringResource(R.string.no_orders),
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(16.dp)
                        )
                    } else {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 6.dp)
                        ) {
                            items(orders) { order ->
                                OrderCard(order)
                            }
                        }
                    }
                }

                is UIState.Error -> {
                    Text(
                        text = stringResource(R.string.order_error, state.message),
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }

        }
    )
}

@Composable
fun OrderCard(orderItem: OrderItem) {
    val order = orderItem
    val items = order.items

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outline,
                shape = MaterialTheme.shapes.medium
            ),
        elevation = CardDefaults.cardElevation(3.dp),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        ),
    ) {
        Column(Modifier.padding(16.dp)) {
            Text(
                text = stringResource(R.string.order_id, order.orderId),
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.padding(top = 5.dp))

            Text(
                text = stringResource(R.string.order_date, formatDate(order.date)),
                style = MaterialTheme.typography.bodySmall
            )

            Spacer(modifier = Modifier.padding(top = 8.dp))

            Text(
                text = stringResource(R.string.order_products),
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.padding(top = 4.dp))

            items.forEach { item ->
                Text(
                    text = stringResource(
                        R.string.order_item,
                        item.productName,
                        item.quantity,
                        item.price * item.quantity
                    ),
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Spacer(modifier = Modifier.padding(top = 8.dp))

            Text(
                text = stringResource(R.string.order_total, order.totalAmount),
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.align(Alignment.End)
            )
        }
    }
}

fun formatDate(timestamp: Long): String {
    val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
    return sdf.format(Date(timestamp))
}
