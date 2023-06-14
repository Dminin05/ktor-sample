package com.example.services

import com.example.dao.cartDao.CartDao
import com.example.dao.cartDao.ICartDao
import com.example.dtos.CartDto
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class CartService : KoinComponent {

    val cartDao by inject<ICartDao>()
    val productService by inject<ProductService>()

    suspend fun getCart(username: String): CartDto {
        val cartDto = CartDto()
        val list = cartDao.cart(username)
        list.forEach{
            val product = productService.getProductById(it.id)
            cartDto.products.add(product!!)
            cartDto.price += product.price
        }
        return cartDto
    }

    suspend fun addProductInCart(
        username: String,
        productId: Int
    ) {

        cartDao.addProductInCart(username, productId)

    }

    suspend fun deleteProductFromCart(
        username: String,
        productId: Int)
    {

        cartDao.deleteProductFromCart(username, productId)

    }

}