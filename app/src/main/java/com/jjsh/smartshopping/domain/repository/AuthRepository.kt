package com.jjsh.smartshopping.domain.repository

interface AuthRepository {
    suspend fun signin(userId: String, password: String): Result<Unit>
}