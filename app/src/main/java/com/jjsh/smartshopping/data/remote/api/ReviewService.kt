package com.jjsh.smartshopping.data.remote.api

import com.jjsh.smartshopping.data.remote.ApiResponse
import com.jjsh.smartshopping.data.remote.request.ReviewRequest
import com.jjsh.smartshopping.data.remote.response.ReviewResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ReviewService {
    @POST("/api/v1/review")
    suspend fun writeReview(
        @Body reviewRequest: ReviewRequest
    ): ApiResponse<Unit>

    @GET("/api/v1/reviews")
    suspend fun getReviews(
        @Query("productId") productId: Long?
    ): ApiResponse<List<ReviewResponse>>
}