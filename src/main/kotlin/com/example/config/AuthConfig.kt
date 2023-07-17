package com.example.config

import io.ktor.server.config.*

class AuthConfig(
    root: ApplicationConfig,
    path: String = "auth"
) {

    private val handle = root.config(path)

    val secret = handle.property("secret").getString()
    val issuer = handle.property("issuer").getString()
    val audience = handle.property("audience").getString()
    val realm = handle.property("realm").getString()

}