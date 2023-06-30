package com.example.models

import com.example.dto.product.CategoryDto
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

    fun toSerializable() = CategoryDto(
        id.value,
        title
    )
}