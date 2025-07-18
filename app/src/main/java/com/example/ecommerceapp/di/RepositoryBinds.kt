package com.example.ecommerceapp.di

import com.example.ecommerceapp.data.repository.CartRepositoryImpl
import com.example.ecommerceapp.domain.repository.CartRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryBinds {

    @Binds
    abstract fun bindCartRepository(
        impl: CartRepositoryImpl
    ): CartRepository

}