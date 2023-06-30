package com.example.dto.product

import kotlinx.serialization.Serializable

@Serializable
data class CategoryDto(
    val id: Int? = null,
    val title: String,
    var products: MutableList<ProductDto> = mutableListOf()
)