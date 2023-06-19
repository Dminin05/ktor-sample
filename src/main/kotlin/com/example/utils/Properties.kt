package com.example.utils

import com.example.dto.auth.PropertiesDto
import io.ktor.server.application.*

fun Application.properties():  PropertiesDto {

    val jwtSecret = environment.config.propertyOrNull("jwt.secret")!!.getString()
    val jwtIssuer = environment.config.propertyOrNull("jwt.issuer")!!.getString()
    val jwtAudience = environment.config.propertyOrNull("jwt.audience")!!.getString()
    val jwtRealm = environment.config.propertyOrNull("jwt.realm")!!.getString()

    val propertiesDto = PropertiesDto(jwtSecret, jwtIssuer, jwtAudience, jwtRealm)

    return propertiesDto
}