package com.example.dto.product

import com.example.dto.feedback.FeedbackDto
import kotlinx.serialization.Serializable

@Serializable
data class ProductDto(
    val id: Int? = null,
    val title: String,
    var price: Int,
    var feedbacks: MutableList<FeedbackDto> = mutableListOf()
)