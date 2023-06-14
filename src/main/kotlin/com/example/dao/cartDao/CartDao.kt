package com.example.dao.cartDao

import com.example.models.Cart
import com.example.models.Carts
import com.example.models.Products
import com.example.utils.DatabaseFactory.dbQuery
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class CartDao : ICartDao{

    private fun resultRowToCart(row: ResultRow) = Cart(

        id = row[Carts.id],
        username = row[Carts.username],
        productId = row[Carts.productId]

    )

    override suspend fun cart(username: String): List<Cart> = dbQuery{

        Carts
            .select(Carts.username eq username)
            .map(::resultRowToCart)

    }

    override suspend fun addProductInCart(
        username: String,
        productId: Int
    ): Cart? = dbQuery {

        val insertStatement = Carts.insert {
            it[Carts.username] = username
            it[Carts.productId] = productId

        }

        insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToCart)
    }

    override suspend fun deleteProductFromCart(
        username: String,
        productId: Int)
    : Int = dbQuery{

        Carts.deleteWhere { (Carts.username eq username) and (Carts.productId eq productId) }

    }
}