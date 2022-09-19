package com.jjsh.smartshopping.data.repository

import com.jjsh.smartshopping.common.Auth
import com.jjsh.smartshopping.data.remote.datasource.RemoteDataSource
import com.jjsh.smartshopping.data.remote.request.SigninRequest
import com.jjsh.smartshopping.data.remote.request.SignupRequest
import com.jjsh.smartshopping.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val auth: Auth
) : AuthRepository {

    override suspend fun signin(userId: String, password: String): Result<Unit> {
        return remoteDataSource
            .signin(SigninRequest(userId, password))
            .mapCatching {
                auth.signin(it.token, it.refreshToken, it.nickName, it.userCode, userId)
            }
    }

    override suspend fun signup(
        userId: String,
        password: String,
        nickName: String,
        name: String
    ): Result<Unit> {
        return remoteDataSource.signup(SignupRequest(userId, password, nickName, name))
    }

    override suspend fun validateUserId(userId: String): Result<Unit> {
        return remoteDataSource.validateUserId(userId)
    }

    override suspend fun validateNickName(nickName: String): Result<Unit> {
        return remoteDataSource.validateNickName(nickName)
    }
}