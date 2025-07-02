package com.example.ecommerceapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.ecommerceapp.data.local.entity.OrderItemEntity

@Dao
interface OrderItemDao {

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insertOrderItems(items: List<OrderItemEntity>)

    @Query("SELECT * FROM order_items WHERE productId = :productId LIMIT 1")
    suspend fun getOrderItemByProductId(productId: String): OrderItemEntity?

    @Query("DELETE FROM order_items WHERE orderId = :orderId")
    suspend fun deleteItemsByOrderId(orderId: Long)

    @Query("SELECT DISTINCT productId FROM order_items")
    suspend fun getUsedProductIds(): List<String>
}