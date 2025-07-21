package com.example.ecommerceapp.data.local.mappers

import com.example.ecommerceapp.data.local.entity.CartEntity
import com.example.ecommerceapp.data.model.cart.CartWithProduct
import com.example.ecommerceapp.domain.model.CartItem
import com.example.ecommerceapp.domain.model.ProductItem

fun CartEntity.toDomain(productItem: ProductItem): CartItem {
    return CartItem(productItem = productItem, quantity = this.quantity)
}

fun CartItem.toEntity(): CartEntity {
    return CartEntity(
        productId = this.productItem.id,
        quantity = this.quantity
    )
}

fun CartWithProduct.toDomain(): CartItem {
    return CartItem(
        productItem = product.toDomain(),
        quantity = cartItem.quantity
    )
}


