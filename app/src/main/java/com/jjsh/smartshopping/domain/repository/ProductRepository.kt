package com.jjsh.smartshopping.domain.repository

import com.jjsh.smartshopping.domain.model.Product
import com.jjsh.smartshopping.domain.model.ProductRegistrationInfo
import java.io.File

interface ProductRepository {
    suspend fun getProducts(
        productId: Long,
        categoryId: Int? = null,
        direction: String = NEXT,
        keyword: String? = null
    ): Result<List<Product>>

    suspend fun getProductItem(
        productId: Long
    ): Result<Product>

    suspend fun findProductByBarcode(
        barcode: Long
    ): Result<Product>

    suspend fun registerProduct(
        productRegistrationInfo: ProductRegistrationInfo,
        imageIds : List<Long>
    ): Result<Unit>

    suspend fun uploadDetailImage(
        images : List<File>
    ): Result<List<Long>>

    companion object {
        const val NEXT = "next"
        const val PREV = "prev"
    }
}