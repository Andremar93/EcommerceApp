package com.example.ecommerceapp.data.local.data_source

import com.example.ecommerceapp.data.local.dao.OrderDao
import com.example.ecommerceapp.data.local.dao.OrderItemDao
import com.example.ecommerceapp.data.local.mappers.toEntity
import com.example.ecommerceapp.domain.local.data_source.OrdersLocalDataSource
import com.example.ecommerceapp.domain.model.OrderItem
import javax.inject.Inject

class OrdersLocalDataSourceImpl @Inject constructor(
    private val orderDao: OrderDao,
    private val orderItemDao: OrderItemDao
) :
    OrdersLocalDataSource {

    override suspend fun createOrder(
        orderItem: OrderItem
    ): OrderItem {

        orderDao.insertOrder(orderItem.toEntity())
        val itemsWithOrderId = orderItem.items.map { it.toEntity(orderItem.orderId) }
        orderItemDao.insertOrderItems(itemsWithOrderId)

        return orderItem
    }
}