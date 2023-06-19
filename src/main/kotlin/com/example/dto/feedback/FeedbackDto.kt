package com.example.dto.feedback

import com.example.models.FeedbackStatus
import kotlinx.serialization.Serializable

@Serializable
data class FeedbackDto(
    val id: Int? = null,
    val message: String,
    val username: String,
    val productId: Int,
    var status: FeedbackStatus
) {
    enum class Status {
        TODO()
    }
}