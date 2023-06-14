package com.example.dao.productDao

import com.example.dao.customerDao.CustomerDao
import com.example.dao.customerDao.ICustomerDao
import com.example.models.Product
import com.example.models.Products
import com.example.utils.DatabaseFactory.dbQuery
import kotlinx.coroutines.runBlocking
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class ProductDao : IProductsDao {


    private fun resultRowToProduct(row: ResultRow) = Product(
        id = row[Products.id],
        title = row[Products.title],
        price = row[Products.price]
    )
    override suspend fun allProducts(): List<Product> = dbQuery{
        Products.selectAll().map(::resultRowToProduct)
    }

    override suspend fun product(id: Int): Product? = dbQuery{
        Products
            .select { Products.id eq id }
            .map(::resultRowToProduct)
            .singleOrNull()
    }

    override suspend fun addNewProduct(
        title: String,
        price: Int)
    : Product? = dbQuery{

        val insertStatement = Products.insert {
            it[Products.title] = title
            it[Products.price] = price

        }
        insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToProduct)

    }

    override suspend fun deleteProduct(id: Int): Boolean = dbQuery{
        Products.deleteWhere { Products.id eq id } > 0
    }

//    val dao: IProductsDao = ProductDao().apply {
//        runBlocking {
//            if(allProducts().isEmpty()) {
//                addNewProduct("apple", 230)
//            }
//        }
//    }
}