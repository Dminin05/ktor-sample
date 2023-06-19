package com.example.models

import com.example.dto.customer.RoleDto
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

object Roles : IntIdTable() {

    val role = varchar("role", 1024).uniqueIndex()

}

class RoleDao(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<RoleDao>(Roles)

    var role by Roles.role

    fun toRole() = RoleDto(
        id.value,
        role
    )

}


