package com.jjsh.smartshopping.common

import android.content.SharedPreferences
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthImpl @Inject constructor(
    private val prefs: SharedPreferences
) : Auth() {
    override var token: String?
        get() = prefs.getString(TOKEN, null)
        set(value) = prefs.edit().putString(TOKEN, value).apply()

    override var refreshToken: String?
        get() = prefs.getString(REFRESH_TOKEN, null)
        set(value) = prefs.edit().putString(REFRESH_TOKEN, value).apply()

    override var nickName: String?
        get() = prefs.getString(USER_NICKNAME, null)
        set(value) = prefs.edit().putString(USER_NICKNAME, value).apply()

    override var userCode: Long
        get() = prefs.getLong(USER_CODE, -1)
        set(value) = prefs.edit().putLong(USER_CODE, value).apply()

    override var userId: String?
        get() = prefs.getString(USER_ID, null)
        set(value) = prefs.edit().putString(USER_ID, value).apply()

    override var userName: String?
        get() = prefs.getString(USER_NAME, null)
        set(value) = prefs.edit().putString(USER_NAME, value).apply()

    companion object {
        private const val TOKEN = "token"
        private const val REFRESH_TOKEN = "refresh_token"
        private const val USER_NICKNAME = "user_nickname"
        private const val USER_CODE = "user_code"
        private const val USER_ID = "user_id"
        private const val USER_NAME = "user_name"
    }
}