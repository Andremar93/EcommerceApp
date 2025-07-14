package com.example.ecommerceapp.domain.local.data_source

import com.example.ecommerceapp.domain.model.ProductItem

interface ProductLocalDataSource {
    suspend fun saveProducts(products: List<ProductItem>)
    suspend fun clearProducts()
    suspend fun getProducts(): List<ProductItem>
}
