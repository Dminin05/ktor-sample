package com.example.models

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table

@Serializable
data class Feedback(
    val id: Int? = null,
    val message: String,
    val username: String,
    val productId: Int
)

object Feedbacks : Table() {

    val id = integer("id").autoIncrement()
    val message = varchar("message", 128)
    val username = varchar("username", 128).references(Customers.username)
    val productId = integer("productId").references(Products.id)


    override val primaryKey = PrimaryKey(id)

}