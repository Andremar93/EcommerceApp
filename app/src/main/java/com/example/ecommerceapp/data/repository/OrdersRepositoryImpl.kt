package com.example.ecommerceapp.data.repository

import com.example.ecommerceapp.data.local.entity.OrderEntity
import com.example.ecommerceapp.data.local.entity.OrderItemEntity
import com.example.ecommerceapp.data.local.mappers.toDomain
import com.example.ecommerceapp.domain.model.Order
import com.example.ecommerceapp.data.local.dao.OrderDao
import com.example.ecommerceapp.data.local.dao.OrderItemDao
import com.example.ecommerceapp.domain.repository.OrdersRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class OrdersRepositoryImpl(
    private val orderDao: OrderDao,
    private val orderItemDao: OrderItemDao
) : OrdersRepository {

    override val orders: Flow<List<Order>> =
        orderDao.getAllOrdersWithItems().map { list -> list.map { it.toDomain() } }

    override suspend fun createOrder(order: OrderEntity, items: List<OrderItemEntity>) {
        val orderId = orderDao.insertOrder(order)
        val itemsWithOrderId = items.map { it.copy(orderId = orderId) }
        orderItemDao.insertOrderItems(itemsWithOrderId)
    }

}