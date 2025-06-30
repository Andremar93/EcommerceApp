package com.example.ecommerceapp.data.products


import com.example.ecommerceapp.data.database.dao.ProductDao
import com.example.ecommerceapp.data.remote.ProductApiService
import com.example.ecommerceapp.data.database.mappers.toDomain
import com.example.ecommerceapp.data.database.mappers.toEntity
import com.example.ecommerceapp.data.remote.mappers.toDomain
import com.example.ecommerceapp.domain.model.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ProductDataSourceImpl @Inject constructor(
    private val apiService: ProductApiService,
    private val dao: ProductDao
) : ProductsDataSource {

    override suspend fun getAllProducts(): List<Product> = withContext(Dispatchers.IO) {
        apiService.getAllProducts().map { it.toDomain() }
    }

    override fun observeCachedProducts(): Flow<List<Product>> {
        return dao.getProducts().map { list -> list.map { it.toDomain() } }
    }

    override suspend fun cacheProducts(products: List<Product>) {
        dao.clearAll()
        dao.insertAll(products.map { it.toEntity() })
    }
}