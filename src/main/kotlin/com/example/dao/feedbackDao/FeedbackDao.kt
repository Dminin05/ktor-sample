package com.example.dao.feedbackDao

import com.example.models.Feedback
import com.example.models.Feedbacks
import com.example.utils.DatabaseFactory.dbQuery
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class FeedbackDao : IFeedbackDao{

    private fun resultRowToFeedback(row: ResultRow) = Feedback(

        id = row[Feedbacks.id],
        message = row[Feedbacks.message],
        username = row[Feedbacks.username],
        productId = row[Feedbacks.productId]

    )

    override suspend fun allFeedbacks(): List<Feedback> = dbQuery{
        Feedbacks.selectAll().map(::resultRowToFeedback)
    }

    override suspend fun feedbacksByUsername(username: String): List<Feedback> = dbQuery{
        Feedbacks
            .select { Feedbacks.username eq username }
            .map(::resultRowToFeedback)
    }

    override suspend fun feedbackById(id: Int): Feedback? = dbQuery{
        Feedbacks
            .select { Feedbacks.id eq id }
            .map(::resultRowToFeedback)
            .singleOrNull()
    }

    override suspend fun addFeedback(
        message: String,
        username: String,
        productId: Int
    ): Feedback? = dbQuery{

        val insertStatement = Feedbacks.insert {
            it[Feedbacks.message] = message
            it[Feedbacks.username] = username
            it[Feedbacks.productId] = productId
        }
        insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToFeedback)

    }

    override suspend fun deleteFeedback(id: Int): Boolean = dbQuery{
        Feedbacks.deleteWhere { Feedbacks.id eq id } > 0

    }

    override suspend fun feedbacksByProductId(id: Int): List<Feedback> = dbQuery{
        Feedbacks
            .select { Feedbacks.id eq id }
            .map(::resultRowToFeedback)
    }
}