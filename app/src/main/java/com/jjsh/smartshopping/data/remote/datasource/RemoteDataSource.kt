package com.jjsh.smartshopping.data.remote.datasource

import com.jjsh.smartshopping.data.remote.request.*
import com.jjsh.smartshopping.data.remote.response.ProductResponse
import com.jjsh.smartshopping.data.remote.response.PurchaseResponse
import com.jjsh.smartshopping.data.remote.response.ReviewResponse
import com.jjsh.smartshopping.data.remote.response.SigninResponse
import okhttp3.MultipartBody

interface RemoteDataSource {

    suspend fun signin(signinRequest: SigninRequest): Result<SigninResponse>

    suspend fun signup(signupRequest: SignupRequest): Result<Unit>

    suspend fun validateUserId(userId: String): Result<Unit>

    suspend fun validateNickName(nickName: String): Result<Unit>

    suspend fun withdrawal(): Result<Unit> //회원 탈퇴

    suspend fun getProduct(id: Long): Result<ProductResponse>

    suspend fun findProductByBarcode(barcode: Long): Result<ProductResponse>

    suspend fun getProducts(
        productId: Long,
        categoryId: Int?,
        direction: String,
        keyword: String?
    ): Result<List<ProductResponse>>

    suspend fun registerPurchaseRecord(
        purchaseRequest: List<PurchaseRequest>
    ): Result<Unit>

    suspend fun getPurchaseRecord(
        year: Int, month: Int
    ): Result<List<PurchaseResponse>>

    suspend fun getReview(
        reviewId: Long
    ): Result<ReviewResponse>

    suspend fun writeReview(
        reviewRequest: ReviewRequest
    ): Result<Unit>

    suspend fun updateReview(
        reviewRequest: ReviewRequest
    ): Result<Unit>

    suspend fun deleteReview(
        reviewId: Long
    ): Result<Unit>

    suspend fun getReviews(
        productId: Long?
    ): Result<List<ReviewResponse>>

    suspend fun registerProduct(
        productRegistrationRequest: ProductRegistrationRequest
    ): Result<Unit>

    suspend fun uploadImages(
        images: List<MultipartBody.Part>
    ): Result<List<Long>>
}