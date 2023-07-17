package com.example.services.cart

import com.example.dto.cart.CartDto
import com.example.dto.product.ProductDtoForCarts
import com.example.models.CartItem
import com.example.models.CartItemDao
import com.example.services.feedback.IFeedbackService
import com.example.services.product.IProductService
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class CartService : ICartService, KoinComponent {

    val productService by inject<IProductService>()
    val feedbackService by inject<IFeedbackService>()

    override fun getCart(
        username: String
    ): CartDto = transaction {

        val cartDto = CartDto()
        val list = CartItemDao.all()
            .filter { it.username == username }
            .map(CartItemDao::toCartItem)

        list.forEach{

            val product = productService.getProductById(it.productId)
            val productDto = ProductDtoForCarts(product.title, product.price)
            val feedbacks = feedbackService.getFeedbacksByProductId(product.id!!).toMutableList()

            product.feedbacks = feedbacks
            cartDto.products.add(productDto)
            cartDto.price += product.price

        }

        return@transaction cartDto
    }

    override fun addProductInCart(
        cartItem: CartItem
    ): Unit = transaction {

        CartItemDao.new {
            this.username = cartItem.username
            this.productId = cartItem.productId
        }

    }

    override fun deleteProductFromCart(
        id: Int
    ): Unit = transaction {

        CartItemDao.findById(id)!!.delete()

    }

    override fun clearCart(
        username: String
    ): Unit {

        val ids = mutableListOf<Int>()
        CartItemDao.all().filter { it.username == username}.forEach {

            ids.add(it.id.toString().toInt())

        }

        ids.forEach {

            CartItemDao.findById(it)!!.delete()

        }

    }

}