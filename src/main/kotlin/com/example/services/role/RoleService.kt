package com.example.services.role

import com.example.dto.customer.RoleDto
import com.example.models.RoleDao
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.core.component.KoinComponent

class RoleService : IRoleService, KoinComponent {

    override fun addRole(
        role: RoleDto
    ): Unit = transaction {

        RoleDao.new {
            this.role = role.role
        }

    }

    override fun getRole(
        id: Int
    ): RoleDto = transaction {

        return@transaction RoleDao.findById(id)!!.toRole()
    }

    override fun getAllRoles(): List<RoleDto> = transaction {

        return@transaction RoleDao.all().map(RoleDao::toRole)
    }

}