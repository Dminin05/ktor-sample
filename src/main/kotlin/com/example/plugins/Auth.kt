package com.example.plugins

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.example.utils.properties
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*

fun Application.configureAuth() {

    val properties = properties()

    authentication {
        jwt("user"){
            realm = properties.jwtRealm
            verifier(
                JWT
                    .require(Algorithm.HMAC256(properties.jwtSecret))
                    .withAudience(properties.jwtAudience)
                    .withIssuer(properties.jwtIssuer)
                    .build()
            )
            validate { credential ->

                val payload = credential.payload
                val audience = payload.audience
                val claims = payload.claims

                if (properties.jwtAudience !in audience) {
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
            realm = properties.jwtRealm
            verifier(
                JWT
                    .require(Algorithm.HMAC256(properties.jwtSecret))
                    .withAudience(properties.jwtAudience)
                    .withIssuer(properties.jwtIssuer)
                    .build()
            )
            validate { credential ->

                val payload = credential.payload
                val audience = payload.audience
                val claims = payload.claims

                if (properties.jwtAudience !in audience) {
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