package com.example.dto.product

import kotlinx.serialization.Serializable

@Serializable
data class PageResult<T>(

    val offset: Long,
    val limit: Int,
    val items: List<T>
    
)