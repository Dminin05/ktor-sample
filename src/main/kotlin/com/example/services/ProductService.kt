package com.example.services

import com.example.dao.ProductDao
import com.example.models.Product
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ProductService: KoinComponent{

    val productDao by inject<ProductDao>()

    suspend fun getAllProducts(): List<Product>{
        return productDao.allProducts()
    }
    suspend fun getProductById(id: Int): Product? {
        return productDao.product(id)
    }
    suspend fun addProduct(title: String, price: Int){
        productDao.addNewProduct(title, price)
    }
    suspend fun deleteProduct(id: Int){
        productDao.deleteProduct(id)
    }

}