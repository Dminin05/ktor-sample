package com.example.services.cart

import com.example.dto.cart.CartDto
import com.example.dto.cart.CartItemDto

interface ICartService {

    fun getCart(
        username: String
    ): CartDto

    fun addProductInCart(
        cartItem: CartItemDto
    ): Unit

    fun deleteProductFromCart(
        id: Int
    ): Unit

    fun clearCart(
        username: String
    ): Unit

}