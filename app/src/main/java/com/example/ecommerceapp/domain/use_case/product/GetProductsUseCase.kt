package com.example.ecommerceapp.domain.use_case.product

import com.example.ecommerceapp.domain.model.ProductItem
import com.example.ecommerceapp.domain.repository.ProductRepository
import javax.inject.Inject

class GetProductsUseCase @Inject constructor(
    private val repository: ProductRepository
) {
    suspend operator fun invoke(refreshData: Boolean = false): List<ProductItem> =
        repository.getProducts(refreshData)
}
