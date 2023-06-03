package com.example.controllers

import com.example.models.Customer
import com.example.services.CustomerService
import com.example.services.ProductService
import io.ktor.http.*
import io.ktor.server.application.*
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
    get("/{id}"){
        call.respond(customerService.getCustomerById(Integer.parseInt(call.parameters["id"])))
    }
    post("/addProductInCart/{customerId}/{productId}"){

        val customerId = call.parameters.getOrFail<Int>("customerId")
        val productId = call.parameters.getOrFail<Int>("productId")

        val customer = customerService.getCustomerById(customerId)
        val product = productService.getProductById(productId)

        customer.cart.addProductInCart(product)

        call.respond(HttpStatusCode.NoContent)
    }
    post {
        val customer = call.receive<Customer>()
        customerService.addCustomer(customer)
        call.respond(HttpStatusCode.OK)
    }
    delete("/{id}"){
        customerService.deleteCustomer(Integer.parseInt(call.parameters["id"]))
    }

}