package com.example.ecommerceapp.data.remote

import com.example.ecommerceapp.data.model.OrderDto
import com.example.ecommerceapp.data.model.OrderGetRequestDto
import com.example.ecommerceapp.data.model.OrderRequestDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface OrdersApiService {
    @POST("orders")
    suspend fun createOrder(@Body orderRequest: OrderRequestDto): OrderDto

    @GET("orders")
    suspend fun getOrders(): List<OrderGetRequestDto>

}