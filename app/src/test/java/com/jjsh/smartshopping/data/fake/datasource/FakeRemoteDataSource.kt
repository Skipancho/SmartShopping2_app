package com.jjsh.smartshopping.data.fake.datasource

import com.jjsh.smartshopping.data.remote.datasource.RemoteDataSource
import com.jjsh.smartshopping.data.remote.request.PurchaseRequest
import com.jjsh.smartshopping.data.remote.request.SigninRequest
import com.jjsh.smartshopping.data.remote.request.SignupRequest
import com.jjsh.smartshopping.data.remote.response.ProductResponse
import com.jjsh.smartshopping.data.remote.response.PurchaseResponse
import com.jjsh.smartshopping.data.remote.response.SigninResponse

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

    override suspend fun getProduct(id: Long): Result<ProductResponse> {
        return Result.success(
            ProductResponse(
                1L, "name0", "desc0", 10000, 9000, "SELLABLE", 1L, 1, listOf("imageUrl0")
            )
        )
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
                    1L,"category1",1L,"name1",10000,1
                ),
                PurchaseResponse(
                    2L,"category2",2L,"name2",9000,10
                )
            )
        )
    }
}