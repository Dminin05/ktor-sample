package com.example.models

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table


@Serializable
data class Customer(val id: Int? = null, val name: String, val surname: String) {

}

object Customers : Table() {
    val id = integer("id").autoIncrement()
    val name = varchar("name", 128)
    val surname = varchar("surname", 1024)

    override val primaryKey = PrimaryKey(id)
}