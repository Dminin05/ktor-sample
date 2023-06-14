package com.example.models

import com.example.dtos.CartDto
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table


@Serializable
data class Customer(
    val id: Int? = null,
    val name: String,
    val surname: String,
    val username: String,
    val password: String,
    val role: String,
    var cart: CartDto = CartDto(),
    var feedbacks: MutableList<Feedback> = mutableListOf()
)

object Customers : Table() {

    val id = integer("id").autoIncrement()
    val name = varchar("name", 128)
    val surname = varchar("surname", 1024)
    val username = varchar("username", 1024).uniqueIndex()
    val password = varchar("password", 1024)
    val role = varchar("role", 1024).references(Roles.role)

    override val primaryKey = PrimaryKey(id)

}