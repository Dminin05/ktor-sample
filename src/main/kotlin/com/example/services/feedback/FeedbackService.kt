package com.example.services.feedback

import com.example.dto.feedback.FeedbackDto
import com.example.models.FeedbackDao
import com.example.models.FeedbackStatus
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.core.component.KoinComponent

class FeedbackService : IFeedbackService, KoinComponent {

    override fun getAllFeedbacks(): List<FeedbackDto> = transaction{

        return@transaction FeedbackDao.all().map(FeedbackDao::toFeedback)
    }

    override fun getFeedbacksByUsername(
        username: String
    ): List<FeedbackDto> = transaction {

        return@transaction FeedbackDao.all()
            .filter { it.username == username }
            .map(FeedbackDao::toFeedback)
    }

    override fun getFeedbacksByProductId(
        productId: Int
    ): List<FeedbackDto> = transaction {

        return@transaction FeedbackDao.all()
            .filter {
                it.productId == productId &&
                it.status == FeedbackStatus.ACCEPTED
            }
            .map(FeedbackDao::toFeedback)
    }

    override fun getFeedbackById(
        id: Int
    ): FeedbackDto = transaction {

        return@transaction FeedbackDao.findById(id)!!.toFeedback()
    }

    override fun addFeedback(
        feedback: FeedbackDto
    ): Unit = transaction {

        FeedbackDao.new {
            this.message = feedback.message
            this.username = feedback.username
            this.productId = feedback.productId
            this.status = FeedbackStatus.UNCHECKED
        }

    }

    override fun deleteFeedback(
        id: Int
    ): Unit = transaction {

        FeedbackDao.findById(id)!!.delete()

    }

    override fun refactorFeedback(
        status: FeedbackStatus,
        id: Int
    ): Unit = transaction {

        FeedbackDao.findById(id)!!.status = status

    }


}