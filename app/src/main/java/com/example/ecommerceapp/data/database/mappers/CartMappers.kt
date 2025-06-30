package com.example.ecommerceapp.data.database.mappers

import com.example.ecommerceapp.data.database.entities.CartEntity
import com.example.ecommerceapp.domain.model.CartItem
import com.example.ecommerceapp.domain.model.Product


fun CartEntity.toDomain(product: Product): CartItem {
    return CartItem(product = product, quantity = this.quantity)
}

fun CartItem.toEntity(): CartEntity {
    return CartEntity(
        productId = this.product.id,
        quantity = this.quantity
    )
}
