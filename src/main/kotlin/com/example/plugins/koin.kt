package com.example.plugins

import com.example.dao.CustomerDao
import com.example.dao.ICustomerDao
import com.example.models.Cart
import com.example.models.ProductDao
import com.example.services.CustomerService
import com.example.services.ProductService
import io.ktor.server.application.*
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin

val customerModule = module{
    single { CustomerService() }
    single<ICustomerDao> { CustomerDao() }
}

val productModule = module{
    single { ProductService() }
    single { ProductDao() }
}

val cartModule = module{
    single { Cart() }
}


fun Application.configureKoin() = install(Koin) {
    modules(customerModule, productModule, cartModule)
}