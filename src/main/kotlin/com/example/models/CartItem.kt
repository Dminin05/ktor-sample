package com.example.models

import com.example.dto.cart.CartItemDto
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

object CartItems : IntIdTable() {

    val username = varchar("username", 1024).references(Customers.username)
    val productId = integer("productId").references(Products.id)

}

class CartItemDao(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<CartItemDao>(CartItems)

    var username by CartItems.username
    var productId by CartItems.productId

    fun toCartItem() = CartItemDto(
        id.value,
        username,
        productId
    )

}