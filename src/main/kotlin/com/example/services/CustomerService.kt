package com.example.services

import com.example.models.Customer
import com.example.models.CustomerDao
import com.example.models.Product
import com.example.models.Role
import kotlinx.coroutines.runBlocking
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


class CustomerService : KoinComponent {

    val cartService by inject<CartService>()
    val roleService by inject<RoleService>()
    val productService by inject<ProductService>()
    val feedbackService by inject<FeedbackService>()

    init {
        runBlocking {
            roleService.addRole(Role(null, "USER"))
            roleService.addRole(Role(null, "ADMIN"))
            addCustomer(Customer(null, "dima", "minin", "user","user", "USER"))
            addCustomer(Customer(null, "anton", "verevkin", "admin","admin", "ADMIN"))
            productService.addProduct(Product(null,"apple", 222))
        }
    }



    suspend fun getAllCustomers(): MutableList<Customer> = newSuspendedTransaction {
        val list = CustomerDao.all().map(CustomerDao::toCustomer)
        val newList = mutableListOf<Customer>()

        list.forEach{
            val feedbacks = feedbackService.getFeedbacksByUsername(it.username).toMutableList()
            it.cart = cartService.getCart(it.username)
            it.feedbacks = feedbacks
            newList.add(it)
        }

        return@newSuspendedTransaction newList

    }

    fun getCustomerById(id: Int): Customer = transaction {
        val customer = CustomerDao[id].toCustomer()
        val feedbacks = feedbackService.getFeedbacksByUsername(customer.username).toMutableList()

        customer.feedbacks = feedbacks

        return@transaction customer

    }


    fun getCustomerByUsername(username: String): Customer = transaction {

        val customer = CustomerDao.all()
            .first { it.username == username }
            .toCustomer()

        val feedbacks = feedbackService.getFeedbacksByUsername(customer.username).toMutableList()

        customer.cart = cartService.getCart(username)
        customer.feedbacks = feedbacks

        return@transaction customer

    }

    fun addCustomer(customer: Customer): Customer? = transaction {

        val list = CustomerDao.all().map(CustomerDao::toCustomer)

        list.forEach {

            if (it.username == customer.username){
                return@transaction null
            }

        }

        CustomerDao.new {
            this.name = customer.name
            this.surname = customer.surname
            this.username = customer.username
            this.password = customer.password
            this.role = customer.role
        }

        return@transaction customer

    }

    fun deleteCustomer(id: Int) = transaction {

        CustomerDao[id].delete()

    }



}