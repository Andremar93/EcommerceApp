package com.example.ecommerceapp.domain.use_case.order

import com.example.ecommerceapp.domain.model.OrderItem
import com.example.ecommerceapp.domain.repository.OrdersRepository
import javax.inject.Inject

class GetOrdersUseCase @Inject constructor(
    private val ordersRepository: OrdersRepository
) {
    suspend operator fun invoke(userId: String) : List<OrderItem> {
        return ordersRepository.getOrders(userId)
    }
}