package com.example.ecommerceapp.data.database.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.ecommerceapp.data.database.entities.OrderEntity
import com.example.ecommerceapp.data.database.entities.OrderItemEntity

data class OrderWithItems(
    @Embedded val order: OrderEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "orderId"
    )
    val items: List<OrderItemEntity>
)
