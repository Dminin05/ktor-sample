package com.example.services

import com.example.models.Customer
import com.example.models.CustomerDao


class CustomerService {

    private val custumerDao = CustomerDao()

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