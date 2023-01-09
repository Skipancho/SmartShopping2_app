package com.jjsh.smartshopping.domain.repository

import com.jjsh.smartshopping.domain.model.UserInfo

interface AuthRepository {
    fun signOut()

    fun getUserInfo(): UserInfo

    fun getUserToken(): String?

    suspend fun signin(userId: String, password: String): Result<Unit>

    suspend fun signup(
        userId: String,
        password: String,
        nickName: String,
        name: String
    ): Result<Unit>

    suspend fun validateUserId(userId: String): Result<Unit>

    suspend fun validateNickName(nickName: String): Result<Unit>

    suspend fun withdrawal(userId: String): Result<Unit>
}