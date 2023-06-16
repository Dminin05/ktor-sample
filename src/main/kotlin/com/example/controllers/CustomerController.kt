package com.example.controllers

import com.example.models.CartItem
import com.example.models.Customer
import com.example.services.*
import com.example.utils.getUsernameFromToken
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
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
    val cartService by inject<CartService>()
    val roleService by inject<RoleService>()

    get{

        call.respond(customerService.getAllCustomers())

    }

    get("/roles") {

        val roles = roleService.getAllRoles()

        call.respond(roles)

    }

    authenticate("user") {
        get("/info"){

            val username = getUsernameFromToken(call)
            val customer = customerService.getCustomerByUsername(username)

            call.respond(customer)

        }
    }

    authenticate("user"){
        get("/cart"){

            val username = getUsernameFromToken(call)

            call.respond(cartService.getCart(username))

        }
    }

    authenticate("user"){
        post("/cart/{productId}"){

            val username = getUsernameFromToken(call)
            val productId = call.parameters.getOrFail<Int>("productId")

            cartService.addProductInCart(CartItem(null, username, productId))

            call.respond(HttpStatusCode.OK)

        }
    }

    post {

        val customer = call.receive<Customer>()

        if(customerService.addCustomer(customer) == null){

            call.respond(HttpStatusCode.Conflict, "user with username = ${customer.username}, already exists")

        } else {

            call.respond(HttpStatusCode.OK)

        }

    }

    authenticate("user") {
        delete("/{id}"){

            val customerId = call.parameters.getOrFail<Int>("id")

            customerService.deleteCustomer(customerId)

        }
    }


}