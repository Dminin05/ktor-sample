package com.example.plugins

import com.example.dao.cartDao.CartDao
import com.example.dao.customerDao.CustomerDao
import com.example.dao.customerDao.ICustomerDao
import com.example.dao.productDao.ProductDao
import com.example.services.CartService
import com.example.services.CustomerService
import com.example.services.ProductService
import io.ktor.server.application.*
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin

val customerModule = module{
    single { CustomerService() }
    single<ICustomerDao>(createdAtStart = true) { CustomerDao() }
}

val productModule = module{
    single { ProductService() }
    single { ProductDao() }
}

val cartModule = module{
    single { CartDao() }
    single { CartService() }
}


fun Application.configureKoin() = install(Koin) {
    modules(customerModule, productModule, cartModule)
}