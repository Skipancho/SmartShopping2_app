package com.jjsh.smartshopping.common

import android.content.SharedPreferences
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Auth @Inject constructor(
    private val prefs: SharedPreferences
) {
    var token: String?
        get() = prefs.getString(TOKEN, null)
        set(value) = prefs.edit().putString(TOKEN, value).apply()

    var refreshToken: String?
        get() = prefs.getString(REFRESH_TOKEN, null)
        set(value) = prefs.edit().putString(REFRESH_TOKEN, value).apply()

    var nickName: String?
        get() = prefs.getString(USER_NICKNAME, null)
        set(value) = prefs.edit().putString(USER_NICKNAME, value).apply()

    var userCode: Long
        get() = prefs.getLong(USER_CODE, -1)
        set(value) = prefs.edit().putLong(USER_CODE, value).apply()

    var userId: String?
        get() = prefs.getString(USER_ID, null)
        set(value) = prefs.edit().putString(USER_ID, value).apply()

    var userName: String?
        get() = prefs.getString(USER_NAME, null)
        set(value) = prefs.edit().putString(USER_NAME, value).apply()

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

    companion object {
        private const val TOKEN = "token"
        private const val REFRESH_TOKEN = "refresh_token"
        private const val USER_NICKNAME = "user_nickname"
        private const val USER_CODE = "user_code"
        private const val USER_ID = "user_id"
        private const val USER_NAME = "user_name"
    }
}