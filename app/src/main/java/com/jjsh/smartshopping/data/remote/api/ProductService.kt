package com.jjsh.smartshopping.data.remote.api

import com.jjsh.smartshopping.data.remote.ApiResponse
import com.jjsh.smartshopping.data.remote.response.ProductResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ProductService {
    @GET("/api/v1/products")
    suspend fun getProducts(
        @Query("productId") productId: Long,
        @Query("categoryId") categoryId: Int?,
        @Query("direction") direction: String,
        @Query("keyword") keyword: String? = null
    ): ApiResponse<List<ProductResponse>>

    @GET("/api/v1/products/{id}")
    suspend fun getProduct(
        @Path("id") id: Long
    ): ApiResponse<ProductResponse>
}