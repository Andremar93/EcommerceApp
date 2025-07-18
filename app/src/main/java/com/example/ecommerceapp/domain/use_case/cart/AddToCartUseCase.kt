package com.example.ecommerceapp.domain.use_case.cart

import com.example.ecommerceapp.domain.model.ProductItem
import com.example.ecommerceapp.domain.repository.CartRepository
import javax.inject.Inject

class AddToCartUseCase @Inject constructor(
    private val cartRepository: CartRepository
) {
    suspend operator fun invoke(productItem: ProductItem, quantity: Int): Boolean {
        return cartRepository.addToCart(productItem, quantity)
    }
}
