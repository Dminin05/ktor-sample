package com.example.services.customer

import com.example.models.Customer

interface ICustomerService {

    fun getAllCustomers(): MutableList<Customer>

    fun getCustomerById(
        id: Int
    ): Customer

    fun getCustomerByUsername(
        username: String
    ): Customer

    fun addCustomer(
        customer: Customer
    ): Customer?

    fun deleteCustomer(
        id: Int
    ): Unit

}