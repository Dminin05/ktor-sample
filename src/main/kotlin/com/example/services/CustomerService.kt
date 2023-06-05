package com.example.services

import com.example.dao.ICustomerDao
import com.example.models.Customer
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


class CustomerService: KoinComponent {

    val custumerDao by inject<ICustomerDao>()

    suspend fun getAllCustomers(): List<Customer> {
        return custumerDao.allCustomers()
    }

    suspend fun getCustomerById(id: Int): Customer? {
        return custumerDao.customer(id)
    }

    suspend fun addCustomer(name: String, surname: String){
        custumerDao.addNewCustomer(name, surname)
    }

    suspend fun deleteCustomer(id: Int){
        custumerDao.deleteCustomer(id)
    }



}