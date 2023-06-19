package com.example.dto.auth

import kotlinx.serialization.Serializable

@Serializable
data class PropertiesDto(
    val jwtSecret: String,
    val jwtIssuer: String,
    val jwtAudience: String,
    val jwtRealm: String
)