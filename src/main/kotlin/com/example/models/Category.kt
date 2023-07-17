package com.example.models

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

object Categories : IntIdTable() {

    val title = varchar("title", 1024)

}

class CategoriesDao(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<CategoriesDao>(Categories)

    var title by Categories.title

    fun toSerializable() = Category(
        id.value,
        title
    )
}

@Serializable
data class Category(
    val id: Int? = null,
    val title: String,
    var products: MutableList<Product> = mutableListOf()
)
