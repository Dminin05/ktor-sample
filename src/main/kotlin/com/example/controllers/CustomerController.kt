package com.example.controllers

import com.example.models.Customer
import com.example.services.CustomerService
import com.example.services.ProductService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject


fun Routing.configureCustomerRouting() = route("/customers"){

    config()

}

private fun Route.config(){
    val customerService: CustomerService by inject()
    val productService: ProductService by inject()
    get{
        call.respond(customerService.getAllCustomers())
    }
    get("/{id}"){
        call.respond(customerService.getCustomerById(Integer.parseInt(call.parameters["id"])))
    }
    get("/addProductInCart/{customerId}/{productId}"){
        call.respond(customerService.getCustomerById(Integer.parseInt(call.parameters["customerId"])).cart.addProductInCart(productService.getProductById(Integer.parseInt(call.parameters["productId"]))))
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