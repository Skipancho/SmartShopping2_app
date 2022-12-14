package com.jjsh.smartshopping.data.fake.service

import com.jjsh.smartshopping.data.remote.ApiResponse
import com.jjsh.smartshopping.data.remote.api.ProductService
import com.jjsh.smartshopping.data.remote.response.ProductResponse

class FakeProductService(
    private val fakeProductResponses: List<ProductResponse>
) : ProductService {
    override suspend fun getProducts(
        productId: Long,
        categoryId: Int?,
        direction: String,
        keyword: String?,
        pageSize: Int
    ): ApiResponse<List<ProductResponse>> {
        return ApiResponse(true, fakeProductResponses, null)
    }

    override suspend fun getProduct(id: Long): ApiResponse<ProductResponse> {
        return ApiResponse(true, fakeProductResponses.find { it.id == id }, null)
    }

    override suspend fun findProductByBarcode(barcode: Long): ApiResponse<ProductResponse> {
        return ApiResponse(true, fakeProductResponses[barcode.toInt()], null)
    }
}