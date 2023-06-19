package com.example.dto.cart

import kotlinx.serialization.Serializable

@Serializable
data class CartItemDto(
    val id: Int? = null,
    val username: String,
    val productId: Int
)