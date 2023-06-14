package com.example.services

import com.example.dao.customerDao.ICustomerDao
import com.example.dao.roleDao.IRoleDao
import com.example.models.Customer
import kotlinx.coroutines.runBlocking
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


class CustomerService : KoinComponent {

    val custumerDao by inject<ICustomerDao>()
    val cartService by inject<CartService>()
    val roledao by inject<IRoleDao>()
    init {
        runBlocking {
            roledao.addRole("USER")
            roledao.addRole("ADMIN")
            custumerDao.addNewCustomer("dima", "minin", "user","user", "USER")
            custumerDao.addNewCustomer("anton", "minin", "admin","admin", "ADMIN")
        }
    }



    suspend fun getAllCustomers(): List<Customer> {
        return custumerDao.allCustomers()
    }

    suspend fun getCustomerById(id: Int): Customer? {
        return custumerDao.customer(id)
    }

    suspend fun getCustomerByUsername(username: String): Customer? {
        val customer = custumerDao.customerByUsername(username) ?: return null
        customer.cart = cartService.getCart(username)
        return customer
    }

    suspend fun addCustomer(
        name: String,
        surname: String,
        username: String,
        password: String,
        role: String) {

        custumerDao.addNewCustomer(name, surname, username, password, role)

    }

    suspend fun deleteCustomer(id: Int){
        custumerDao.deleteCustomer(id)
    }



}