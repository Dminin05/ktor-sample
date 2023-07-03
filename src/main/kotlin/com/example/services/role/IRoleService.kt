package com.example.services.role

import com.example.dto.customer.RoleDto

interface IRoleService {

    fun getAllRoles(): List<RoleDto>

    fun addRole(
        role: RoleDto
    ): Unit

    fun getRole(
        id: Int
    ): RoleDto

}