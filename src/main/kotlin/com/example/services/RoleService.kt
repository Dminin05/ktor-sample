package com.example.services

import com.example.dto.customer.RoleDto
import com.example.models.RoleDao
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.core.component.KoinComponent

class RoleService : KoinComponent {

    fun addRole(role: RoleDto) = transaction {

        RoleDao.new {
            this.role = role.role
        }

    }

    fun getRole(id: Int) = transaction {

        return@transaction RoleDao.findById(id)!!.toRole()
    }

    fun getAllRoles(): List<RoleDto> = transaction {

        return@transaction RoleDao.all().map(RoleDao::toRole)
    }

}