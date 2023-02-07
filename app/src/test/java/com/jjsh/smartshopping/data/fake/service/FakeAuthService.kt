package com.jjsh.smartshopping.data.fake.service

import com.jjsh.smartshopping.data.remote.ApiResponse
import com.jjsh.smartshopping.data.remote.api.AuthService
import com.jjsh.smartshopping.data.remote.request.SigninRequest
import com.jjsh.smartshopping.data.remote.request.SignupRequest
import com.jjsh.smartshopping.data.remote.response.SigninResponse

class FakeAuthService(
    private val fakeResponse: SigninResponse
) : AuthService {
    override suspend fun signin(signinRequest: SigninRequest): ApiResponse<SigninResponse> {
        return ApiResponse(true, fakeResponse, null)
    }

    override suspend fun signup(signupRequest: SignupRequest): ApiResponse<Unit> {
        return ApiResponse(true)
    }

    override suspend fun validateUserId(userId: String): ApiResponse<Unit> {
        return ApiResponse(true)
    }

    override suspend fun validateNickName(nickName: String): ApiResponse<Unit> {
        return ApiResponse(true)
    }

    override suspend fun withdrawal(): ApiResponse<Unit> {
        return ApiResponse(true)
    }
}