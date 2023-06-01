package com.example.plugins

import com.example.models.ProductDao
import com.example.services.CustomerService
import io.ktor.server.application.*
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin

val customerModule = module{
    single { CustomerService() }
}

val productModule = module{
    single { ProductDao() }
}

fun Application.configureKoin() = install(Koin) {
    modules(customerModule, productModule)
}