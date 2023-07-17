package com.example.services.feedback

import com.example.models.Feedback
import com.example.models.FeedbackDao
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.core.component.KoinComponent

class FeedbackService : IFeedbackService, KoinComponent {

    override fun getAllFeedbacks(): List<Feedback> = transaction{

        return@transaction FeedbackDao.all().map(FeedbackDao::toFeedback)
    }

    override fun getFeedbacksByUsername(
        username: String
    ): List<Feedback> = transaction {

        return@transaction FeedbackDao.all()
            .filter { it.username == username }
            .map(FeedbackDao::toFeedback)
    }

    override fun getFeedbacksByProductId(
        productId: Int
    ): List<Feedback> = transaction {

        return@transaction FeedbackDao.all()
            .filter {
                it.productId == productId &&
                it.status == Feedback.FeedbackStatus.ACCEPTED
            }
            .map(FeedbackDao::toFeedback)
    }

    override fun getFeedbackById(
        id: Int
    ): Feedback = transaction {

        return@transaction FeedbackDao.findById(id)!!.toFeedback()
    }

    override fun addFeedback(
        feedback: Feedback
    ): Unit = transaction {

        FeedbackDao.new {
            this.message = feedback.message
            this.username = feedback.username
            this.productId = feedback.productId
            this.status = Feedback.FeedbackStatus.UNCHECKED
        }

    }

    override fun deleteFeedback(
        id: Int
    ): Unit = transaction {

        FeedbackDao.findById(id)!!.delete()

    }

    override fun refactorFeedback(
        status: Feedback.FeedbackStatus,
        id: Int
    ): Unit = transaction {

        FeedbackDao.findById(id)!!.status = status

    }


}