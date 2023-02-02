package com.jjsh.smartshopping.data.repository

import com.jjsh.smartshopping.data.remote.datasource.RemoteDataSource
import com.jjsh.smartshopping.data.remote.request.ReviewRequest
import com.jjsh.smartshopping.data.remote.response.ReviewResponse
import com.jjsh.smartshopping.domain.model.Review
import com.jjsh.smartshopping.domain.repository.ReviewRepository
import javax.inject.Inject

class ReviewRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) : ReviewRepository {

    override suspend fun getReview(reviewId: Long): Result<Review> {
        return remoteDataSource.getReview(reviewId).mapCatching {
            responseToReview(it)
        }
    }

    override suspend fun writeReview(
        productId: Long,
        purchaseId: Long,
        score: Int,
        reviewText: String
    ): Result<Unit> {
        return remoteDataSource.writeReview(
            ReviewRequest(productId, purchaseId, score, reviewText)
        )
    }

    override suspend fun updateReview(
        productId: Long,
        purchaseId: Long,
        score: Int,
        reviewText: String
    ): Result<Unit> {
        return remoteDataSource.updateReview(
            ReviewRequest(productId, purchaseId, score, reviewText)
        )
    }

    override suspend fun deleteReview(reviewId: Long): Result<Unit> {
        return remoteDataSource.deleteReview(reviewId)
    }

    override suspend fun getReviews(productId: Long?): Result<List<Review>> {
        return remoteDataSource.getReviews(productId).mapCatching { list ->
            list.map {
                responseToReview(it)
            }
        }
    }

    private suspend fun responseToReview(reviewResponse: ReviewResponse): Review {
        val product = remoteDataSource.getProduct(reviewResponse.productId).getOrNull()
        val productName = product?.name ?: "none"
        val thumbnailUrl = product?.imagePaths?.firstOrNull() ?: ""
        return reviewResponse.toReview(productName, thumbnailUrl)
    }
}