package com.example.models

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table

@Serializable
data class Product(
    val id: Int? = null,
    val title: String,
    var price: Int,
    var feedbacks: MutableList<Feedback> = mutableListOf()
)

object Products : Table() {
    val id = integer("id").autoIncrement()
    val title = varchar("title", 1024)
    val price = integer("price")

    override val primaryKey = PrimaryKey(id)
}