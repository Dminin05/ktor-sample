package com.example.dao.cartDao

import com.example.models.Cart

interface ICartDao {

    suspend fun cart(username: String): List<Cart>
    suspend fun addProductInCart(username: String, productId: Int): Cart?
    suspend fun deleteProductFromCart(username: String, productId: Int): Int

}