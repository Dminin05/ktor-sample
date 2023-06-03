package com.example.services

import com.example.models.Customer
import com.example.models.CustomerDao
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


class CustomerService: KoinComponent {

    val custumerDao by inject<CustomerDao>()

    fun getAllCustomers(): MutableList<Customer>{
        return custumerDao.getAllCustomers()
    }

    fun getCustomerById(id: Int): Customer{
        return custumerDao.getCustomerById(id)
    }

    fun addCustomer(customer: Customer){
        custumerDao.addCustomer(customer)
    }

    fun deleteCustomer(id: Int){
        custumerDao.deleteCustomer(id)
    }



}