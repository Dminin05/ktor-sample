package com.example.dto.customer

import kotlinx.serialization.Serializable

@Serializable
data class RoleDto(
    val id: Int? = null,
    val role: String
)