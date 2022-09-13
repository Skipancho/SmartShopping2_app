package com.jjsh.smartshopping.data.remote.api

import com.jjsh.smartshopping.data.remote.ApiResponse
import com.jjsh.smartshopping.data.remote.request.SigninRequest
import com.jjsh.smartshopping.data.remote.request.SignupRequest
import com.jjsh.smartshopping.data.remote.response.SigninResponse
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface AuthService {
    @POST("/api/v1/signin")
    suspend fun signin(
        @Body signinRequest: SigninRequest
    ): ApiResponse<SigninResponse>

    @POST("/api/v1/users")
    suspend fun signup(
        @Body signupRequest: SignupRequest
    ) : ApiResponse<Void>

    @POST("/api/v1/users/id")
    suspend fun validateUserId(
        @Query("userId") userId : String
    ) : ApiResponse<Void>

    @POST("/api/v1/users/nickname")
    suspend fun validateNickName(
        @Query("nickName") nickName : String
    ) : ApiResponse<Void>
}