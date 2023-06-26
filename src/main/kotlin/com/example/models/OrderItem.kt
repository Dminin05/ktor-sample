package com.example.models

import com.example.dto.order.OrderItemDto
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

object OrderItems : IntIdTable() {

    val orderId = integer("orderId")
    val username = varchar("username", 1024).references(Customers.username)
    val productTitle = varchar("productTitle", 1024).references(Products.title)
    val price = integer("price")

}

class OrderItemDao(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<OrderItemDao>(OrderItems)

    var orderId by OrderItems.orderId
    var username by OrderItems.username
    var productTitle by OrderItems.productTitle
    var price by OrderItems.price

    fun toOrderItem() = OrderItemDto(
        id.value,
        orderId,
        username,
        productTitle,
        price
    )

}