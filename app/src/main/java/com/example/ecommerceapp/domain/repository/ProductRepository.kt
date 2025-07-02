package com.example.ecommerceapp.domain.repository

import com.example.ecommerceapp.domain.model.Product

interface ProductRepository {
    suspend fun getProductsFromApi(): List<Product>
    suspend fun getProductsFromLocal(): List<Product>
    suspend fun saveProducts(products: List<Product>)
}
