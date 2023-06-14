package com.example.models

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table

@Serializable
data class Role(
    val id: Int? = null,
    val role: String
)

object Roles : Table() {

    val id = integer("id").autoIncrement()
    val role = varchar("role", 1024).uniqueIndex()

    override val primaryKey = PrimaryKey(id)
}


