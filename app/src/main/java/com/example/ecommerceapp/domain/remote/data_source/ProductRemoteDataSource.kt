package com.example.ecommerceapp.domain.remote.data_source

import com.example.ecommerceapp.data.model.products.ProductDto

interface ProductRemoteDataSource {
    suspend fun getProducts(): List<ProductDto>
}
