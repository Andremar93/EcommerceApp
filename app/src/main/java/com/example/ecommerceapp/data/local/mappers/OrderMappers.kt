package com.example.ecommerceapp.data.local.mappers

import com.example.ecommerceapp.data.local.entity.OrderEntity
import com.example.ecommerceapp.data.local.entity.OrderItemsEntity
import com.example.ecommerceapp.data.local.relations.OrderWithItems
import com.example.ecommerceapp.data.model.OrderDto
import com.example.ecommerceapp.data.model.OrderGetRequestDto
import com.example.ecommerceapp.data.model.OrderItemDto
import com.example.ecommerceapp.domain.model.OrderItem
import com.example.ecommerceapp.domain.model.OrderItemsItem
import java.util.UUID

fun OrderItemsEntity.toDomain(): OrderItemsItem {
    return OrderItemsItem(
        orderId = orderId,
        productId = productId,
        quantity = quantity,
        price = price,
        productName = productName,
        avatar = "",
        description = "",
        hasDrink = false,
        categories = emptyList(),
    )
}

fun OrderItemsItem.toEntity(orderId: String): OrderItemsEntity {
    return OrderItemsEntity(
        orderId = orderId,
        productId = productId,
        quantity = quantity,
        price = price,
        productName = productName,
    )
}

fun OrderItemsItem.toEntity(): OrderItemsEntity {
    return OrderItemsEntity(
        orderId = orderId,
        productId = productId,
        quantity = quantity,
        price = price,
        productName = productName,
    )
}

fun List<OrderItemsItem>.toEntity(orderId: String): List<OrderItemsEntity> {
    return map {
        OrderItemsEntity(
            orderId = orderId,
            productId = it.productId,
            productName = it.productName,
            quantity = it.quantity,
            price = it.price
        )
    }
}

fun OrderWithItems.toDomain(): OrderItem = OrderItem(
    date = order.orderDate,
    totalAmount = order.totalAmount,
    totalItems = order.totalItems,
    items = items.map { it.toDomain() },
    userId = order.userId,
)

fun OrderItem.toEntity(): OrderEntity {
    return OrderEntity(
        id = orderId,
        orderDate = date,
        totalAmount = totalAmount,
        totalItems = totalItems,
        userId = userId
    )
}

fun OrderItem.toDto(items: List<OrderItemsItem>): OrderDto {
    return OrderDto(
        orderId = UUID.randomUUID().toString(),
        userId = this.userId,
        productIds = items.map { it.toDto() },
        total = this.totalAmount,
        timestamp = this.date
    )
}

fun OrderItemsItem.toDto(): OrderItemDto {
    return OrderItemDto(
        productId = productId,
        name = productName,
        description = description,
        imageUrl = avatar,
        price = price,
        quantity = quantity,
        hasDrink = hasDrink,
        categories = categories
    )
}

fun OrderDto.toDomain(): OrderItem {
    return OrderItem(
        orderId = this.orderId,
        userId = this.userId,
        date = this.timestamp,
        totalAmount = this.total,
        totalItems = this.productIds.sumOf { it.quantity },
        items = this.productIds.map { it.toDomain(this.orderId) }
    )
}

fun OrderItemDto.toDomain(orderId: String): OrderItemsItem {
    return OrderItemsItem(
        orderId = orderId,
        productId = this.productId,
        quantity = this.quantity,
        price = this.price,
        productName = this.name,
        avatar = this.imageUrl,
        description = this.description,
        hasDrink = this.hasDrink,
        categories = this.categories
    )
}

fun OrderGetRequestDto.toDomain(): OrderItem {
    return OrderItem(
        orderId = this.orderId,
        userId = this.userId,
        date = this.timestamp,
        totalAmount = this.total,
        totalItems = this.productIds.size,
        items = this.productIds.map { it.toDomain(this.orderId) }
    )
}

