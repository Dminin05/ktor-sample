package com.example.dtos

import kotlinx.serialization.Serializable

@Serializable
data class PageResult<T>(

    val offset: Long,
    val limit: Int,
    val items: List<T>
    
)