package com.example.services

import com.example.dto.customer.CustomerDto
import com.example.dto.customer.RoleDto
import com.example.dto.product.ProductDto
import com.example.models.CustomerDao
import kotlinx.coroutines.runBlocking
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.mindrot.jbcrypt.BCrypt


class CustomerService : KoinComponent {

    val cartService by inject<CartService>()
    val roleService by inject<RoleService>()
    val productService by inject<ProductService>()
    val feedbackService by inject<FeedbackService>()

    init {
        runBlocking {
            roleService.addRole(RoleDto(null, "USER"))
            roleService.addRole(RoleDto(null, "ADMIN"))
            addCustomer(CustomerDto(null, "dima", "minin", "user","user", "USER"))
            addCustomer(CustomerDto(null, "anton", "verevkin", "admin", "admin", "ADMIN"))
            productService.addProduct(ProductDto(null,"apple", 222))
        }
    }



    fun getAllCustomers(): MutableList<CustomerDto> = transaction {

        val list = CustomerDao.all().map(CustomerDao::toCustomer)
        val newList = mutableListOf<CustomerDto>()

        list.forEach {
            val feedbacks = feedbackService.getFeedbacksByUsername(it.username).toMutableList()
            it.cart = cartService.getCart(it.username)
            it.feedbacks = feedbacks
            newList.add(it)
        }

        return@transaction newList
    }

    fun getCustomerById(id: Int): CustomerDto = transaction {
        val customer = CustomerDao.findById(id)!!.toCustomer()
        val feedbacks = feedbackService.getFeedbacksByUsername(customer.username).toMutableList()

        customer.feedbacks = feedbacks

        return@transaction customer
    }


    fun getCustomerByUsername(username: String): CustomerDto = transaction {

        val customer = CustomerDao.all()
            .first { it.username == username }
            .toCustomer()

        val feedbacks = feedbackService.getFeedbacksByUsername(customer.username).toMutableList()

        customer.cart = cartService.getCart(username)
        customer.feedbacks = feedbacks

        return@transaction customer
    }

    fun addCustomer(customer: CustomerDto): CustomerDto? = transaction {

        val password = BCrypt.hashpw(customer.password, BCrypt.gensalt())
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
            this.password = password
            this.role = customer.role
        }

        return@transaction customer
    }

    fun deleteCustomer(id: Int) = transaction {

        CustomerDao.findById(id)!!.delete()

    }



}