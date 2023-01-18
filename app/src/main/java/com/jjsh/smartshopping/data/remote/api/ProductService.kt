package com.jjsh.smartshopping.data.remote.api

import com.jjsh.smartshopping.data.remote.ApiResponse
import com.jjsh.smartshopping.data.remote.request.ProductRegistrationRequest
import com.jjsh.smartshopping.data.remote.response.ProductResponse
import okhttp3.MultipartBody
import retrofit2.http.*

interface ProductService {
    @GET("/api/v1/products")
    suspend fun getProducts(
        @Query("productId") productId: Long,
        @Query("categoryId") categoryId: Int?,
        @Query("direction") direction: String,
        @Query("keyword") keyword: String? = null,
        @Query("limit") pageSize: Int = 10
    ): ApiResponse<List<ProductResponse>>

    @GET("/api/v1/products/{id}")
    suspend fun getProduct(
        @Path("id") id: Long
    ): ApiResponse<ProductResponse>

    @GET("/api/v1/product/{barcode}")
    suspend fun findProductByBarcode(
        @Path("barcode") barcode: Long
    ): ApiResponse<ProductResponse>

    @POST("/api/v1/products")
    suspend fun registerProduct(
        @Body productRegistrationRequest: ProductRegistrationRequest
    ): ApiResponse<Unit>

    @Multipart
    @POST("/api/v1/image")
    suspend fun uploadDetailImage(
        @Part image : MultipartBody.Part
    ): ApiResponse<Long>
}