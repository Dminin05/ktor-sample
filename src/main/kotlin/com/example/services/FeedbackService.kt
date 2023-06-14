package com.example.services

import com.example.dao.feedbackDao.IFeedbackDao
import com.example.models.Feedback
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class FeedbackService : KoinComponent{

    val feedbackDao by inject<IFeedbackDao>()

    suspend fun getAllFeedbacks(): List<Feedback>{
        return feedbackDao.allFeedbacks()
    }

    suspend fun getFeedbacksByUsername(username: String): List<Feedback>{
        return feedbackDao.feedbacksByUsername(username)
    }

    suspend fun getFeedbacksByProductId(id: Int): List<Feedback>{
        return feedbackDao.feedbacksByProductId(id)
    }

    suspend fun getFeedbackById(id: Int): Feedback?{
        return feedbackDao.feedbackById(id)
    }

    suspend fun addFeedback(
        message: String,
        username: String,
        productId: Int
    ){
        feedbackDao.addFeedback(message, username, productId)
    }

    suspend fun deleteFeedback(id: Int){
        feedbackDao.deleteFeedback(id)
    }

}