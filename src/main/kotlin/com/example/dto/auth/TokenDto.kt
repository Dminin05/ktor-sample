package com.example.dto.auth

import kotlinx.serialization.Serializable

@Serializable
data class TokenDto(
    val token: String
)