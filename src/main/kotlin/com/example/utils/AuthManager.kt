package com.example.utils

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import java.util.*

fun createToken(username: String): String? {

    val jwtSecret = "secret"
    val jwtIssuer = "http://0.0.0.0:8080/"
    val jwtAudience = "http://0.0.0.0:8080/hello"

    val token = JWT.create()
        .withAudience(jwtAudience)
        .withIssuer(jwtIssuer)
        .withClaim("username", username)
        .withExpiresAt(Date(System.currentTimeMillis() + 600000))
        .sign(Algorithm.HMAC256(jwtSecret))

    return token

}

fun getUsernameFromToken(principal: JWTPrincipal?): String{
    return principal!!.payload.getClaim("username").asString()
}