package com.example.services

import com.example.dto.product.ProductDto
import com.example.models.CategoriesDao
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class CategoryService : KoinComponent {

    val productService by inject<ProductService>()
    fun getProductsByCategory(
        categoryTitle: String
    ): List<ProductDto> = transaction {

        val productsInOneCategory = mutableListOf<ProductDto>()

        val products = productService.getAllProducts()

        products.forEach {
            if (it.categoryTitle == categoryTitle) {
                productsInOneCategory.add(it)
            }
        }

        return@transaction productsInOneCategory
    }

    fun addCategory(
        title: String
    ): Unit = transaction {

        CategoriesDao.new {
            this.title = title
        }

    }

}