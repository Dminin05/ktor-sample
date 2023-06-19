package com.example.extensions

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*

fun ApplicationCall.getUsernameFromToken(): String{
    val principal = principal<JWTPrincipal>()
    return principal!!.payload.getClaim("username").asString()
}

fun ApplicationCall.getRole(): String{
    val principal = principal<JWTPrincipal>()
    return principal!!.payload.getClaim("role").asString()
}