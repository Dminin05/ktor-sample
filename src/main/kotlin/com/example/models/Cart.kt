package com.example.models

import kotlinx.serialization.Serializable


@Serializable
class Cart(val productsInCart: MutableList<Product>, var totalPrice: Int){
    constructor():this(mutableListOf<Product>(), 0)

    fun addProductInCart(product: Product){
        productsInCart.add(product)
    }

    fun deleteProductFromCart(id: Int){
        productsInCart.remove(productsInCart.first(){it.id == id})
    }

    fun recalculate(): Unit{
        productsInCart.forEach {
            totalPrice += it.price
        }
    }
}