package com.example.services.category

import com.example.models.Product

interface ICategoryService {

    fun getProductsByCategory(
        categoryTitle: String
    ): List<Product>

    fun addCategory(
        title: String
    ): Unit

}