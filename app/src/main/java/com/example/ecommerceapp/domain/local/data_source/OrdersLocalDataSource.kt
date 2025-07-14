package com.example.ecommerceapp.domain.local.data_source

import com.example.ecommerceapp.domain.model.OrderItem

interface OrdersLocalDataSource {

    suspend fun createOrder(
        orderItem: OrderItem
    ): OrderItem
}