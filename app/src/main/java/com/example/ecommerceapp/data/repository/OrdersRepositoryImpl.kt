package com.example.ecommerceapp.data.repository

import retrofit2.HttpException
import android.util.Log
import com.example.ecommerceapp.data.local.mappers.toDomain
import com.example.ecommerceapp.domain.model.OrderItem
import com.example.ecommerceapp.data.local.mappers.toEntity
import com.example.ecommerceapp.domain.local.data_source.OrdersLocalDataSource
import com.example.ecommerceapp.domain.model.OrderItemsItem
import com.example.ecommerceapp.domain.remote.data_source.OrdersRemoteDataSource
import com.example.ecommerceapp.domain.repository.OrdersRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class OrdersRepositoryImpl(
    private val ordersRemoteDataSource: OrdersRemoteDataSource,
    private val ordersLocalDataSource: OrdersLocalDataSource
) : OrdersRepository {

//    override val orders: Flow<List<OrderItem>> =
//        orderDao.getAllOrdersWithItems().map { list -> list.map { it.toDomain() } }

    override suspend fun createOrder(orderItem: OrderItem, items: List<OrderItemsItem>): OrderItem {
        return try {

            val createdOrder = ordersRemoteDataSource.createOrder(orderItem, items)

            val updatedItems = createdOrder.items.map { it.copy(orderId = createdOrder.orderId) }
            val updatedOrder = createdOrder.copy(items = updatedItems)

            val orderDatabase = ordersLocalDataSource.createOrder(updatedOrder)

            orderDatabase
        } catch (e: HttpException) {
            Log.e("OrdersRepository", "Error creating order: ${e.message()}")
            throw e
        }
    }

    override suspend fun getOrders(): List<OrderItem> {
        return try {
            ordersRemoteDataSource.getOrders()
        } catch (e: HttpException) {
            Log.e("OrdersRepository", "Error fetching orders: ${e.message()}")
            throw e
        }
    }
}