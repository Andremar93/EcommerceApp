package com.example.ecommerceapp.domain.repository

import com.example.ecommerceapp.domain.model.OrderItem
import com.example.ecommerceapp.domain.model.OrderItemsItem
import kotlinx.coroutines.flow.Flow

interface OrdersRepository {
//    val orders: Flow<List<OrderItem>>

    suspend fun createOrder(orderItem: OrderItem, items: List<OrderItemsItem>): OrderItem

    suspend fun getOrders(): List<OrderItem>
}
