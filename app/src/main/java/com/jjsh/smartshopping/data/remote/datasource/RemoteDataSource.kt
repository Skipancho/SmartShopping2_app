package com.jjsh.smartshopping.data.remote.datasource

import com.jjsh.smartshopping.data.remote.request.PurchaseRequest
import com.jjsh.smartshopping.data.remote.request.SigninRequest
import com.jjsh.smartshopping.data.remote.request.SignupRequest
import com.jjsh.smartshopping.data.remote.response.ProductResponse
import com.jjsh.smartshopping.data.remote.response.PurchaseResponse
import com.jjsh.smartshopping.data.remote.response.SigninResponse
import retrofit2.http.Query

interface RemoteDataSource {

    suspend fun signin(signinRequest: SigninRequest): Result<SigninResponse>

    suspend fun signup(signupRequest: SignupRequest): Result<Unit>

    suspend fun validateUserId(userId: String): Result<Unit>

    suspend fun validateNickName(nickName: String): Result<Unit>

    suspend fun getProduct(id: Long): Result<ProductResponse>

    suspend fun findProductByBarcode(barcode: Long): Result<ProductResponse>

    suspend fun getProducts(
        productId: Long,
        categoryId: Int?,
        direction: String,
        keyword: String?
    ): Result<List<ProductResponse>>

    suspend fun registerPurchaseRecord(
        purchaseRequest: PurchaseRequest
    ): Result<Unit>

    suspend fun getPurchaseRecord(
        year: Int, month: Int
    ): Result<List<PurchaseResponse>>
}