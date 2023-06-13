package com.example.dao

import com.example.models.Customer
import com.example.models.Customers
import com.example.utils.DatabaseFactory.dbQuery
import kotlinx.coroutines.runBlocking
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class CustomerDao : ICustomerDao {


    private fun resultRowToCustomer(row: ResultRow) = Customer(
        id = row[Customers.id],
        name = row[Customers.name],
        surname = row[Customers.surname],
        username = row[Customers.username],
        password = row[Customers.password],
    )

    override suspend fun allCustomers(): List<Customer> = dbQuery {
        Customers.selectAll().map(::resultRowToCustomer)
    }

    override suspend fun customer(id: Int): Customer? = dbQuery{
        Customers
            .select { Customers.id eq id }
            .map(::resultRowToCustomer)
            .singleOrNull()
    }

    override suspend fun customerByUsername(username: String): Customer? = dbQuery{
        Customers
            .select { Customers.username eq username }
            .map(::resultRowToCustomer)
            .singleOrNull()
    }

    override suspend fun addNewCustomer(
        name: String,
        surname: String,
        username: String,
        password: String)
    : Customer? = dbQuery{

        val insertStatement = Customers.insert {
            it[Customers.name] = name
            it[Customers.surname] = surname
            it[Customers.username] = username
            it[Customers.password] = password
        }
        insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToCustomer)

    }

    override suspend fun editCustomer(
        id: Int,
        name: String,
        surname: String)
    : Boolean = dbQuery{

        Customers.update({ Customers.id eq id }) {
            it[Customers.name] = name
            it[Customers.surname] = surname
        } > 0

    }

    override suspend fun deleteCustomer(id: Int): Boolean = dbQuery{
        Customers.deleteWhere { Customers.id eq id } > 0
    }

//    val dao: ICustomerDao = CustomerDao().apply {
//        runBlocking {
//            if(allCustomers().isEmpty()) {
//                addNewCustomer("dima", "test")
//            }
//        }
//    }
}