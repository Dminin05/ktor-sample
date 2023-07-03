package com.example.plugins

import com.example.services.auth.AuthService
import com.example.services.cart.CartService
import com.example.services.cart.ICartService
import com.example.services.category.CategoryService
import com.example.services.category.ICategoryService
import com.example.services.customer.CustomerService
import com.example.services.customer.ICustomerService
import com.example.services.feedback.FeedbackService
import com.example.services.feedback.IFeedbackService
import com.example.services.order.IOrderService
import com.example.services.order.OrderService
import com.example.services.product.IProductService
import com.example.services.product.ProductService
import com.example.services.role.IRoleService
import com.example.services.role.RoleService
import com.example.utils.properties
import io.ktor.server.application.*
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin

val customerModule = module{
    single<ICustomerService>(createdAtStart = true) { CustomerService() }
}

val productModule = module{
    single<IProductService> { ProductService() }
}

val cartModule = module{
    single<ICartService> { CartService() }
}

val roleModule = module{
    single<IRoleService> { RoleService() }
}

val feedbackModule = module{
    single<IFeedbackService> { FeedbackService() }
}

val orderModule = module{
    single<IOrderService> { OrderService() }
}

val categoryModule = module{
    single<ICategoryService> { CategoryService() }
}

fun Application.configureKoin() = install(Koin) {

    val authModule = module{
        single { AuthService(properties()) }
    }

    modules(authModule, roleModule, customerModule, productModule, cartModule, feedbackModule, orderModule, categoryModule)
}
