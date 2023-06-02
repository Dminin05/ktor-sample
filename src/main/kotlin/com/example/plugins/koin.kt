package com.example.plugins

import com.example.models.Cart
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

val cartModule = module{
    single { Cart() }
}

fun Application.configureKoin() = install(Koin) {
    modules(customerModule, productModule, cartModule)
}