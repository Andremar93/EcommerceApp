package com.example.ecommerceapp.data.products

import com.example.ecommerceapp.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface ProductsDataSource {

    suspend fun getAllProducts(): List<Product>

    fun observeCachedProducts(): Flow<List<Product>>

    suspend fun cacheProducts(products: List<Product>)

}