package com.jjsh.smartshopping.data.fake.datasource

import com.jjsh.smartshopping.common.ErrorException
import com.jjsh.smartshopping.data.remote.datasource.RemoteDataSource
import com.jjsh.smartshopping.data.remote.request.PurchaseRequest
import com.jjsh.smartshopping.data.remote.request.ReviewRequest
import com.jjsh.smartshopping.data.remote.request.SigninRequest
import com.jjsh.smartshopping.data.remote.request.SignupRequest
import com.jjsh.smartshopping.data.remote.response.ProductResponse
import com.jjsh.smartshopping.data.remote.response.PurchaseResponse
import com.jjsh.smartshopping.data.remote.response.ReviewResponse
import com.jjsh.smartshopping.data.remote.response.SigninResponse
import java.util.*

class FakeRemoteDataSource : RemoteDataSource {
    override suspend fun signin(signinRequest: SigninRequest): Result<SigninResponse> {
        return Result.success(
            SigninResponse(
                "fakeToken",
                "fakeRefreshToken",
                "fakeNickName",
                1111L,
                "fakeUserName"
            )
        )
    }

    override suspend fun signup(signupRequest: SignupRequest): Result<Unit> {
        return Result.success(Unit)
    }

    override suspend fun validateUserId(userId: String): Result<Unit> {
        return Result.success(Unit)
    }

    override suspend fun validateNickName(nickName: String): Result<Unit> {
        return Result.success(Unit)
    }

    override suspend fun withdrawal(): Result<Unit> {
        return Result.success(Unit)
    }

    override suspend fun getProduct(id: Long): Result<ProductResponse> {
        return runCatching {
            listOf(
                ProductResponse(
                    1L, "name0", "desc0", 10000, 9000, "SELLABLE", 1L, 1, listOf("imageUrl0")
                ),
                ProductResponse(
                    2L, "name1", "desc1", 1000, 900, "SELLABLE", 2L, 2, listOf("imageUrl1")
                )
            ).find { it.id == id } ?: throw ErrorException.ProductException
        }
    }

    override suspend fun findProductByBarcode(barcode: Long): Result<ProductResponse> {
        return Result.success(
            ProductResponse(
                1L, "name0", "desc0", 10000, 9000, "SELLABLE", 1L, 1, listOf("imageUrl0")
            )
        )
    }

    override suspend fun getProducts(
        productId: Long,
        categoryId: Int?,
        direction: String,
        keyword: String?
    ): Result<List<ProductResponse>> {
        return Result.success(
            listOf(
                ProductResponse(
                    1L, "name0", "desc0", 10000, 9000, "SELLABLE", 1L, 1, listOf("imageUrl0")
                ),
                ProductResponse(
                    2L, "name1", "desc1", 1000, 900, "SELLABLE", 2L, 2, listOf("imageUrl1")
                )
            )
        )
    }

    override suspend fun registerPurchaseRecord(purchaseRequest: List<PurchaseRequest>): Result<Unit> {
        return Result.success(Unit)
    }

    override suspend fun getPurchaseRecord(year: Int, month: Int): Result<List<PurchaseResponse>> {
        return Result.success(
            listOf(
                PurchaseResponse(
                    1L,"category1",1L,"name1",10000,1,false
                ),
                PurchaseResponse(
                    2L,"category2",2L,"name2",9000,10, false
                )
            )
        )
    }

    private var _testDate : Date? = null
    private val testDate : Date get() = _testDate ?: error("testDate is null")

    fun setTestDate(date: Date) {
        _testDate = date
    }

    override suspend fun getReview(reviewId: Long): Result<ReviewResponse> {
        return runCatching {
            listOf(
                ReviewResponse(
                    1L,1L,"nickname1",1L,1,"reviewText1", testDate
                ),
                ReviewResponse(
                    2L,2L,"nickname2",2L,2,"reviewText2", testDate
                )
            ).find { it.id == reviewId } ?: throw ErrorException.ReviewException
        }
    }

    override suspend fun writeReview(reviewRequest: ReviewRequest): Result<Unit> {
        return Result.success(Unit)
    }

    override suspend fun updateReview(reviewRequest: ReviewRequest): Result<Unit> {
        return Result.success(Unit)
    }

    override suspend fun deleteReview(reviewId: Long): Result<Unit> {
        return Result.success(Unit)
    }

    override suspend fun getReviews(productId: Long?): Result<List<ReviewResponse>> {
        return runCatching {
            listOf(
                ReviewResponse(
                    1L,1L,"nickname1",1L,1,"reviewText1", testDate
                ),
                ReviewResponse(
                    2L,2L,"nickname2",2L,2,"reviewText2", testDate
                )
            )
        }
    }
}