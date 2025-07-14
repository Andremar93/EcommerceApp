package com.example.ecommerceapp.data.local.data_source

import com.example.ecommerceapp.data.local.dao.ProductDao
import com.example.ecommerceapp.domain.model.ProductItem
import com.example.ecommerceapp.data.local.mappers.toDomain
import com.example.ecommerceapp.data.local.mappers.toEntity
import com.example.ecommerceapp.domain.local.data_source.ProductLocalDataSource

import javax.inject.Inject

class ProductLocalDataSourceImpl @Inject constructor(
    private val dao: ProductDao
) : ProductLocalDataSource {
    override suspend fun saveProducts(products: List<ProductItem>) {
        dao.insertProducts(products.map { it.toEntity() })
    }

    override suspend fun clearProducts() {
        dao.clearProducts()
    }

    override suspend fun getProducts(): List<ProductItem> {
        return dao.getProducts().map { it.toDomain() }
    }
}