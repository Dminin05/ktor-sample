package com.example

import io.ktor.server.application.*
import com.example.plugins.*
import com.example.utils.DatabaseFactory
import io.ktor.server.netty.EngineMain


fun main(
    args: Array<String>
): Unit = EngineMain.main(args)

fun Application.module() {

    DatabaseFactory.init()
    configureKoin()
    configureAuth()
    configureSerialization()
    configureRouting()

}
