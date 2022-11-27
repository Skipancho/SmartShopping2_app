package com.jjsh.smartshopping.data.remote.api

import com.jjsh.smartshopping.data.remote.ApiResponse
import com.jjsh.smartshopping.data.remote.request.PurchaseRequest
import com.jjsh.smartshopping.data.remote.response.PurchaseResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface PurchaseService {
    @POST("/api/v1/purchase")
    suspend fun registerPurchaseRecord(
        @Body purchaseRequest: List<PurchaseRequest>
    ): ApiResponse<Unit>

    @GET("/api/v1/purchase")
    suspend fun getPurchaseRecord(
        @Query("year") year: Int,
        @Query("month") month: Int
    ): ApiResponse<List<PurchaseResponse>>
}