package com.example.ecommerceapp.data.remote.mappers

import com.example.ecommerceapp.domain.model.ProductFromAPI
import com.example.ecommerceapp.domain.model.Product

fun ProductFromAPI.toDomain(): Product {
    return Product(
        id = this._id,
        name = this.name,
        price = this.price,
        description = this.description,
        hasDrink = this.hasDrink,
        imageUrl = this.imageUrl,
        categories = this.categories
    )
}
