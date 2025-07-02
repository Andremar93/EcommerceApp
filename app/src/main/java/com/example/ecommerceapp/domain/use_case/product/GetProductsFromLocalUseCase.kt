package com.example.ecommerceapp.domain.use_case.product

import com.example.ecommerceapp.domain.model.Product
import com.example.ecommerceapp.domain.repository.ProductRepository
import javax.inject.Inject

class GetProductsFromLocalUseCase @Inject constructor(
    private val repository: ProductRepository
) {
    suspend operator fun invoke(): List<Product> {
        return repository.getProductsFromLocal()
    }
}