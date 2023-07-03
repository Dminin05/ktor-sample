package com.example.controllers

import com.example.dto.product.ProductDto
import com.example.services.category.CategoryService
import com.example.services.category.ICategoryService
import com.example.services.product.IProductService
import com.example.services.product.ProductService
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

    val productService by inject<IProductService>()
    val categoryService by inject<ICategoryService>()

    get{

        call.respond(productService.getAllProducts())

    }

    get("{categoryTitle}") {

        val categoryTitle = call.parameters.getOrFail<String>("categoryTitle")
        call.respond(categoryService.getProductsByCategory(categoryTitle))

    }

    get("page/{offset}") {

        val offset = call.parameters.getOrFail<Long>("offset")

        call.respond(productService.getPageProducts(offset))

    }

    route("/{id}"){

        get{

            val id = call.parameters.getOrFail<Int>("id")
            val product = productService.getProductById(id)

            call.respond(product)

        }

        delete{

            productService.deleteProduct(Integer.parseInt(call.parameters["id"]))

        }

    }

    post{

        val product = call.receive<ProductDto>()
        productService.addProduct(product)

        call.respond(HttpStatusCode.NoContent)
    }

}