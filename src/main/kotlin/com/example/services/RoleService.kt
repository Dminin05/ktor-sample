package com.example.services

import com.example.models.Role
import com.example.models.RoleDao
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.core.component.KoinComponent

class RoleService : KoinComponent{

    fun addRole(role: Role) = transaction{

        RoleDao.new {
            this.role = role.role
        }

    }

    fun getRole(id: Int) = transaction{
        return@transaction RoleDao[id].toRole()
    }

    fun getAllRoles(): List<Role> = transaction{
        return@transaction RoleDao.all().map(RoleDao::toRole)
    }

}