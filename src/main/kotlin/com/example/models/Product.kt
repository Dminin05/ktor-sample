package com.example.models

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Table

@Serializable
data class Product(
    val id: Int? = null,
    val title: String,
    var price: Int,
    var feedbacks: MutableList<Feedback> = mutableListOf()
)

object Products : IntIdTable() {
    val title = varchar("title", 1024)
    val price = integer("price")
}

class ProductDao(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<ProductDao>(Products)

    var title by Products.title
    var price by Products.price

    override fun toString(): String {
        return "ProductDao(title='$title', price=$price)"
    }

    fun toProduct() = Product(id.value, title, price)

}