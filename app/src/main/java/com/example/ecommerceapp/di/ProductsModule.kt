package com.example.ecommerceapp.di

import com.example.ecommerceapp.data.local.dao.ProductDao
import com.example.ecommerceapp.data.local.data_source.ProductLocalDataSourceImpl
import com.example.ecommerceapp.data.remote.ProductApiService
import com.example.ecommerceapp.data.remote.data_source.ProductRemoteDataSourceImpl
import com.example.ecommerceapp.data.repository.OrdersRepositoryImpl
import com.example.ecommerceapp.data.repository.ProductRepositoryImpl
import com.example.ecommerceapp.domain.local.data_source.OrdersLocalDataSource
import com.example.ecommerceapp.domain.local.data_source.ProductLocalDataSource
import com.example.ecommerceapp.domain.remote.data_source.OrdersRemoteDataSource
import com.example.ecommerceapp.domain.remote.data_source.ProductRemoteDataSource
import com.example.ecommerceapp.domain.repository.OrdersRepository
import com.example.ecommerceapp.domain.repository.ProductRepository
import com.example.ecommerceapp.domain.use_case.product.GetProductsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ProductsModule {

    @Provides
    fun provideOrderRepository(
        ordersRemoteDataSource: OrdersRemoteDataSource,
        ordersLocalDataSource: OrdersLocalDataSource
    ): OrdersRepository {
        return OrdersRepositoryImpl(ordersRemoteDataSource, ordersLocalDataSource)
    }

    @Provides
    @Singleton
    fun provideProductRemoteDataSource(
        apiService: ProductApiService
    ): ProductRemoteDataSource {
        return ProductRemoteDataSourceImpl(apiService)
    }

    @Provides
    @Singleton
    fun provideProductLocalDataSource(
        dao: ProductDao
    ): ProductLocalDataSource {
        return ProductLocalDataSourceImpl(dao)
    }

    @Provides
    @Singleton
    fun provideProductRepository(
        remote: ProductRemoteDataSource,
        local: ProductLocalDataSource
    ): ProductRepository {
        return ProductRepositoryImpl(remote, local)
    }

    @Provides
    fun provideGetProductsUseCase(
        repository: ProductRepository
    ): GetProductsUseCase {
        return GetProductsUseCase(repository)
    }

}
