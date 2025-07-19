package com.example.ecommerceapp.presentation.view.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.LocalDrink
import androidx.compose.material.icons.filled.Remove
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.ecommerceapp.domain.model.CartItem

@Composable
fun ProductItemOnCart(
    cartItem: CartItem,
    onIncrease: () -> Unit,
    onDecrease: () -> Unit,
    onDeleteProduct: () -> Unit,
    isDeleting: Boolean
) {

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
        Column(modifier = Modifier.padding(16.dp)) {

            Text(text = cartItem.productItem.name, style = MaterialTheme.typography.titleLarge)
            Text(
                text = cartItem.productItem.description,
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Precio: $${cartItem.productItem.price}",
                style = MaterialTheme.typography.bodyMedium
            )

            if (cartItem.productItem.hasDrink) {
                Spacer(modifier = Modifier.width(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "con bebida",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
            }


            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = 8.dp)
            ) {
                IconButton(onClick = onDecrease) {
                    Icon(Icons.Filled.Remove, contentDescription = "Disminuir")
                }
                Text("${cartItem.quantity}", style = MaterialTheme.typography.bodyLarge)
                IconButton(onClick = onIncrease) {
                    Icon(Icons.Filled.Add, contentDescription = "Aumentar")
                }

                Spacer(modifier = Modifier.weight(1f))

                IconButton(
                    onClick = onDeleteProduct,
                    enabled = !isDeleting
                ) {
                    Icon(Icons.Filled.Delete, contentDescription = "Eliminar")
                }

            }
        }
    }
}
