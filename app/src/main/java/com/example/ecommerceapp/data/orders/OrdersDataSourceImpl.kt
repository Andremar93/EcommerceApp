package com.example.ecommerceapp.data.orders

import com.example.ecommerceapp.data.cart.CartDataSource
import com.example.ecommerceapp.data.database.dao.OrderDao
import com.example.ecommerceapp.data.database.dao.OrderItemDao
import com.example.ecommerceapp.data.database.entities.OrderEntity
import com.example.ecommerceapp.data.database.entities.OrderItemEntity
import com.example.ecommerceapp.data.database.mappers.toDomain
import com.example.ecommerceapp.domain.model.Order
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class OrdersDataSourceImpl @Inject constructor(
    private val orderDao: OrderDao,
    private val orderItemDao: OrderItemDao,
    private val cartDataSource: CartDataSource
) : OrdersDataSource {

    override val orders: Flow<List<Order>> =
        orderDao.getAllOrdersWithItems().map { list -> list.map { it.toDomain() } }

    override suspend fun createOrder(order: OrderEntity, items: List<OrderItemEntity>) {
        val orderId = orderDao.insertOrder(order)
        val itemsWithOrderId = items.map { it.copy(orderId = orderId) }
        orderItemDao.insertOrderItems(itemsWithOrderId)
    }

}