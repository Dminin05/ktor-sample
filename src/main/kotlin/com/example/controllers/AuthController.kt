package com.example.controllers

import com.example.dtos.LoginRequest
import com.example.services.CustomerService
import com.example.utils.createToken
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Routing.configureAuthRouting() = route("/auth"){

    config()

}

private fun Route.config(){

    val customerService by inject<CustomerService>()

    post("/login") {
        val customerToLogin = call.receive<LoginRequest>()
        val customer = customerService.getCustomerByUsername(customerToLogin.username)
        if (customer?.password == customerToLogin.password){
            val token = createToken(customerToLogin.username)
            call.respond(hashMapOf("token" to token))
        } else {
            call.respond("bad password or username")
        }

    }

}