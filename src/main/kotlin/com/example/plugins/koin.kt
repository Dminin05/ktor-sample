package com.example.plugins

import com.example.services.*
import io.ktor.server.application.*
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin

val customerModule = module{
    single(createdAtStart = true) { CustomerService() }
}

val productModule = module{
    single { ProductService() }
}

val cartModule = module{
    single { CartService() }
}

val roleModule = module{
    single { RoleService() }
}

val feedbackModule = module{
    single { FeedbackService() }
}

fun Application.configureKoin() = install(Koin) {
    modules(roleModule, customerModule, productModule, cartModule, feedbackModule)
}
