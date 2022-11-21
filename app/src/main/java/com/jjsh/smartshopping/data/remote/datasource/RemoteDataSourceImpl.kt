package com.jjsh.smartshopping.data.remote.datasource

import com.jjsh.smartshopping.common.ErrorException
import com.jjsh.smartshopping.data.remote.api.AuthService
import com.jjsh.smartshopping.data.remote.api.ProductService
import com.jjsh.smartshopping.data.remote.request.SigninRequest
import com.jjsh.smartshopping.data.remote.request.SignupRequest
import com.jjsh.smartshopping.data.remote.response.ProductResponse
import com.jjsh.smartshopping.data.remote.response.SigninResponse
import com.jjsh.smartshopping.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val authService: AuthService,
    private val productService: ProductService,
    @IoDispatcher private val ioDispatcher : CoroutineDispatcher
) : RemoteDataSource {
    override suspend fun signin(signinRequest: SigninRequest): Result<SigninResponse> {
        return withContext(ioDispatcher){
            runCatching {
                val response = authService.signin(signinRequest)
                response.successOrThrow()
                response.getOrThrow(ErrorException.SigninException)
            }
        }
    }

    override suspend fun signup(signupRequest: SignupRequest): Result<Unit> {
        return withContext(ioDispatcher){
            runCatching {
                authService.signup(signupRequest).successOrThrow()
            }
        }
    }

    override suspend fun validateUserId(userId: String): Result<Unit> {
        return withContext(ioDispatcher){
            runCatching {
                authService.validateUserId(userId).successOrThrow()
            }
        }
    }

    override suspend fun validateNickName(nickName: String): Result<Unit> {
        return withContext(ioDispatcher){
            runCatching {
                authService.validateNickName(nickName).successOrThrow()
            }
        }
    }

    override suspend fun getProduct(id: Long): Result<ProductResponse> {
        return withContext(ioDispatcher){
            runCatching {
                val response = productService.getProduct(id)
                response.successOrThrow()
                response.getOrThrow(ErrorException.ProductException)
            }
        }
    }

    override suspend fun findProductByBarcode(barcode: Long): Result<ProductResponse> {
        return withContext(ioDispatcher){
            runCatching {
                val response = productService.findProductByBarcode(barcode)
                response.successOrThrow()
                response.getOrThrow(ErrorException.ProductException)
            }
        }
    }

    override suspend fun getProducts(
        productId: Long,
        categoryId: Int?,
        direction: String,
        keyword: String?
    ): Result<List<ProductResponse>> {
        return withContext(ioDispatcher){
            runCatching {
                val response = productService.getProducts(productId, categoryId, direction, keyword)
                response.successOrThrow()
                response.getOrThrow(ErrorException.ProductException)
            }
        }
    }
}