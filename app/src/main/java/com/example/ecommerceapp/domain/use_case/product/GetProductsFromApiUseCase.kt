package com.example.ecommerceapp.domain.use_case.product

import com.example.ecommerceapp.domain.model.Product
import com.example.ecommerceapp.domain.repository.ProductRepository
import javax.inject.Inject

class GetProductsFromApiUseCase @Inject constructor(private val repo: ProductRepository) {
    suspend operator fun invoke(): List<Product> = repo.getProductsFromApi()
}
