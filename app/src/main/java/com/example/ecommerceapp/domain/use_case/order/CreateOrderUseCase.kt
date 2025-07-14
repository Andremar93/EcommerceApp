package com.example.ecommerceapp.domain.use_case.order

import com.example.ecommerceapp.domain.model.OrderItem
import com.example.ecommerceapp.domain.model.OrderItemsItem
import com.example.ecommerceapp.domain.repository.OrdersRepository
import javax.inject.Inject

class CreateOrderUseCase @Inject constructor(
    private val ordersRepository: OrdersRepository
) {
    suspend operator fun invoke(
        orderItem: OrderItem,
        items: List<OrderItemsItem>
    ): OrderItem {
        return ordersRepository.createOrder(orderItem, items)
    }
}