package com.example.ecommerceapp.data.remote

import com.example.ecommerceapp.data.model.ProductDto
import retrofit2.http.GET

interface ProductApiService {

    @GET("foods")
    suspend fun getProducts(): List<ProductDto>

    // @GET("products/{id}")
    // @POST("products")
}
