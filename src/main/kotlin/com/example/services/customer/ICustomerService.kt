package com.example.services.customer

import com.example.dto.customer.CustomerDto

interface ICustomerService {

    fun getAllCustomers(): MutableList<CustomerDto>

    fun getCustomerById(
        id: Int
    ): CustomerDto

    fun getCustomerByUsername(
        username: String
    ): CustomerDto

    fun addCustomer(
        customer: CustomerDto
    ): CustomerDto?

    fun deleteCustomer(
        id: Int
    ): Unit

}