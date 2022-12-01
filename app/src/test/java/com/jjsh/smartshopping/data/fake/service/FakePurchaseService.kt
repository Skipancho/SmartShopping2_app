package com.jjsh.smartshopping.data.fake.service

import com.jjsh.smartshopping.data.remote.ApiResponse
import com.jjsh.smartshopping.data.remote.api.PurchaseService
import com.jjsh.smartshopping.data.remote.request.PurchaseRequest
import com.jjsh.smartshopping.data.remote.response.PurchaseResponse

class FakePurchaseService(
    private val fakePurchaseResponse: List<PurchaseResponse>
) : PurchaseService {
    override suspend fun registerPurchaseRecord(purchaseRequest: List<PurchaseRequest>): ApiResponse<Unit> {
        return ApiResponse(true, null, null)
    }

    override suspend fun getPurchaseRecord(
        year: Int,
        month: Int
    ): ApiResponse<List<PurchaseResponse>> {
        return ApiResponse(true,fakePurchaseResponse,null)
    }
}