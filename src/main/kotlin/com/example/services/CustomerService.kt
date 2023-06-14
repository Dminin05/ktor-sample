package com.example.services

import com.example.dao.customerDao.ICustomerDao
import com.example.dao.productDao.IProductDao
import com.example.dao.roleDao.IRoleDao
import com.example.models.Customer
import com.example.models.Product
import kotlinx.coroutines.runBlocking
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


class CustomerService : KoinComponent {

    val custumerDao by inject<ICustomerDao>()
    val cartService by inject<CartService>()
    val roledao by inject<IRoleDao>()
    val productDao by inject<IProductDao>()
    val feedbackService by inject<FeedbackService>()

    init {
        runBlocking {
            roledao.addRole("USER")
            roledao.addRole("ADMIN")
            custumerDao.addNewCustomer("dima", "minin", "user","user", "USER")
            custumerDao.addNewCustomer("anton", "minin", "admin","admin", "ADMIN")
            productDao.addNewProduct("apple", 222)
        }
    }



    suspend fun getAllCustomers(): MutableList<Customer> {
        val list = custumerDao.allCustomers()
        var newList = mutableListOf<Customer>()

        list.forEach{
            val feedbacks = feedbackService.getFeedbacksByUsername(it.username).toMutableList()
            it.cart = cartService.getCart(it.username)
            it.feedbacks = feedbacks
            newList.add(it)
        }

        return newList

    }

    suspend fun getCustomerById(id: Int): Customer? {
        val customer = custumerDao.customer(id)
        val feedbacks = feedbackService.getFeedbacksByUsername(customer!!.username).toMutableList()

        customer.feedbacks = feedbacks

        return customer

    }


    suspend fun getCustomerByUsername(username: String): Customer? {

        val customer = custumerDao.customerByUsername(username) ?: return null
        val feedbacks = feedbackService.getFeedbacksByUsername(customer!!.username).toMutableList()

        customer.cart = cartService.getCart(username)
        customer.feedbacks = feedbacks

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