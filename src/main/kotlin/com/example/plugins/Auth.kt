package com.example.plugins

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.config.*
import io.ktor.server.config.ConfigLoader.Companion.load


fun Application.configureAuth() {

    val jwtSecret = environment.config.propertyOrNull("jwt.secret")!!.getString()
    val jwtIssuer = environment.config.propertyOrNull("jwt.issuer")!!.getString()
    val jwtAudience = environment.config.propertyOrNull("jwt.audience")!!.getString()
    val jwtRealm = environment.config.propertyOrNull("jwt.realm")!!.getString()

    authentication {
        jwt("user"){
            realm = jwtRealm
            verifier(
                JWT
                    .require(Algorithm.HMAC256(jwtSecret))
                    .withAudience(jwtAudience)
                    .withIssuer(jwtIssuer)
                    .build()
            )
            validate { credential ->

                val payload = credential.payload
                val audience = payload.audience
                val claims = payload.claims

                if (jwtAudience !in audience) {
                    return@validate null
                }

                val role = claims["role"]?.asString()
                if (role != "USER" && role != "ADMIN") {
                    return@validate null
                }

                return@validate JWTPrincipal(credential.payload)
            }
        }

        jwt("admin"){
            realm = jwtRealm
            verifier(
                JWT
                    .require(Algorithm.HMAC256(jwtSecret))
                    .withAudience(jwtAudience)
                    .withIssuer(jwtIssuer)
                    .build()
            )
            validate { credential ->

                val payload = credential.payload
                val audience = payload.audience
                val claims = payload.claims

                if (jwtAudience !in audience) {
                    return@validate null
                }

                val role = claims["role"]?.asString()
                if (role != "ADMIN") {
                    return@validate null
                }

                return@validate JWTPrincipal(credential.payload)

            }
        }
    }
}