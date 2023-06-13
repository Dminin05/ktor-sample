package com.example.dao

import com.example.models.Product

interface IProductsDao {

    suspend fun allProducts(): List<Product>
    suspend fun product(id: Int): Product?
    suspend fun addNewProduct(title: String, price: Int): Product?
    suspend fun deleteProduct(id: Int): Boolean

}