package com.example.services

import com.example.dto.product.PageResult
import com.example.dto.product.ProductDto
import com.example.models.ProductDao
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ProductService : KoinComponent {

    val feedbackService by inject<FeedbackService>()

    fun getAllProducts(): List<ProductDto> = transaction {

        val list = ProductDao.all().map(ProductDao::toProduct)
        val newList = mutableListOf<ProductDto>()

        list.forEach{
            val feedbacks = feedbackService.getFeedbacksByProductId(it.id!!).toMutableList()
            it.feedbacks = feedbacks
            newList.add(it)
        }

        return@transaction newList
    }

    fun getPageProducts(offset: Long): PageResult<ProductDto> = transaction {

        val limit = 2

        val productsList = ProductDao.all().limit(limit, offset = offset).map(ProductDao::toProduct)
        val page = PageResult<ProductDto>(offset, limit, productsList)

        return@transaction page
    }

    fun getProductById(id: Int): ProductDto = transaction {

        val product = ProductDao.findById(id)!!.toProduct()
        val feedbacks = feedbackService.getFeedbacksByProductId(product.id!!).toMutableList()

        product.feedbacks = feedbacks

        return@transaction product
    }
    fun addProduct(product: ProductDto) = transaction {

        ProductDao.new {
            this.title = product.title
            this.price = product.price
        }

    }
    fun deleteProduct(id: Int) = transaction {

        ProductDao.findById(id)!!.delete()

    }

}