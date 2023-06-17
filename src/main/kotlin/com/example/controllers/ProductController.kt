package com.example.controllers

import com.example.models.Product
import com.example.services.ProductService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.util.*
import org.koin.ktor.ext.inject

fun Routing.configureProductRouting()  = route("/products") {

    config()

}

private fun Route.config(){

    val productService by inject<ProductService>()

    get{
        call.respond(productService.getAllProducts())
    }

    get("page/{offset}") {

        val offset = call.parameters.getOrFail<Long>("offset")

        call.respond(productService.getPageProducts(offset))

    }

    get("/{id}"){

        val id = call.parameters.getOrFail<Int>("id")
        val product = productService.getProductById(id)

        call.respond(product)

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