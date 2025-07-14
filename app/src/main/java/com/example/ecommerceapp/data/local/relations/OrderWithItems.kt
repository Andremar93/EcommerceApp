package com.example.ecommerceapp.data.local.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.ecommerceapp.data.local.entity.OrderEntity
import com.example.ecommerceapp.data.local.entity.OrderItemsEntity
import com.example.ecommerceapp.data.local.entity.UserEntity

data class OrderWithItems(
    @Embedded val order: OrderEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "orderId"
    )
    val items: List<OrderItemsEntity>
)

data class UserWithOrders(
    @Embedded val user: UserEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "userId"
    )
    val orders: List<OrderEntity>
)
