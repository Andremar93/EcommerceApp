package com.example.ecommerceapp.data.database.mappers

import com.example.ecommerceapp.data.database.entities.OrderItemEntity
import com.example.ecommerceapp.data.database.relations.OrderWithItems
import com.example.ecommerceapp.domain.model.Order
import com.example.ecommerceapp.domain.model.OrderItem

fun OrderItemEntity.toDomain(): OrderItem {
    return OrderItem(
        id = orderItemId,
        productId = productId,
        quantity = quantity,
        price = price
    )
}

fun OrderItem.toEntity(orderId: Long): OrderItemEntity {
    return OrderItemEntity(
        orderItemId = id,
        orderId = orderId,
        productId = productId,
        quantity = quantity,
        price = price
    )
}

fun OrderWithItems.toDomain(): Order = Order(
    id = order.id,
    date = order.orderDate,
    totalAmount = order.totalAmount,
    totalItems = order.totalItems,
    items = items.map { it.toDomain() }
)
