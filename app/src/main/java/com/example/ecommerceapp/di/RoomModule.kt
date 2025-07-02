package com.example.ecommerceapp.di

import android.content.Context
import androidx.room.Room
import com.example.ecommerceapp.data.local.AppDatabase
import com.example.ecommerceapp.data.local.dao.CartDao
import com.example.ecommerceapp.data.local.dao.OrderDao
import com.example.ecommerceapp.data.local.dao.OrderItemDao
import com.example.ecommerceapp.data.local.dao.ProductDao
import com.example.ecommerceapp.data.local.dao.UsersDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    private const val APP_DATABASE_NAME = "product_database"

    @Singleton
    @Provides
    fun provideRoom(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, AppDatabase::class.java, APP_DATABASE_NAME)
            .fallbackToDestructiveMigration().build()

    @Singleton
    @Provides
    fun provideProductDao(db: AppDatabase): ProductDao = db.getProductDao()

    @Provides
    @Singleton
    fun provideCartDao(db: AppDatabase): CartDao = db.getCartDao()

    @Provides
    @Singleton
    fun provideOrderDao(db: AppDatabase): OrderDao = db.getOrderDao()

    @Provides
    @Singleton
    fun provideOrderItemDao(db: AppDatabase): OrderItemDao = db.getOrderItemDao()

    @Provides
    fun provideUserDao(db: AppDatabase): UsersDao = db.getUserDao()

}