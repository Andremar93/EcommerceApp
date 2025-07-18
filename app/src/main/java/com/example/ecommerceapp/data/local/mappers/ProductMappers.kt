package com.example.ecommerceapp.data.local.mappers

import com.example.ecommerceapp.data.local.entity.ProductEntity
import com.example.ecommerceapp.data.model.products.ProductDto
import com.example.ecommerceapp.domain.model.ProductItem

fun ProductEntity.toDomain(): ProductItem {
    return ProductItem(id, name, description, price, hasDrink, imageUrl, categories)
}

fun ProductItem.toEntity(): ProductEntity {
    return ProductEntity(id, name, description, price, hasDrink, imageUrl, categories)
}

fun ProductDto.toEntity(): ProductEntity {
    return ProductEntity(
        id = id,
        name = name,
        description = description,
        price = price,
        hasDrink = hasDrink,
        imageUrl = imageUrl,
        categories = categories
    )
}

fun ProductDto.toDomain(): ProductItem {
    return ProductItem(
        id = id,
        name = name,
        description = description,
        price = price,
        hasDrink = hasDrink,
        imageUrl = imageUrl,
        categories = categories
    )
}



