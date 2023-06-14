package com.example.services

import com.example.dao.roleDao.IRoleDao
import com.example.models.Role
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class RoleService : KoinComponent{

    val roleDao by inject<IRoleDao>()

    suspend fun addRole(role: String){
        roleDao.addRole(role)
    }

    suspend fun getRole(id: Int){
        roleDao.role(id)
    }

    suspend fun getAllRoles(): List<Role> {
        return roleDao.allRoles()
    }

}