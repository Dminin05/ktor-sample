package com.example.services.auth

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.example.config.AuthConfig
import com.example.config.Config
import com.example.dto.auth.PropertiesDto
import com.example.services.customer.CustomerService
import com.example.services.customer.ICustomerService
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.util.*
import org.mindrot.jbcrypt.BCrypt

class AuthService() : IAuthService, KoinComponent{

    private val config: AuthConfig
        get() = Config.authConfig

    val customerService by inject<ICustomerService>()

    override fun createToken(
        username: String,
        role: String
    ): String? {

        val token = JWT.create()
            .withAudience(config.audience)
            .withIssuer(config.issuer)
            .withClaim("username", username)
            .withClaim("role", role)
            .withExpiresAt(Date(System.currentTimeMillis() + 600000))
            .sign(Algorithm.HMAC256(config.secret))

        return token

    }

    override fun checkPassword(
        receivePassword: String,
        username: String
    ): Boolean {

        val customer = customerService.getCustomerByUsername(username)
        val isPasswordCorrect = BCrypt.checkpw(receivePassword, customer.password)
        return isPasswordCorrect
    }

}