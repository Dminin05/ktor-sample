package com.example.dto.category

import com.example.dto.product.ProductDto
import kotlinx.serialization.Serializable

@Serializable
data class CategoryDto(
    val id: Int? = null,
    val title: String,
    var products: MutableList<ProductDto> = mutableListOf()
)