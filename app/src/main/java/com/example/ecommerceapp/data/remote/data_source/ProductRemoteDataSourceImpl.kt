package com.example.ecommerceapp.data.remote.data_source

import com.example.ecommerceapp.data.model.ProductDto
import com.example.ecommerceapp.data.remote.ProductApiService
import com.example.ecommerceapp.domain.remote.data_source.ProductRemoteDataSource
import javax.inject.Inject

class ProductRemoteDataSourceImpl @Inject constructor(
    private val apiService: ProductApiService
) : ProductRemoteDataSource {

    override suspend fun getProducts(): List<ProductDto> {
        return apiService.getProducts()
    }
}