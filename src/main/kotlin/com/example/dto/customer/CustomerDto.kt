package com.example.dto.customer

import com.example.dto.cart.CartDto
import com.example.dto.feedback.FeedbackDto
import kotlinx.serialization.Serializable

@Serializable
data class CustomerDto(
    val id: Int? = null,
    val name: String,
    val surname: String,
    val username: String,
    val password: String,
    val role: String,
    var cart: CartDto = CartDto(),
    var feedbacks: MutableList<FeedbackDto> = mutableListOf()
)