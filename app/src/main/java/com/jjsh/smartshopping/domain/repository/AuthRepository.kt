package com.jjsh.smartshopping.domain.repository

interface AuthRepository {
    suspend fun signin(userId: String, password: String): Result<Unit>

    suspend fun signup(
        userId: String,
        password: String,
        nickName: String,
        name: String
    ): Result<Unit>

    suspend fun validateUserId(userId: String): Result<Unit>

    suspend fun validateNickName(nickName: String): Result<Unit>
}