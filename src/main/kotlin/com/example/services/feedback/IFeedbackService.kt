package com.example.services.feedback

import com.example.models.Feedback

interface IFeedbackService {

    fun getAllFeedbacks(): List<Feedback>

    fun getFeedbacksByUsername(
        username: String
    ): List<Feedback>

    fun getFeedbacksByProductId(
        productId: Int
    ): List<Feedback>

    fun getFeedbackById(
        id: Int
    ): Feedback

    fun addFeedback(
        feedback: Feedback
    ): Unit

    fun deleteFeedback(
        id: Int
    ): Unit

    fun refactorFeedback(
        status: Feedback.FeedbackStatus,
        id: Int
    ): Unit

}