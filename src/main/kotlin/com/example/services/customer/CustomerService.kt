package com.example.services.customer

import com.example.dto.customer.CustomerDto
import com.example.dto.customer.RoleDto
import com.example.dto.product.ProductDto
import com.example.models.CustomerDao
import com.example.services.product.ProductService
import com.example.services.role.RoleService
import com.example.services.cart.CartService
import com.example.services.cart.ICartService
import com.example.services.category.CategoryService
import com.example.services.category.ICategoryService
import com.example.services.feedback.FeedbackService
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
            roleService.addRole(RoleDto(null, "USER"))
            roleService.addRole(RoleDto(null, "ADMIN"))
            addCustomer(CustomerDto(null, "dima", "minin", "user","user", "USER"))
            addCustomer(CustomerDto(null, "anton", "verevkin", "admin", "admin", "ADMIN"))
            categoryService.addCategory("food")
            productService.addProduct(ProductDto(null,"apple", 222, mutableListOf(), "food"))
            productService.addProduct(ProductDto(null,"orange", 1888, mutableListOf(), "food"))
        }
    }



    override fun getAllCustomers(): MutableList<CustomerDto> = transaction {

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

    override fun getCustomerById(
        id: Int
    ): CustomerDto = transaction {

        val customer = CustomerDao.findById(id)!!.toCustomer()
        val feedbacks = feedbackService.getFeedbacksByUsername(customer.username).toMutableList()

        customer.feedbacks = feedbacks

        return@transaction customer
    }


    override fun getCustomerByUsername(
        username: String
    ): CustomerDto = transaction {

        val customer = CustomerDao.all()
            .first { it.username == username }
            .toCustomer()

        val feedbacks = feedbackService.getFeedbacksByUsername(customer.username).toMutableList()

        customer.cart = cartService.getCart(username)
        customer.feedbacks = feedbacks

        return@transaction customer
    }

    override fun addCustomer(
        customer: CustomerDto
    ): CustomerDto? = transaction {

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