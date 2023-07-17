package com.example.models

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

object Products : IntIdTable() {
    val title = varchar("title", 1024).uniqueIndex()
    val price = integer("price")
    val categoryTitle = varchar("categoryTitle", 1024).nullable()
}

class ProductDao(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<ProductDao>(Products)

    var title by Products.title
    var price by Products.price
    var categoryTitle by Products.categoryTitle

    fun toProduct() = Product(
        id.value,
        title,
        price,
        mutableListOf(),
        categoryTitle
    )

}

@Serializable
data class Product(
    val id: Int? = null,
    val title: String,
    var price: Int,
    var feedbacks: MutableList<Feedback> = mutableListOf(),
    val categoryTitle: String?
)