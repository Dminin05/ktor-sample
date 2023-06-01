package com.example.services

import com.example.models.Product
import com.example.models.ProductDao

class ProductService {

    private val productDao = ProductDao()

    fun getAllProducts(): MutableList<Product>{
        return productDao.getAllProducts()
    }
    fun getProductById(id: Int): Product {
        return productDao.getProductById(id)
    }
    fun addProduct(product: Product){
        productDao.addProduct(product)
    }

    fun deleteProduct(id: Int){
        productDao.deleteProduct(id)
    }

}