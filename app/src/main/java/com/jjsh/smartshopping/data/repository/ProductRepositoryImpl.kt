package com.jjsh.smartshopping.data.repository

import com.jjsh.smartshopping.data.remote.datasource.RemoteDataSource
import com.jjsh.smartshopping.domain.model.Product
import com.jjsh.smartshopping.domain.repository.ProductRepository
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) : ProductRepository {

    override suspend fun getProducts(
        productId: Long,
        categoryId: Int?,
        direction: String,
        keyword: String?
    ): Result<List<Product>> {
        return remoteDataSource.getProducts(productId, categoryId, direction, keyword)
            .mapCatching { products ->
                products.map { it.toProduct() }
            }
    }

    override suspend fun getProduct(productId: Long): Result<Product> {
        return remoteDataSource.getProduct(productId).mapCatching { it.toProduct() }
    }
}