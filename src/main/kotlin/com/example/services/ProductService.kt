package com.example.services

import com.example.models.Product
import com.example.models.ProductDao
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ProductService: KoinComponent{

    val feedbackService by inject<FeedbackService>()

    fun getAllProducts(): List<Product> = transaction{

        val list = ProductDao.all().map(ProductDao::toProduct)
        val newList = mutableListOf<Product>()

        list.forEach{
            val feedbacks = feedbackService.getFeedbacksByProductId(it.id!!).toMutableList()
            it.feedbacks = feedbacks
            newList.add(it)
        }

        return@transaction newList

    }
    fun getProductById(id: Int): Product = transaction{

        val product = ProductDao[id].toProduct()
        val feedbacks = feedbackService.getFeedbacksByProductId(product.id!!).toMutableList()

        product.feedbacks = feedbacks

        return@transaction product

    }
    fun addProduct(product: Product) = transaction{

        ProductDao.new {
            this.title = product.title
            this.price = product.price
        }

    }
    fun deleteProduct(id: Int) = transaction{

        ProductDao[id].delete()

    }

}