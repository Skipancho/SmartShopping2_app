package com.jjsh.smartshopping.data.fake.service

import com.jjsh.smartshopping.data.remote.ApiResponse
import com.jjsh.smartshopping.data.remote.api.ReviewService
import com.jjsh.smartshopping.data.remote.request.ReviewRequest
import com.jjsh.smartshopping.data.remote.response.ReviewResponse

class FakeReviewService(
    private val fakeReviewResponse: List<ReviewResponse>
) : ReviewService {
    override suspend fun getReview(reviewId: Long): ApiResponse<ReviewResponse> {
        return fakeReviewResponse.find { it.id == reviewId }
            ?.let { ApiResponse(true, it) }
            ?: ApiResponse.error("review not found")
    }

    override suspend fun writeReview(reviewRequest: ReviewRequest): ApiResponse<Unit> {
        return ApiResponse(true)
    }

    override suspend fun updateReview(reviewRequest: ReviewRequest): ApiResponse<Unit> {
        return ApiResponse(true)
    }

    override suspend fun deleteReview(reviewId: Long): ApiResponse<Unit> {
        return ApiResponse(true)
    }

    override suspend fun getReviews(productId: Long?): ApiResponse<List<ReviewResponse>> {
        return ApiResponse(true, fakeReviewResponse)
    }
}