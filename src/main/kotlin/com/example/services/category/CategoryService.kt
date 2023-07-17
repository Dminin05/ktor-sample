package com.example.services.category

import com.example.models.CategoriesDao
import com.example.models.Product
import com.example.services.product.IProductService
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class CategoryService : ICategoryService, KoinComponent {

    val productService by inject<IProductService>()
    override fun getProductsByCategory(
        categoryTitle: String
    ): List<Product> = transaction {

        val productsInOneCategory = mutableListOf<Product>()

        val products = productService.getAllProducts()

        products.forEach {
            if (it.categoryTitle == categoryTitle) {
                productsInOneCategory.add(it)
            }
        }

        return@transaction productsInOneCategory
    }

    override fun addCategory(
        title: String
    ): Unit = transaction {

        CategoriesDao.new {
            this.title = title
        }

    }

}