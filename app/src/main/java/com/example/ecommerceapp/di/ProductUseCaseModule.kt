package com.example.ecommerceapp.di

import com.example.ecommerceapp.domain.repository.ProductRepository
import com.example.ecommerceapp.domain.use_case.product.GetProductsFromApiUseCase
import com.example.ecommerceapp.domain.use_case.product.GetProductsFromLocalUseCase
import com.example.ecommerceapp.domain.use_case.product.SaveProductsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object ProductUseCaseModule {

    @Provides
    fun provideGetProductsFromApiUseCase(
        repository: ProductRepository
    ): GetProductsFromApiUseCase {
        return GetProductsFromApiUseCase(repository)
    }

    @Provides
    fun provideGetProductsFromLocalUseCase(
        repo: ProductRepository
    ) = GetProductsFromLocalUseCase(repo)

    @Provides
    fun provideSaveProductsUseCase(
        repo: ProductRepository
    ) = SaveProductsUseCase(repo)
}