package com.example.controllers

import com.example.dtos.ReceiveFeedback
import com.example.models.Feedback
import com.example.services.FeedbackService
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
            val feedback = Feedback(null, message, username, productId)

            feedbackService.addFeedback(feedback)

            call.respond(HttpStatusCode.OK)

        }
    }

    authenticate("admin"){
        delete("/{id}"){

            val id = call.parameters.getOrFail<Int>("id")

            feedbackService.deleteFeedback(id)

        }
    }

}