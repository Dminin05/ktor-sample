package com.example.models

import kotlinx.serialization.Serializable


class ProductDao(){

    private val products: MutableList<Product> = mutableListOf()
    var id = 1


    fun getAllProducts(): MutableList<Product>{
        return products
    }
    fun getProductById(id: Int): Product{
        return products.first(){it.id == id}
    }
    fun addProduct(product: Product){
        products.add(product)
    }

    fun deleteProduct(id: Int){
        products.remove(products.first(){it.id == id})
    }



}
@Serializable
data class Product(val id: Int, val title: String, var price: Int)