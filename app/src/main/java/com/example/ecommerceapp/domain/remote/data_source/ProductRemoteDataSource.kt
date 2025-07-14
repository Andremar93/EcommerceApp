package com.example.ecommerceapp.domain.remote.data_source

import com.example.ecommerceapp.data.model.ProductDto

interface ProductRemoteDataSource {
    suspend fun getProducts(): List<ProductDto>
}
