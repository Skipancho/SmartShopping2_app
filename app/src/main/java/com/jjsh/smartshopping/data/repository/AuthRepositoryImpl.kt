package com.jjsh.smartshopping.data.repository

import com.jjsh.smartshopping.data.auth.Auth
import com.jjsh.smartshopping.data.remote.datasource.RemoteDataSource
import com.jjsh.smartshopping.data.remote.request.SigninRequest
import com.jjsh.smartshopping.data.remote.request.SignupRequest
import com.jjsh.smartshopping.domain.model.UserInfo
import com.jjsh.smartshopping.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val auth: Auth
) : AuthRepository {

    override fun signOut() {
        auth.signOut()
    }

    override fun getUserInfo(): UserInfo {
        return UserInfo(auth.userId, auth.userName, auth.nickName)
    }

    override fun getUserToken(): String? {
        return auth.token
    }

    override suspend fun signin(userId: String, password: String): Result<Unit> {
        return remoteDataSource
            .signin(SigninRequest(userId, password))
            .mapCatching {
                auth.signin(
                    it.token,
                    it.refreshToken,
                    it.nickName,
                    it.userCode,
                    userId,
                    it.userName
                )
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