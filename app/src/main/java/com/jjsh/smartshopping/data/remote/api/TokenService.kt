package com.jjsh.smartshopping.data.remote.api

import com.jjsh.smartshopping.data.remote.ApiResponse
import retrofit2.http.POST
import retrofit2.http.Query

interface TokenService {
    @POST("/api/v1/refresh_token")
    suspend fun refreshToken(
        @Query("grant_type") grantType: String = "refresh_token"
    ): ApiResponse<String>
}