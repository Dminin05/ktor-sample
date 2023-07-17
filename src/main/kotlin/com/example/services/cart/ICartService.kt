package com.example.services.cart

import com.example.dto.cart.CartDto
import com.example.models.CartItem

interface ICartService {

    fun getCart(
        username: String
    ): CartDto

    fun addProductInCart(
        cartItem: CartItem
    ): Unit

    fun deleteProductFromCart(
        id: Int
    ): Unit

    fun clearCart(
        username: String
    ): Unit

}