package com.example.plugins

import com.example.models.Customer
import com.example.models.CustomerDao
import com.example.services.CustomerService
import io.ktor.http.*
import io.ktor.server.routing.*
import io.ktor.server.response.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import kotlinx.serialization.Serializable

fun Application.configureRouting() {

    val customerService = CustomerService()
    routing {
        get{
            call.respond(customerService.getAllCustomers())
        }
        post("/customer") {
            val customer = call.receive<Customer>()
            customerService.addCustomer(customer)
        }
        delete("/delete/{id}"){
            customerService.deleteCustomer(Integer.parseInt(call.parameters["id"]))
        }
    }


}
