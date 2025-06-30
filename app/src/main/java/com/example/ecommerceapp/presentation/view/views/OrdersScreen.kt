package com.example.ecommerceapp.presentation.view.views

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.ecommerceapp.domain.model.Order
import com.example.ecommerceapp.presentation.view.components.layout.MainLayout
import com.example.ecommerceapp.presentation.view.viewmodel.OrderViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun OrdersScreen(navController: NavHostController) {

    val orderViewModel: OrderViewModel = hiltViewModel()
    val orders = orderViewModel.orders.collectAsState(initial = emptyList())
    MainLayout(
        navController = navController,
        topBarMessage = "Mis Ordenes",
        showTopBar = true,
        showBottomBar = false,
        mainContent = {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                items(orders.value) { order ->
                    OrderCard(order)
                }
            }
        }
    )
}

@Composable
fun OrderCard(order: Order) {
    val order = order
    val items = order.items

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(Modifier.padding(16.dp)) {
            Text(
                text = "Orden #${order.id}",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.padding(top = 4.dp))

            Text(
                text = "Fecha: ${formatDate(order.date)}",
                style = MaterialTheme.typography.bodySmall
            )

            Spacer(modifier = Modifier.padding(top = 8.dp))

            Text(
                text = "Productos:",
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.padding(top = 4.dp))

            items.forEach { item ->
                Text(
                    text = "• ${item.productName} x${item.quantity} - $${item.price * item.quantity}",
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Spacer(modifier = Modifier.padding(top = 8.dp))

            Text(
                text = "Total: $${order.totalAmount}",
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
