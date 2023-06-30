package com.example.services.product

import com.example.dto.product.PageResult
import com.example.dto.product.ProductDto

interface IProductService {

    fun getAllProducts(): List<ProductDto>

    fun getPageProducts(
        offset: Long
    ): PageResult<ProductDto>

    fun getProductById(
        id: Int
    ): ProductDto

    fun addProduct(
        product: ProductDto
    ): Unit

    fun deleteProduct(
        id: Int
    ): Unit

}