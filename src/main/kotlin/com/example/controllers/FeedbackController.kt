package com.example.controllers

import com.example.dto.feedback.ReceiveFeedback
import com.example.extensions.getUsernameFromToken
import com.example.models.Feedback
import com.example.services.feedback.FeedbackService
import com.example.services.feedback.IFeedbackService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.util.*
import org.koin.ktor.ext.inject

fun Routing.configureFeedbackRouting() = route("/feedback") {

    userConfig()
    adminConfig()

}

private fun Route.userConfig() {

    val feedbackService by inject<IFeedbackService>()

    authenticate("user") {

        post("/{productId}") {

            val message = call.receive<ReceiveFeedback>().message
            val productId = call.parameters.getOrFail<Int>("productId")
            val username = call.getUsernameFromToken()
            val feedback = Feedback(null, message, username, productId, Feedback.FeedbackStatus.UNCHECKED)

            feedbackService.addFeedback(feedback)

            call.respond(HttpStatusCode.NoContent)

        }

    }

}

private fun Route.adminConfig() {

    val feedbackService by inject<IFeedbackService>()

    authenticate("admin") {

        get {

            call.respond(feedbackService.getAllFeedbacks())

        }

        delete("/{id}") {

            val id = call.parameters.getOrFail<Int>("id")

            feedbackService.deleteFeedback(id)

        }

        patch("/refactorFeedback") {

            val feedbackId  = call.request.queryParameters.getOrFail<Int>("feedbackId")

            if (call.request.queryParameters["status"] == "accepted") {

                feedbackService.refactorFeedback(Feedback.FeedbackStatus.ACCEPTED, feedbackId)
                call.respond(HttpStatusCode.NoContent)

            }

            if (call.request.queryParameters["status"] == "rejected") {

                feedbackService.refactorFeedback(Feedback.FeedbackStatus.REJECTED, feedbackId)
                call.respond(HttpStatusCode.NoContent)

            }

            else {

                call.respond(HttpStatusCode.BadRequest)

            }

        }

    }

}
