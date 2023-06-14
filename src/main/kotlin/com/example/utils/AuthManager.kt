package com.example.utils

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.routing.*
import java.util.*

fun createToken(username: String, role: String): String? {

    val jwtSecret = "secret"
    val jwtIssuer = "http://0.0.0.0:8080/"
    val jwtAudience = "http://0.0.0.0:8080/hello"

    val token = JWT.create()
        .withAudience(jwtAudience)
        .withIssuer(jwtIssuer)
        .withClaim("username", username)
        .withClaim("role", role)
        .withExpiresAt(Date(System.currentTimeMillis() + 600000))
        .sign(Algorithm.HMAC256(jwtSecret))

    return token

}

fun getUsernameFromToken(call: ApplicationCall): String{
    val principal = call.principal<JWTPrincipal>()
    return principal!!.payload.getClaim("username").asString()
}

fun getRole(call: ApplicationCall): String{
    val principal = call.principal<JWTPrincipal>()
    return principal!!.payload.getClaim("role").asString()
}
