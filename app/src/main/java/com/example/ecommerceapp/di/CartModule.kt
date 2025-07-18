package com.example.ecommerceapp.di


import com.example.ecommerceapp.data.local.dao.ProductDao
import com.example.ecommerceapp.data.local.dao.CartDao
import com.example.ecommerceapp.data.repository.CartRepositoryImpl
import com.example.ecommerceapp.domain.repository.CartRepository
import com.example.ecommerceapp.domain.use_case.cart.GetCartItemCountUseCase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object CartModule {

    @Provides
    fun provideGetCartItemCountUseCase(
        repository: CartRepository
    ): GetCartItemCountUseCase {
        return GetCartItemCountUseCase(repository)
    }

}



