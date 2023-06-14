package com.example.controllers

import com.example.dtos.LoginRequest
import com.example.dtos.ReceiveFeedback
import com.example.services.CartService
import com.example.services.CustomerService
import com.example.services.FeedbackService
import com.example.services.RoleService
import com.example.utils.createToken
import com.example.utils.getUsernameFromToken
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.util.*
import org.koin.ktor.ext.inject

fun Routing.configureFeedbackRouting() = route("/feedback"){

    config()

}

private fun Route.config(){

    val feedbackService by inject<FeedbackService>()

    authenticate("admin"){
        get{
            call.respond(feedbackService.getAllFeedbacks())
        }
    }

    authenticate("user"){
        post("/{productId}"){

            val message = call.receive<ReceiveFeedback>().message
            val productId = call.parameters.getOrFail<Int>("productId")
            val username = getUsernameFromToken(call)

            feedbackService.addFeedback(message, username, productId)

            call.respond(HttpStatusCode.NoContent)

        }
    }

    authenticate("admin"){
        delete("/{id}"){

            val id = call.parameters.getOrFail<Int>("id")

            feedbackService.deleteFeedback(id)

        }
    }

}