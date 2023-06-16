package com.example.models

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Table

@Serializable
data class Feedback(
    val id: Int? = null,
    val message: String,
    val username: String,
    val productId: Int
)

object Feedbacks : IntIdTable() {

    val message = varchar("message", 128)
    val username = varchar("username", 128).references(Customers.username)
    val productId = integer("productId").references(Products.id)

}

class FeedbackDao(id: EntityID<Int>) : IntEntity(id) {

    companion object : IntEntityClass<FeedbackDao>(Feedbacks)

    var message by Feedbacks.message
    var username by Feedbacks.username
    var productId by Feedbacks.productId

    fun toFeedback() = Feedback(id.value, message, username, productId)

    override fun toString(): String {
        return "FeedbackDao(message='$message', username='$username', productId=$productId)"
    }

}