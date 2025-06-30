package com.example.ecommerceapp.data.remote

import com.example.ecommerceapp.domain.model.ProductFromAPI
import retrofit2.http.GET

interface ProductApiService {
    @GET("/foods")
    suspend fun getAllProducts(): List<ProductFromAPI>

}