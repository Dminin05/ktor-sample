package com.example.dtos

import com.example.models.Product
import kotlinx.serialization.Serializable

@Serializable
data class CartDto(
    val products: MutableList<ProductDto> = mutableListOf(),
    var price: Int = 0
)