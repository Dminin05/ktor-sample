package com.example.services.customer

import com.example.models.*
import com.example.services.cart.ICartService
import com.example.services.category.ICategoryService
import com.example.services.feedback.IFeedbackService
import com.example.services.product.IProductService
import com.example.services.role.IRoleService
import kotlinx.coroutines.runBlocking
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.mindrot.jbcrypt.BCrypt


class CustomerService : ICustomerService, KoinComponent {

    val cartService by inject<ICartService>()
    val roleService by inject<IRoleService>()
    val productService by inject<IProductService>()
    val feedbackService by inject<IFeedbackService>()
    val categoryService by inject<ICategoryService>()

    init {
        runBlocking {
            roleService.addRole(Role(null, "USER"))
            roleService.addRole(Role(null, "ADMIN"))
            addCustomer(Customer(null, "dima", "minin", "user","user", "USER"))
            addCustomer(Customer(null, "anton", "verevkin", "admin", "admin", "ADMIN"))
            categoryService.addCategory("food")
            productService.addProduct(Product(null,"apple", 222, mutableListOf(), "food"))
            productService.addProduct(Product(null,"orange", 1888, mutableListOf(), "food"))
        }
    }



    override fun getAllCustomers(): MutableList<Customer> = transaction {

        val list = CustomerDao.all().map(CustomerDao::toCustomer)
        val newList = mutableListOf<Customer>()

        list.forEach {
            val feedbacks = feedbackService.getFeedbacksByUsername(it.username).toMutableList()
            it.cart = cartService.getCart(it.username)
            it.feedbacks = feedbacks
            newList.add(it)
        }

        return@transaction newList
    }

    override fun getCustomerById(
        id: Int
    ): Customer = transaction {

        val customer = CustomerDao.findById(id)!!.toCustomer()
        val feedbacks = feedbackService.getFeedbacksByUsername(customer.username).toMutableList()

        customer.feedbacks = feedbacks

        return@transaction customer
    }


    override fun getCustomerByUsername(
        username: String
    ): Customer = transaction {

        val customer = CustomerDao.all()
            .first { it.username == username }
            .toCustomer()

        val feedbacks = feedbackService.getFeedbacksByUsername(customer.username).toMutableList()

        customer.cart = cartService.getCart(username)
        customer.feedbacks = feedbacks

        return@transaction customer
    }

    override fun addCustomer(
        customer: Customer
    ): Customer? = transaction {

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

    override fun deleteCustomer(
        id: Int
    ): Unit = transaction {

        CustomerDao.findById(id)!!.delete()

    }



}