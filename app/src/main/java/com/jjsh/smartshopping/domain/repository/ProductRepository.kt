package com.jjsh.smartshopping.domain.repository

import com.jjsh.smartshopping.domain.model.Product

interface ProductRepository {
    suspend fun getProducts(
        productId: Long,
        categoryId: Int?,
        direction: String,
        keyword: String?
    ): Result<List<Product>>

    suspend fun getProduct(
        productId: Long
    ): Result<Product>
}