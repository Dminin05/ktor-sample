package com.example.config

import io.ktor.server.config.*
import io.ktor.server.config.ConfigLoader.Companion.load

object Config {

    private val root = ConfigLoader.load()

    val authConfig = AuthConfig(root)

}