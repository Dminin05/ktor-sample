package com.example.dao.roleDao

import com.example.models.Products
import com.example.models.Role
import com.example.models.Roles
import com.example.utils.DatabaseFactory.dbQuery
import kotlinx.coroutines.runBlocking
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll

class RoleDao : IRoleDao{

    private fun resultRowToRole(row: ResultRow) = Role(
        id = row[Roles.id],
        role = row[Roles.role]
    )

    override suspend fun addRole(role: String): Role? = dbQuery{

        val insertStatement = Roles.insert {
            it[Roles.role] = role

        }
        insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToRole)

    }

    override suspend fun role(id: Int): Role? = dbQuery{

        Roles
            .select { Products.id eq id }
            .map(::resultRowToRole)
            .singleOrNull()

    }

    override suspend fun allRoles(): List<Role> = dbQuery{
        Roles.selectAll().map(::resultRowToRole)
    }
}