package com.example.ecommerceapp.presentation.view.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.material3.Icon
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.ecommerceapp.R

@Composable
fun AddToCartButton(
    onClick: () -> Unit,
    isAdding: Boolean
) {
    val scale = remember { Animatable(1f) }

    LaunchedEffect(isAdding) {
        if (isAdding) {
            scale.animateTo(1.15f, tween(120))
            scale.animateTo(1f, tween(120))
        }
    }

    IconButton(
        onClick = onClick,
        enabled = !isAdding,
        modifier = Modifier
            .size(36.dp)
            .graphicsLayer(
                scaleX = scale.value,
                scaleY = scale.value
            )
            .background(
                color = MaterialTheme.colorScheme.primaryContainer,
                shape = CircleShape
            )
    ) {
        Icon(
            imageVector = Icons.Default.ShoppingCart,
            contentDescription = stringResource(id = R.string.contend_description_add_to_cart),
            tint = MaterialTheme.colorScheme.onPrimaryContainer
        )
    }
}
