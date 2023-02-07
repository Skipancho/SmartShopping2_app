package com.jjsh.smartshopping.domain.repository

import com.jjsh.smartshopping.domain.model.Review

interface ReviewRepository {

    suspend fun getReview(
        reviewId: Long
    ): Result<Review>

    suspend fun writeReview(
        productId: Long,
        purchaseId: Long,
        score: Int,
        reviewText: String
    ): Result<Unit>

    suspend fun updateReview(
        productId: Long,
        purchaseId: Long,
        score: Int,
        reviewText: String
    ): Result<Unit>

    suspend fun deleteReview(
        reviewId: Long
    ): Result<Unit>

    suspend fun getReviews(
        productId: Long? = null
    ): Result<List<Review>>
}