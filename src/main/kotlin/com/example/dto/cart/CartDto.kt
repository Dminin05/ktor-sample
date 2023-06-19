package com.example.dto.cart

import com.example.dto.product.ProductDtoForCarts
import kotlinx.serialization.Serializable

@Serializable
data class CartDto(
    val products: MutableList<ProductDtoForCarts> = mutableListOf(),
    var price: Int = 0
)