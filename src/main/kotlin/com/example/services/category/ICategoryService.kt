package com.example.services.category

import com.example.dto.product.ProductDto

interface ICategoryService {

    fun getProductsByCategory(
        categoryTitle: String
    ): List<ProductDto>

    fun addCategory(
        title: String
    ): Unit

}