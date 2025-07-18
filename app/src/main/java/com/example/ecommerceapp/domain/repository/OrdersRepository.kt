package com.example.ecommerceapp.domain.repository

import com.example.ecommerceapp.domain.model.OrderItem
import com.example.ecommerceapp.domain.model.OrderItemsItem

interface OrdersRepository {

    suspend fun createOrder(orderItem: OrderItem, items: List<OrderItemsItem>): OrderItem

    suspend fun getOrders(userId: String): List<OrderItem>
}
