package com.example.ecommerceapp.domain.repository

import com.example.ecommerceapp.data.local.entity.OrderEntity
import com.example.ecommerceapp.data.local.entity.OrderItemEntity
import com.example.ecommerceapp.domain.model.Order
import kotlinx.coroutines.flow.Flow

interface OrdersRepository {
    val orders: Flow<List<Order>>
    //    fun updateOrder()
    //    fun removeOrder()
    suspend fun createOrder(order: OrderEntity, items: List<OrderItemEntity>)
}