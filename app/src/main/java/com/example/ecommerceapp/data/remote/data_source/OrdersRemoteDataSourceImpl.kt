package com.example.ecommerceapp.data.remote.data_source

import android.util.Log
import com.example.ecommerceapp.data.local.mappers.toDomain
import com.example.ecommerceapp.data.local.mappers.toDto
import com.example.ecommerceapp.data.model.orders.OrderCreateRequestDto
import com.example.ecommerceapp.data.remote.OrdersApiService
import com.example.ecommerceapp.domain.model.OrderItem
import com.example.ecommerceapp.domain.model.OrderItemsItem
import com.example.ecommerceapp.domain.remote.data_source.OrdersRemoteDataSource
import retrofit2.HttpException
import javax.inject.Inject

class OrdersRemoteDataSourceImpl @Inject constructor(
    private val ordersApiService: OrdersApiService
) : OrdersRemoteDataSource {

    override suspend fun createOrder(order: OrderItem, items: List<OrderItemsItem>): OrderItem {
        val orderRequest = OrderCreateRequestDto(
            userId = order.userId,
            productIds = items.map { it.toDto() },
            total = order.totalAmount,
            timestamp = System.currentTimeMillis(),
            orderId = order.orderId
        )
        return try {
            val createdOrderItem = ordersApiService.createOrder(orderRequest).toDomain()
            createdOrderItem
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            Log.e("API_ERROR", "Código: ${e.code()}, Cuerpo: $errorBody")
            throw e
        } catch (e: Exception) {
            Log.e("API_ERROR", "Otro error: ${e.localizedMessage}")
            throw e
        }
    }

    override suspend fun getOrders(userId: String): List<OrderItem> {
        return try {
            val orders = ordersApiService.getOrders(userId)
                .map { it.toDomain() }
            orders
        } catch (e: HttpException) {
            Log.e("OrdersRepositoryImpl", "Error fetching orders: ${e.message()}")
            throw e
        } catch (e: Exception) {
            Log.e("OrdersRepositoryImpl", "Error fetching orders: ${e.localizedMessage}")
            throw e
        }
    }
}