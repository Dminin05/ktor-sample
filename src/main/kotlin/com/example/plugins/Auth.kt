package com.example.plugins

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.example.config.AuthConfig
import com.example.config.Config
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*

private val config: AuthConfig
    get() = Config.authConfig

fun Application.configureAuth() {

    authentication {
        jwt("user"){
            realm = config.realm
            verifier(
                JWT
                    .require(Algorithm.HMAC256(config.secret))
                    .withAudience(config.audience)
                    .withIssuer(config.issuer)
                    .build()
            )
            validate { credential ->

                val payload = credential.payload
                val audience = payload.audience
                val claims = payload.claims

                if (config.audience !in audience) {
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
            realm = config.realm
            verifier(
                JWT
                    .require(Algorithm.HMAC256(config.secret))
                    .withAudience(config.audience)
                    .withIssuer(config.issuer)
                    .build()
            )
            validate { credential ->

                val payload = credential.payload
                val audience = payload.audience
                val claims = payload.claims

                if (config.audience !in audience) {
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