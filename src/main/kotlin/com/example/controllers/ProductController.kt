package com.example.controllers

import com.example.models.Customer
import com.example.models.Product
import com.example.services.CustomerService
import com.example.services.ProductService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Routing.configureProductRouting()  = route("/products") {

    config()

//    val productService: ProductService by inject()
//
//    routing {
//        get{
//            call.respond(productService.getAllProducts())
//        }
//        get("/{id}"){
//            call.respond(productService.getProductById(Integer.parseInt(call.parameters["id"])))
//        }
//        post("") {
//            val product = call.receive<Product>()
//            productService.addProduct(product)
//            call.respond(HttpStatusCode.OK)
//        }
//        delete("/{id}"){
//            productService.deleteProduct(Integer.parseInt(call.parameters["id"]))
//        }
//    }

}

private fun Route.config(){

    val productService: ProductService by inject()

    get{
        call.respond(productService.getAllProducts())
    }
    get("/{id}"){
        call.respond(productService.getProductById(Integer.parseInt(call.parameters["id"])))
    }
    post{
        val product = call.receive<Product>()
        productService.addProduct(product)
        call.respond(HttpStatusCode.OK)
    }
    delete("/{id}"){
        productService.deleteProduct(Integer.parseInt(call.parameters["id"]))
    }
}