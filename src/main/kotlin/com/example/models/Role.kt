package com.example.models

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

@Serializable
data class Role(
    val id: Int? = null,
    val role: String
)

object Roles : IntIdTable() {

    val role = varchar("role", 1024).uniqueIndex()

}

class RoleDao(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<RoleDao>(Roles)

    var role by Roles.role

    override fun toString(): String {
        return "RoleDao(role='$role')"
    }

    fun toRole() = Role(id.value, role)

}


