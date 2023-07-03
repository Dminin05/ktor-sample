package com.example.services.order

import com.example.dto.order.OrderDto

interface IOrderService {

    fun createOrder(
        username: String
    ): OrderDto

    fun getOrdersByUsername(
        username: String
    ): MutableList<OrderDto>



}