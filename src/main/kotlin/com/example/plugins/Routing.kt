package com.example.plugins

import com.example.controllers.configureCustomerRouting
import io.ktor.server.application.*



fun Application.configureRouting() {

    configureCustomerRouting()


}
