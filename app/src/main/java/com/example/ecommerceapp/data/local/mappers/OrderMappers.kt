package com.example.ecommerceapp.data.local.mappers

import com.example.ecommerceapp.data.local.entity.OrderItemEntity
import com.example.ecommerceapp.data.local.relations.OrderWithItems
import com.example.ecommerceapp.domain.model.Order
import com.example.ecommerceapp.domain.model.OrderItem

fun OrderItemEntity.toDomain(): OrderItem {
    return OrderItem(
        id = orderItemId,
        productId = productId,
        quantity = quantity,
        price = price,
        productName = productName
    )
}

fun OrderItem.toEntity(orderId: Long): OrderItemEntity {
    return OrderItemEntity(
        orderItemId = id,
        orderId = orderId,
        productId = productId,
        quantity = quantity,
        price = price,
        productName = productName
    )
}

fun OrderWithItems.toDomain(): Order = Order(
    id = order.id,
    date = order.orderDate,
    totalAmount = order.totalAmount,
    totalItems = order.totalItems,
    items = items.map { it.toDomain() }
)
