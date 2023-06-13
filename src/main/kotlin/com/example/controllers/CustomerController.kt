package com.example.controllers

import com.example.models.Customer
import com.example.services.CustomerService
import com.example.services.ProductService
import com.example.utils.getUsernameFromToken
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.util.*
import org.koin.ktor.ext.inject


fun Routing.configureCustomerRouting() = route("/customers"){

    config()

}

private fun Route.config(){

    val customerService by inject<CustomerService>()
    val productService by inject<ProductService>()


    get{
        call.respond(customerService.getAllCustomers())
    }
    authenticate("auth"){
        get("/info"){
            val username = getUsernameFromToken(call)
            val customer = customerService.getCustomerByUsername(username)
            call.respond(customer!!)
        }
    }



//    post("/addProductInCart/{customerId}/{productId}"){
//
//        val customerId = call.parameters.getOrFail<Int>("customerId")
//        val productId = call.parameters.getOrFail<Int>("productId")
//
//        val customer = customerService.getCustomerById(customerId)
//        val product = productService.getProductById(productId)
//
//        customer.cart.addProductInCart(product)
//
//        call.respond(HttpStatusCode.NoContent)
//    }

    post {
        val (_, name, surname, username, password) = call.receive<Customer>()
        if (customerService.getCustomerByUsername(username) == null) {
            customerService.addCustomer(name, surname, username, password)
            call.respond(HttpStatusCode.OK)
        } else {
            call.respond("user with username = $username, already exists")
        }
    }
    delete("/{id}"){
        val customerId = call.parameters.getOrFail<Int>("id")
        customerService.deleteCustomer(customerId)
    }

}