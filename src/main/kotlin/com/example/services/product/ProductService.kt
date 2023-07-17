package com.example.services.product

import com.example.dto.product.PageResult
import com.example.models.Product
import com.example.models.ProductDao
import com.example.services.feedback.IFeedbackService
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ProductService : IProductService, KoinComponent {

    val feedbackService by inject<IFeedbackService>()

    override fun getAllProducts(): List<Product> = transaction {

        val list = ProductDao.all().map(ProductDao::toProduct)
        val newList = mutableListOf<Product>()

        list.forEach{
            val feedbacks = feedbackService.getFeedbacksByProductId(it.id!!).toMutableList()
            it.feedbacks = feedbacks
            newList.add(it)
        }

        return@transaction newList
    }

    override fun getPageProducts(
        offset: Long
    ): PageResult<Product> = transaction {

        val limit = 2

        val productsList = ProductDao.all().limit(limit, offset = offset).map(ProductDao::toProduct)
        val page = PageResult<Product>(offset, limit, productsList)

        return@transaction page
    }

    override fun getProductById(
        id: Int
    ): Product = transaction {

        val product = ProductDao.findById(id)!!.toProduct()
        val feedbacks = feedbackService.getFeedbacksByProductId(product.id!!).toMutableList()

        product.feedbacks = feedbacks

        return@transaction product
    }
    override fun addProduct(
        product: Product
    ): Unit = transaction {

        ProductDao.new {
            this.title = product.title
            this.price = product.price
        }

    }
    override fun deleteProduct(
        id: Int
    ): Unit = transaction {

        ProductDao.findById(id)!!.delete()

    }

}