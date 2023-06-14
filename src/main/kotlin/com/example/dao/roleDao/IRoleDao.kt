package com.example.dao.roleDao

import com.example.models.Role

interface IRoleDao {

    suspend fun addRole(role: String): Role?
    suspend fun role(id: Int): Role?
    suspend fun allRoles(): List<Role>

}