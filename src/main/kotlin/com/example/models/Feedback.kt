package com.example.models

import com.example.dto.feedback.FeedbackDto
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

object Feedbacks : IntIdTable() {

    val message = varchar("message", 128)
    val username = varchar("username", 128).references(Customers.username)
    val productId = integer("productId").references(Products.id)
    val status = enumeration<FeedbackStatus>("status")

}

class FeedbackDao(id: EntityID<Int>) : IntEntity(id) {

    companion object : IntEntityClass<FeedbackDao>(Feedbacks)

    var message by Feedbacks.message
    var username by Feedbacks.username
    var productId by Feedbacks.productId
    var status by Feedbacks.status

    fun toFeedback() = FeedbackDto(
        id.value,
        message,
        username,
        productId,
        status
    )

}