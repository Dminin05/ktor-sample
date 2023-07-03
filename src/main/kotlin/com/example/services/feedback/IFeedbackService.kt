package com.example.services.feedback

import com.example.dto.feedback.FeedbackDto
import com.example.models.FeedbackStatus

interface IFeedbackService {

    fun getAllFeedbacks(): List<FeedbackDto>

    fun getFeedbacksByUsername(
        username: String
    ): List<FeedbackDto>

    fun getFeedbacksByProductId(
        productId: Int
    ): List<FeedbackDto>

    fun getFeedbackById(
        id: Int
    ): FeedbackDto

    fun addFeedback(
        feedback: FeedbackDto
    ): Unit

    fun deleteFeedback(
        id: Int
    ): Unit

    fun refactorFeedback(
        status: FeedbackStatus,
        id: Int
    ): Unit

}