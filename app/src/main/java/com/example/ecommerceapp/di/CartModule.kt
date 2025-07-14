package com.example.ecommerceapp.di

// PRODUCTS
import com.example.ecommerceapp.data.local.dao.ProductDao


// USER

// ORDERS


// CART
import com.example.ecommerceapp.data.local.dao.CartDao
import com.example.ecommerceapp.data.repository.CartRepositoryImpl
import com.example.ecommerceapp.domain.repository.CartRepository

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object CartModule {

    @Provides
    fun providesCartRepository(
        dao: CartDao,
        productDao: ProductDao
    ): CartRepository {
        return CartRepositoryImpl(dao, productDao)
    }

}

