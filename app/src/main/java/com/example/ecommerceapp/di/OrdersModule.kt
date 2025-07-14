package com.example.ecommerceapp.di

import com.example.ecommerceapp.data.local.dao.OrderDao
import com.example.ecommerceapp.data.local.dao.OrderItemDao
import com.example.ecommerceapp.data.local.data_source.OrdersLocalDataSourceImpl
import com.example.ecommerceapp.data.remote.OrdersApiService
import com.example.ecommerceapp.data.remote.data_source.OrdersRemoteDataSourceImpl
import com.example.ecommerceapp.domain.local.data_source.OrdersLocalDataSource
import com.example.ecommerceapp.domain.remote.data_source.OrdersRemoteDataSource
import com.example.ecommerceapp.domain.repository.OrdersRepository
import com.example.ecommerceapp.domain.use_case.order.CreateOrderUseCase
import com.example.ecommerceapp.domain.use_case.order.GetOrdersUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class OrdersModule {

    @Provides
    fun provideOrdersRemoteDataSource(
        orderApi: OrdersApiService
    ): OrdersRemoteDataSource {
        return OrdersRemoteDataSourceImpl(orderApi)
    }

    @Provides
    fun provideOrdersLocalDataSource(
        orderDao: OrderDao,
        orderItemDao: OrderItemDao
    ): OrdersLocalDataSource {
        return OrdersLocalDataSourceImpl(orderDao, orderItemDao)
    }

    @Provides
    fun provideCreateOrderUseCase(
        repository: OrdersRepository
    ): CreateOrderUseCase {
        return CreateOrderUseCase(repository)
    }

    @Provides
    fun getOrdersUseCase(
        repository: OrdersRepository
    ): GetOrdersUseCase {
        return GetOrdersUseCase(repository)
    }
}