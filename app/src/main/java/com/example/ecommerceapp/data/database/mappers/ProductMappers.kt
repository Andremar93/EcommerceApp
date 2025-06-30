package com.example.ecommerceapp.data.database.mappers

import com.example.ecommerceapp.data.database.entities.ProductEntity
import com.example.ecommerceapp.domain.model.Product

fun ProductEntity.toDomain(): Product {
    return Product(id, name, description, price, hasDrink, imageUrl, categories)
}

fun Product.toEntity(): ProductEntity {
    return ProductEntity(id, name, description, price, hasDrink, imageUrl, categories)
}

