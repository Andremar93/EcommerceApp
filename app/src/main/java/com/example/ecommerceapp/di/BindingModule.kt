package com.example.ecommerceapp.di

import com.example.ecommerceapp.data.cart.CartDataSource
import com.example.ecommerceapp.data.cart.CartDataSourceImpl
import com.example.ecommerceapp.data.orders.OrdersDataSource
import com.example.ecommerceapp.data.orders.OrdersDataSourceImpl
import com.example.ecommerceapp.data.products.ProductDataSourceImpl
import com.example.ecommerceapp.data.products.ProductsDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class BindingModule {

    @Binds
    @Singleton
    abstract fun bindProductsDataSource(
        impl: ProductDataSourceImpl
    ): ProductsDataSource

    @Binds
    @Singleton
    abstract fun bindCartDataSource(
        impl: CartDataSourceImpl
    ): CartDataSource

    @Binds
    @Singleton
    abstract fun bindOrderDataSource(
        impl: OrdersDataSourceImpl
    ): OrdersDataSource

}
