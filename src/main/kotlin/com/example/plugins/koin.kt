package com.example.plugins

import com.example.dao.cartDao.CartDao
import com.example.dao.cartDao.ICartDao
import com.example.dao.customerDao.CustomerDao
import com.example.dao.customerDao.ICustomerDao
import com.example.dao.productDao.IProductDao
import com.example.dao.productDao.ProductDao
import com.example.dao.roleDao.IRoleDao
import com.example.dao.roleDao.RoleDao
import com.example.services.CartService
import com.example.services.CustomerService
import com.example.services.ProductService
import com.example.services.RoleService
import io.ktor.server.application.*
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin

val customerModule = module{
    single(createdAtStart = true) { CustomerService() }
    single<ICustomerDao>(createdAtStart = true) { CustomerDao() }
}

val productModule = module{
    single { ProductService() }
    single<IProductDao>(createdAtStart = true) { ProductDao() }
}

val cartModule = module{
    single { CartService() }
    single<ICartDao> { CartDao() }
}

val roleModule = module{
    single<IRoleDao> { RoleDao() }
    single { RoleService() }
}

fun Application.configureKoin() = install(Koin) {
    modules(roleModule, customerModule, productModule, cartModule)
}
