package com.example.services

import com.example.dto.order.OrderDto
import com.example.dto.order.OrderItemDto
import com.example.models.OrderItemDao
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class OrderService : KoinComponent{

    val customerService by inject<CustomerService>()
    val cartService by inject<CartService>()

    fun createOrder(username: String): OrderDto = transaction {

        val orderId: Int

        if (!OrderItemDao.all().map(OrderItemDao::toOrderItem).isEmpty()) {

            orderId = OrderItemDao.all().sortedByDescending { it.orderId }.first().toOrderItem().orderId + 1

        } else {

            orderId = 1

        }

        val customer = customerService.getCustomerByUsername(username)
        val productsInCart = customer.cart.products

        productsInCart.forEach {

            OrderItemDao.new {
                this.orderId = orderId
                this.username = username
                this.productTitle = it.title
                this.price = it.price
            }

        }

        val orderItems = OrderItemDao.all().filter { it.orderId == orderId }.map(OrderItemDao::toOrderItem).toMutableList()
        val orderDto = OrderDto(orderId, orderItems)

        orderItems.forEach {

            orderDto.totalPrice += it.price

        }

        cartService.clearCart(username)

        return@transaction orderDto
    }

    fun getOrdersByUsername(username: String): MutableList<OrderDto> = transaction {

        val orders = mutableListOf<OrderDto>()
        val orderItems = OrderItemDao.all().filter { it.username == username }.map(OrderItemDao::toOrderItem)
        val ids = mutableSetOf<Int>()

        orderItems.forEach {

            ids.add(it.orderId)

        }

        ids.forEach {

            val orderId = it

            val items = OrderItemDao.all().filter { it.orderId == orderId }.map(OrderItemDao::toOrderItem).toMutableList()
            val orderDto = OrderDto(orderId, items)

            items.forEach {

                orderDto.totalPrice += it.price

            }

            orders.add(orderDto)

        }

        return@transaction orders
    }

}