package com.example.dto.order

import kotlinx.serialization.Serializable

@Serializable
data class OrderDto(
    val orderId: Int,
    val productsInOrder: MutableList<OrderItemDto> = mutableListOf(),
    var totalPrice: Int = 0
)