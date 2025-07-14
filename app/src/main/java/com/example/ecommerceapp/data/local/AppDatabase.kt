package com.example.ecommerceapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.ecommerceapp.data.local.converter.Converters
import com.example.ecommerceapp.data.local.dao.CartDao
import com.example.ecommerceapp.data.local.dao.OrderDao
import com.example.ecommerceapp.data.local.dao.OrderItemDao
import com.example.ecommerceapp.data.local.dao.ProductDao
import com.example.ecommerceapp.data.local.dao.UsersDao
import com.example.ecommerceapp.data.local.entity.CartEntity
import com.example.ecommerceapp.data.local.entity.OrderEntity
import com.example.ecommerceapp.data.local.entity.OrderItemsEntity
import com.example.ecommerceapp.data.local.entity.ProductEntity
import com.example.ecommerceapp.data.local.entity.UserEntity

@Database(
    entities = [ProductEntity::class, CartEntity::class, OrderEntity::class, OrderItemsEntity::class, UserEntity::class],
    version = 9,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getProductDao(): ProductDao

    abstract fun getCartDao(): CartDao

    abstract fun getOrderItemDao(): OrderItemDao

    abstract fun getOrderDao(): OrderDao

    abstract fun getUserDao() : UsersDao
}