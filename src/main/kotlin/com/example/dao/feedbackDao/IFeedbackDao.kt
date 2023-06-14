package com.example.dao.feedbackDao

import com.example.models.Feedback

interface IFeedbackDao {

    suspend fun allFeedbacks(): List<Feedback>

    suspend fun feedbacksByUsername(username: String): List<Feedback>
    suspend fun feedbacksByProductId(id: Int): List<Feedback>

    suspend fun feedbackById(id: Int): Feedback?

    suspend fun addFeedback(message: String, username: String, productId: Int): Feedback?

    suspend fun deleteFeedback(id: Int): Boolean
}