package com.example.ecommerceapp.domain.remote.data_source

import com.example.ecommerceapp.domain.model.OrderItem
import com.example.ecommerceapp.domain.model.OrderItemsItem

interface OrdersRemoteDataSource {

    suspend fun createOrder(
        orderItem: OrderItem,
        items: List<OrderItemsItem>
    ): OrderItem {
        return orderItem
    }

    suspend fun getOrders(userId: String): List<OrderItem>
}