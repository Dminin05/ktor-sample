package com.example.dtos

import kotlinx.serialization.Serializable

@Serializable
data class ProductDto(val title: String, val price: Int)
