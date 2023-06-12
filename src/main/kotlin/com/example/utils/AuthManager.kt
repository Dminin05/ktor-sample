package com.example.utils

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import java.util.*

fun createToken(username: String): String? {

    val jwtSecret = "secret"
    val jwtIssuer = "http://0.0.0.0:8080/"
    val jwtAudience = "http://0.0.0.0:8080/hello"

    val token = JWT.create()
        .withAudience(jwtAudience)
        .withIssuer(jwtIssuer)
        .withClaim("username", username)
        .withExpiresAt(Date(System.currentTimeMillis() + 60000))
        .sign(Algorithm.HMAC256(jwtSecret))

    return token

}