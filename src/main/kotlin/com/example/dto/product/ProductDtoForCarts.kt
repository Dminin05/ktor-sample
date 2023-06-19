package com.example.dto.product

import kotlinx.serialization.Serializable

@Serializable
data class ProductDtoForCarts(
    val title: String,
    val price: Int
)
