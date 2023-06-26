package com.example.dto.order

import kotlinx.serialization.Serializable

@Serializable
data class OrderItemDto(
    val id: Int? = null,
    val orderId: Int,
    val username: String,
    val productTitle: String,
    val price: Int
)