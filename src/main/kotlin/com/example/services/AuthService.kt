package com.example.services

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.typesafe.config.ConfigFactory
import io.ktor.server.config.*
import org.koin.core.component.KoinComponent
import java.util.*

class AuthService : KoinComponent{

    val conf = HoconApplicationConfig(ConfigFactory.load())

    fun createToken(username: String, role: String): String? {

        val jwtSecret = conf.propertyOrNull("jwt.secret")!!.getString()
        val jwtIssuer = conf.propertyOrNull("jwt.issuer")!!.getString()
        val jwtAudience = conf.propertyOrNull("jwt.audience")!!.getString()

        val token = JWT.create()
            .withAudience(jwtAudience)
            .withIssuer(jwtIssuer)
            .withClaim("username", username)
            .withClaim("role", role)
            .withExpiresAt(Date(System.currentTimeMillis() + 600000))
            .sign(Algorithm.HMAC256(jwtSecret))

        return token

    }

}