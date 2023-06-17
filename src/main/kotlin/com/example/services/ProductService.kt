package com.example.services

import com.example.dtos.PageResult
import com.example.models.Product
import com.example.models.ProductDao
import com.example.models.Products
import org.h2.mvstore.Page
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.awt.print.PageFormat
import java.awt.print.Pageable

class ProductService : KoinComponent {

    val feedbackService by inject<FeedbackService>()

    fun getAllProducts(): List<Product> = transaction {

        val list = ProductDao.all().map(ProductDao::toProduct)
        val newList = mutableListOf<Product>()

        list.forEach{
            val feedbacks = feedbackService.getFeedbacksByProductId(it.id!!).toMutableList()
            it.feedbacks = feedbacks
            newList.add(it)
        }

        return@transaction newList

    }

    fun getPageProducts(offset: Long): PageResult<Product> = transaction {

        val limit = 2

        val productsList = ProductDao.all().limit(limit, offset = offset).map(ProductDao::toProduct)
        val page = PageResult<Product>(offset, limit, productsList)

        return@transaction page

    }

    fun getProductById(id: Int): Product = transaction {

        val product = ProductDao[id].toProduct()
        val feedbacks = feedbackService.getFeedbacksByProductId(product.id!!).toMutableList()

        product.feedbacks = feedbacks

        return@transaction product

    }
    fun addProduct(product: Product) = transaction {

        ProductDao.new {
            this.title = product.title
            this.price = product.price
        }

    }
    fun deleteProduct(id: Int) = transaction {

        ProductDao[id].delete()

    }

}