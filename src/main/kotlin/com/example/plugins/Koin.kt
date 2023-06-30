package com.example.plugins

import com.example.services.auth.AuthService
import com.example.services.cart.CartService
import com.example.services.customer.CustomerService
import com.example.services.role.RoleService
import com.example.services.feedback.FeedbackService
import com.example.services.category.CategoryService
import com.example.services.product.ProductService
import com.example.utils.properties
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

val categoryModule = module{
    single { CategoryService() }
}

fun Application.configureKoin() = install(Koin) {

    val authModule = module{
        single { AuthService(properties()) }
    }

    modules(authModule, roleModule, customerModule, productModule, cartModule, feedbackModule, categoryModule)
}
