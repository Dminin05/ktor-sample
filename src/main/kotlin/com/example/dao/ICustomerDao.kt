package com.example.dao

import com.example.models.Customer

interface ICustomerDao {
    suspend fun allCustomers(): List<Customer>
    suspend fun customer(id: Int): Customer?
    suspend fun addNewCustomer(name: String, surname: String, username: String, password: String): Customer?
    suspend fun editCustomer(id: Int, name: String, surname: String): Boolean
    suspend fun deleteCustomer(id: Int): Boolean
}