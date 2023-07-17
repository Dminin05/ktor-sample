package com.example.services.role

import com.example.models.Role


interface IRoleService {

    fun getAllRoles(): List<Role>

    fun addRole(
        role: Role
    ): Unit

    fun getRole(
        id: Int
    ): Role

}