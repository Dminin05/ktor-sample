package com.example.models

import com.example.dto.product.ProductDto
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

object Products : IntIdTable() {
    val title = varchar("title", 1024)
    val price = integer("price")
    val categoryTitle = varchar("categoryTitle", 1024).nullable()
}

class ProductDao(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<ProductDao>(Products)

    var title by Products.title
    var price by Products.price
    var categoryTitle by Products.categoryTitle

    fun toProduct() = ProductDto(
        id.value,
        title,
        price,
        mutableListOf(),
        categoryTitle
    )

}