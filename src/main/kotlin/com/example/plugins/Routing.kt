package com.example.plugins

import com.example.controllers.*
import io.ktor.server.application.*
import io.ktor.server.routing.*


fun Application.configureRouting() = routing {

    configureCustomerRouting()
    configureProductRouting()
    configureAuthRouting()
    configureFeedbackRouting()

}
