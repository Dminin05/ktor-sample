package com.example.services

import com.example.models.Feedback
import com.example.models.FeedbackDao
import com.example.models.StatusFeedback
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.core.component.KoinComponent

class FeedbackService : KoinComponent {

    fun getAllFeedbacks(): List<Feedback> = transaction{

        return@transaction FeedbackDao.all().map(FeedbackDao::toFeedback)

    }

    fun getFeedbacksByUsername(username: String): List<Feedback> = transaction {

        return@transaction FeedbackDao.all()
            .filter { it.username == username }
            .map(FeedbackDao::toFeedback)

    }

    fun getFeedbacksByProductId(productId: Int): List<Feedback> = transaction {

        return@transaction FeedbackDao.all()
            .filter {
                it.productId == productId
                it.status == StatusFeedback.ACCEPTED
            }
            .map(FeedbackDao::toFeedback)

    }

    fun getFeedbackById(id: Int): Feedback = transaction {

        return@transaction FeedbackDao[id].toFeedback()

    }

    fun addFeedback(feedback: Feedback) = transaction {

        FeedbackDao.new {
            this.message = feedback.message
            this.username = feedback.username
            this.productId = feedback.productId
            this.status = StatusFeedback.UNCHECKED
        }

    }

    fun deleteFeedback(id: Int) = transaction {

        FeedbackDao[id].delete()

    }

    fun updateToAccepted(id: Int) = transaction {

        FeedbackDao[id].status = StatusFeedback.ACCEPTED

    }

    fun updateToRejected(id: Int) = transaction {

        FeedbackDao[id].status = StatusFeedback.REJECTED

    }


}