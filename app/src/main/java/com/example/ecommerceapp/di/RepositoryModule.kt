package com.example.ecommerceapp.di

// PRODUCTS
import com.example.ecommerceapp.data.local.dao.ProductDao
import com.example.ecommerceapp.data.remote.ProductApiService
import com.example.ecommerceapp.data.repository.ProductRepositoryImpl
import com.example.ecommerceapp.domain.repository.ProductRepository


// USER
import com.example.ecommerceapp.data.local.dao.UsersDao
import com.example.ecommerceapp.data.remote.UserApiService
import com.example.ecommerceapp.data.repository.UserRepositoryImpl
import com.example.ecommerceapp.domain.repository.UserRepository

// ORDERS
import com.example.ecommerceapp.data.repository.OrdersRepositoryImpl
import com.example.ecommerceapp.domain.repository.OrdersRepository
import com.example.ecommerceapp.data.local.dao.OrderDao
import com.example.ecommerceapp.data.local.dao.OrderItemDao


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
object RepositoryModule {

    @Provides
    fun provideProductRepository(
        api: ProductApiService,
        dao: ProductDao
    ): ProductRepository {
        return ProductRepositoryImpl(api, dao)
    }

    @Provides
    fun provideUserRepository(
        api: UserApiService,
        dao: UsersDao
    ): UserRepository {
        return UserRepositoryImpl(api, dao)
    }

    @Provides
    fun provideOrderRepository(
        dao: OrderDao,
        daoItem: OrderItemDao,
    ): OrdersRepository {
        return OrdersRepositoryImpl(dao, daoItem)
    }

    @Provides
    fun providesCartRepository(
        dao: CartDao,
        productDao: ProductDao
    ): CartRepository {
        return CartRepositoryImpl(dao, productDao)
    }

}

