package com.example.services.role

import com.example.dto.customer.RoleDto

interface IRoleService {

    fun addRole(
        role: RoleDto
    )

    fun getRole(
        id: Int
    ): RoleDto

    fun getAllRoles(): List<RoleDto>

}