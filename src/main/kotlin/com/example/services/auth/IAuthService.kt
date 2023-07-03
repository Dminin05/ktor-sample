package com.example.services.auth

interface IAuthService {

    fun createToken(
        username: String,
        role: String
    ): String?

    fun checkPassword(
        receivePassword: String,
        username: String
    ): Boolean

}