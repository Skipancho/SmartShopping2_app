package com.jjsh.smartshopping.data.remote.datasource

import com.jjsh.smartshopping.data.remote.request.SigninRequest
import com.jjsh.smartshopping.data.remote.request.SignupRequest
import com.jjsh.smartshopping.data.remote.response.SigninResponse

interface RemoteDataSource {
    suspend fun signin(signinRequest: SigninRequest): Result<SigninResponse>
    suspend fun signup(signupRequest: SignupRequest): Result<Unit>
    suspend fun validateUserId(userId: String): Result<Unit>
    suspend fun validateNickName(nickName : String): Result<Unit>
}