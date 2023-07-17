package com.example.controllers

import com.example.extensions.getUsernameFromToken
import com.example.models.CartItem
import com.example.models.Customer
import com.example.services.cart.ICartService
import com.example.services.customer.CustomerService
import com.example.services.customer.ICustomerService
import com.example.services.order.IOrderService
import com.example.services.role.RoleService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.util.*
import org.koin.ktor.ext.inject


fun Routing.configureCustomerRouting() = route("/customers"){

    userConfig()
    adminConfig()

}

private fun Route.userConfig(){

    val customerService by inject<ICustomerService>()
    val cartService by inject<ICartService>()
    val orderService by inject<IOrderService>()

    authenticate("user") {

        get("/info") {

            val username = call.getUsernameFromToken()
            val customer = customerService.getCustomerByUsername(username)

            call.respond(customer)

        }

        get("/cart") {

            val username = call.getUsernameFromToken()

            call.respond(cartService.getCart(username))

        }

        post("/cart/{productId}") {

            val username = call.getUsernameFromToken()
            val productId = call.parameters.getOrFail<Int>("productId")

            cartService.addProductInCart(CartItem(null, username, productId))

            call.respond(HttpStatusCode.NoContent)

        }

        post("/order") {

            val username = call.getUsernameFromToken()
            val order = orderService.createOrder(username)

            call.respond(order)

        }

        get("/order") {

            val username = call.getUsernameFromToken()
            val orders = orderService.getOrdersByUsername(username)

            println(orders)

            call.respond(orders)

        }

        delete("/{id}") {

            val customerId = call.parameters.getOrFail<Int>("id")

            customerService.deleteCustomer(customerId)

        }

    }

}

private fun Route.adminConfig(){

    val customerService by inject<CustomerService>()
    val roleService by inject<RoleService>()

    authenticate("admin") {

        post {

            val customer = call.receive<Customer>()

            if(customerService.addCustomer(customer) == null){

                call.respond(HttpStatusCode.Conflict, "user with username = ${customer.username}, already exists")

            } else {

                call.respond(HttpStatusCode.NoContent)

            }

        }

        get{

            call.respond(customerService.getAllCustomers())

        }

        get("/roles") {

            val roles = roleService.getAllRoles()

            call.respond(roles)

        }

    }

}
