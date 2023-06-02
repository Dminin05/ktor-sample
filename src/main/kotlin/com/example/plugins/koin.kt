package com.example.plugins

import com.example.services.CustomerService
import com.example.services.ProductService
import io.ktor.server.application.*
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin

val customerModule = module{
    single { CustomerService() }
}

val productModule = module{
    single { ProductService() }
}

fun Application.configureKoin() = install(Koin) {
    modules(customerModule, productModule)
}