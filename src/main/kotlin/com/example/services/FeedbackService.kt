package com.example.services

import com.example.dto.feedback.FeedbackDto
import com.example.models.FeedbackDao
import com.example.models.FeedbackStatus
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.core.component.KoinComponent

class FeedbackService : KoinComponent {

    fun getAllFeedbacks(): List<FeedbackDto> = transaction{

        return@transaction FeedbackDao.all().map(FeedbackDao::toFeedback)
    }

    fun getFeedbacksByUsername(username: String): List<FeedbackDto> = transaction {

        return@transaction FeedbackDao.all()
            .filter { it.username == username }
            .map(FeedbackDao::toFeedback)
    }

    fun getFeedbacksByProductId(productId: Int): List<FeedbackDto> = transaction {

        return@transaction FeedbackDao.all()
            .filter {
                it.productId == productId &&
                it.status == FeedbackStatus.ACCEPTED
            }
            .map(FeedbackDao::toFeedback)
    }

    fun getFeedbackById(id: Int): FeedbackDto = transaction {

        return@transaction FeedbackDao.findById(id)!!.toFeedback()
    }

    fun addFeedback(feedback: FeedbackDto) = transaction {

        FeedbackDao.new {
            this.message = feedback.message
            this.username = feedback.username
            this.productId = feedback.productId
            this.status = FeedbackStatus.UNCHECKED
        }

    }

    fun deleteFeedback(id: Int) = transaction {

        FeedbackDao.findById(id)!!.delete()

    }

    fun refactorFeedback(status: FeedbackStatus, id: Int) = transaction {

        FeedbackDao.findById(id)!!.status = status

    }


}