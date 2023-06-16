package com.example.services

import com.example.dtos.CartDto
import com.example.dtos.ProductDto
import com.example.models.CartItem
import com.example.models.CartItemDao
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class CartService : KoinComponent {

    val productService by inject<ProductService>()
    val feedbackService by inject<FeedbackService>()

    fun getCart(username: String): CartDto = transaction {

        val cartDto = CartDto()
        val list = CartItemDao.all()
            .filter { it.username == username }
            .map(CartItemDao::toCartItem)

        list.forEach{

            val product = productService.getProductById(it.productId)
            val productDto = ProductDto(product.title, product.price)
            val feedbacks = feedbackService.getFeedbacksByProductId(product.id!!).toMutableList()

            product.feedbacks = feedbacks
            cartDto.products.add(productDto)
            cartDto.price += product.price

        }

        return@transaction cartDto

    }

    fun addProductInCart(cartItem: CartItem) = transaction {

        CartItemDao.new {
            this.username = cartItem.username
            this.productId = cartItem.productId
        }

    }

    fun deleteProductFromCart(id: Int) = transaction {

        CartItemDao[id].delete()

    }

}