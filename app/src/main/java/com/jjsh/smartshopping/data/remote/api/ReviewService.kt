package com.jjsh.smartshopping.data.remote.api

import com.jjsh.smartshopping.data.remote.ApiResponse
import com.jjsh.smartshopping.data.remote.request.ReviewRequest
import com.jjsh.smartshopping.data.remote.response.ReviewResponse
import retrofit2.http.*

interface ReviewService {

    @GET("/api/v1/review")
    suspend fun getReview(
        @Query("reviewId") reviewId : Long
    ): ApiResponse<ReviewResponse>

    @POST("/api/v1/review")
    suspend fun writeReview(
        @Body reviewRequest: ReviewRequest
    ): ApiResponse<Unit>

    @PUT("/api/v1/review")
    suspend fun updateReview(
        @Body reviewRequest: ReviewRequest
    ): ApiResponse<Unit>

    @DELETE("/api/v1/review")
    suspend fun deleteReview(
        @Query("reviewId") reviewId: Long
    ): ApiResponse<Unit>

    @GET("/api/v1/reviews")
    suspend fun getReviews(
        @Query("productId") productId: Long?
    ): ApiResponse<List<ReviewResponse>>
}