package com.example.ecommerceapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.ecommerceapp.data.database.dao.CartDao
import com.example.ecommerceapp.data.database.dao.OrderDao
import com.example.ecommerceapp.data.database.dao.OrderItemDao
import com.example.ecommerceapp.data.database.dao.ProductDao
import com.example.ecommerceapp.data.database.entities.CartEntity
import com.example.ecommerceapp.data.database.entities.OrderItemEntity
import com.example.ecommerceapp.data.database.entities.ProductEntity
import com.example.ecommerceapp.data.database.entities.OrderEntity

@Database(
    entities = [ProductEntity::class, CartEntity::class, OrderEntity::class, OrderItemEntity::class],
    version = 3,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getProductDao(): ProductDao
    abstract fun getCartDao(): CartDao
    abstract fun getOrderItemDao(): OrderItemDao
    abstract fun getOrderDao(): OrderDao
}