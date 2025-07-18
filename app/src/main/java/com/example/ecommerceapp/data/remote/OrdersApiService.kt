package com.example.ecommerceapp.data.remote

import com.example.ecommerceapp.data.model.orders.OrderDto
import com.example.ecommerceapp.data.model.orders.OrderGetRequestDto
import com.example.ecommerceapp.data.model.orders.OrderCreateRequestDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface OrdersApiService {
    @POST("orders")
    suspend fun createOrder(@Body orderRequest: OrderCreateRequestDto): OrderDto

    @GET("orders/user/{userId}")
    suspend fun getOrders(@Path("userId") userId: String): List<OrderGetRequestDto>

}