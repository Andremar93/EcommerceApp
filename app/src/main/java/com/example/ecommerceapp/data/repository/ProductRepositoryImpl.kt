package com.example.ecommerceapp.data.repository

import com.example.ecommerceapp.domain.model.ProductItem
import com.example.ecommerceapp.domain.repository.ProductRepository

import com.example.ecommerceapp.data.local.mappers.toDomain
import com.example.ecommerceapp.data.model.ProductDto
import com.example.ecommerceapp.domain.local.data_source.ProductLocalDataSource
import com.example.ecommerceapp.domain.remote.data_source.ProductRemoteDataSource
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val remote: ProductRemoteDataSource,
    private val local: ProductLocalDataSource,
) : ProductRepository {

    override suspend fun getProducts(refreshData: Boolean): List<ProductItem> {
        return if (refreshData) {
            val remoteProducts: List<ProductDto> = remote.getProducts()
            local.clearProducts()
            local.saveProducts(remoteProducts.map { it.toDomain() })
            remoteProducts.map { it.toDomain() }
        } else {
            val localProducts: List<ProductItem> = local.getProducts()
            if (localProducts.isNotEmpty()) {
                localProducts.map { it }
            } else {
                val remoteProducts = remote.getProducts()
                local.saveProducts(remoteProducts.map { it.toDomain() })
                remoteProducts.map { it.toDomain() }
            }
        }
    }

}
