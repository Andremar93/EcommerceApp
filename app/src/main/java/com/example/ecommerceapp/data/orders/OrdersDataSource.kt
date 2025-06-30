package com.example.ecommerceapp.data.orders

import com.example.ecommerceapp.data.database.entities.OrderEntity
import com.example.ecommerceapp.data.database.entities.OrderItemEntity
import com.example.ecommerceapp.domain.model.Order
import kotlinx.coroutines.flow.Flow

interface OrdersDataSource {
    val orders: Flow<List<Order>>

    //    fun updateOrder()
//    fun removeOrder()
    suspend fun createOrder(order: OrderEntity, items: List<OrderItemEntity>)
}