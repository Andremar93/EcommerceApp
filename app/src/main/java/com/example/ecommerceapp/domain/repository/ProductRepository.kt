package com.example.ecommerceapp.domain.repository

import com.example.ecommerceapp.domain.model.ProductItem

interface ProductRepository {
    suspend fun getProducts(refreshData: Boolean): List<ProductItem>
}
