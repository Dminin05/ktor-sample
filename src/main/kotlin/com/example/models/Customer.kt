package com.example.models

import com.example.dto.cart.CartDto
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable



object Customers : IntIdTable() {

    val name = varchar("name", 128)
    val surname = varchar("surname", 1024)
    val username = varchar("username", 1024).uniqueIndex()
    val password = varchar("password", 1024)
    val role = varchar("role", 1024).references(Roles.role)

}

class CustomerDao(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<CustomerDao>(Customers)

    var name by Customers.name
    var surname by Customers.surname
    var username by Customers.username
    var password by Customers.password
    var role by Customers.role

    fun toCustomer() = Customer(
        id.value,
        name,
        surname,
        username,
        password,
        role
    )

}

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