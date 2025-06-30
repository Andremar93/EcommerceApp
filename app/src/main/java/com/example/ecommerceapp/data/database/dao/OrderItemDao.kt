package com.example.ecommerceapp.data.database.dao

import androidx.room.*
import com.example.ecommerceapp.data.database.entities.OrderItemEntity

@Dao
interface OrderItemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrderItems(items: List<OrderItemEntity>)

    @Query("SELECT * FROM order_items WHERE productId = :productId LIMIT 1")
    suspend fun getOrderItemByProductId(productId: String): OrderItemEntity?

    @Query("DELETE FROM order_items WHERE orderId = :orderId")
    suspend fun deleteItemsByOrderId(orderId: Long)
}
