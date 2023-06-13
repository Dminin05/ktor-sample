package com.example.models

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table


@Serializable
data class Cart(val id: Int, val username: String, val productId: Int)

object Carts : Table() {
    val id = integer("id").autoIncrement()
    val username = varchar("username", 1024).references(Customers.username)
    val productId = integer("productId").references(Products.id)

    override val primaryKey = PrimaryKey(id)
}