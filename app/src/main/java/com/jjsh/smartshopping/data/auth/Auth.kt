package com.jjsh.smartshopping.data.auth

abstract class Auth {
    abstract var token: String?
    abstract var refreshToken: String?
    abstract var nickName: String?
    abstract var userCode: Long
    abstract var userId: String?
    abstract var userName: String?

    fun signOut() {
        token = null
        refreshToken = null
        nickName = null
        userCode = -1
        userId = null
        userName = null
    }

    fun signin(
        token: String,
        refreshToken: String,
        nickName: String,
        userCode: Long,
        userId: String,
        userName: String
    ) {
        this.token = token
        this.refreshToken = refreshToken
        this.nickName = nickName
        this.userCode = userCode
        this.userId = userId
        this.userName = userName
    }
}