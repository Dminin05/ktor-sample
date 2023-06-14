package com.example.dao.productDao

import com.example.models.Product

interface IProductDao {

    suspend fun allProducts(): List<Product>
    suspend fun product(id: Int): Product?
    suspend fun addNewProduct(title: String, price: Int): Product?
    suspend fun deleteProduct(id: Int): Boolean

}