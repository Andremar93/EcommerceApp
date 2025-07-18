package com.example.ecommerceapp.domain.use_case.cart

import com.example.ecommerceapp.domain.repository.CartRepository

class GetCartItemCountUseCase(
    private val cartRepository: CartRepository
) {
    suspend operator fun invoke(): Int {
        return cartRepository.getCartItemCount()
    }
}