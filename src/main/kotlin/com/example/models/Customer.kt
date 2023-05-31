package com.example.models

import kotlinx.serialization.Serializable

class CustomerDao(){

    private val customers: MutableList<Customer> = mutableListOf()
    var id = 1


    fun getAllCustomers(): MutableList<Customer>{
        return customers
    }
    fun addCustomer(customer: Customer){
       customers.add(customer)
    }

    fun deleteCustomer(id: Int){
       customers.remove(customers.first(){it.id == id})
    }

}


@Serializable
data class Customer(val id: Int, val name: String, val surname: String) {

}