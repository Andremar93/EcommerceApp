package com.example.ecommerceapp.data.repository

import com.example.ecommerceapp.data.local.dao.ProductDao
import com.example.ecommerceapp.data.remote.ProductApiService
import com.example.ecommerceapp.domain.model.Product
import com.example.ecommerceapp.domain.repository.ProductRepository
import com.example.ecommerceapp.data.local.entity.toEntity

class ProductRepositoryImpl(
    private val api: ProductApiService,
    private val dao: ProductDao
) : ProductRepository {

    override suspend fun getProductsFromApi(): List<Product> {
        return api.getProducts().map { it.toDomain() }
    }

    override suspend fun getProductsFromLocal(): List<Product> {
        return dao.getAll().map { it.toDomain() }
    }

    override suspend fun saveProducts(products: List<Product>) {
        dao.insertAll(products.map { it.toEntity() })
    }
}
