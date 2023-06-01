package com.example.controllers

import com.example.models.Customer
import com.example.services.CustomerService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject


fun Routing.configureCustomerRouting() = route("/customers"){

    config()
//    val customerService: CustomerService by inject()
//    routing {
//        get{
//            call.respond(customerService.getAllCustomers())
//        }
//        get("/{id}"){
//            call.respond(customerService.getCustomerById(Integer.parseInt(call.parameters["id"])))
//        }
//        post("/customer") {
//            val customer = call.receive<Customer>()
//            customerService.addCustomer(customer)
//            call.respond(HttpStatusCode.OK)
//        }
//        delete("/delete/{id}"){
//            customerService.deleteCustomer(Integer.parseInt(call.parameters["id"]))
//        }
//    }

}

private fun Route.config(){
    val customerService: CustomerService by inject()
    get{
        call.respond(customerService.getAllCustomers())
    }
    get("/{id}"){
        call.respond(customerService.getCustomerById(Integer.parseInt(call.parameters["id"])))
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