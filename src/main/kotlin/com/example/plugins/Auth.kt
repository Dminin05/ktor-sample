package com.example.plugins

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*


fun Application.configureAuth() {


    val jwtSecret = "secret"
    val jwtIssuer = "http://0.0.0.0:8080/"
    val jwtAudience = "http://0.0.0.0:8080/hello"
    val jwtRealm = "Access to 'hello'"

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