package com.example.services.product

import com.example.dto.product.PageResult
import com.example.models.Product

interface IProductService {

    fun getAllProducts(): List<Product>

    fun getPageProducts(
        offset: Long
    ): PageResult<Product>

    fun getProductById(
        id: Int
    ): Product

    fun addProduct(
        product: Product
    ): Unit

    fun deleteProduct(
        id: Int
    ): Unit

}