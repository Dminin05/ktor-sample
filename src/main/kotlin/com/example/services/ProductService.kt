package com.example.services

import com.example.dao.productDao.IProductDao
import com.example.models.Product
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ProductService: KoinComponent{

    val productDao by inject<IProductDao>()
    val feedbackService by inject<FeedbackService>()

    suspend fun getAllProducts(): List<Product>{

        val list = productDao.allProducts()
        var newList = mutableListOf<Product>()

        list.forEach{
            val feedbacks = feedbackService.getFeedbacksByProductId(it.id!!).toMutableList()
            it.feedbacks = feedbacks
            newList.add(it)
        }

        return newList

    }
    suspend fun getProductById(id: Int): Product? {

        val product = productDao.product(id)
        val feedbacks = feedbackService.getFeedbacksByProductId(product!!.id!!).toMutableList()

        product.feedbacks = feedbacks

        return product

    }
    suspend fun addProduct(title: String, price: Int){
        productDao.addNewProduct(title, price)
    }
    suspend fun deleteProduct(id: Int){
        productDao.deleteProduct(id)
    }

}