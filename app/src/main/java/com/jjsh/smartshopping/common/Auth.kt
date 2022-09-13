package com.jjsh.smartshopping.common

import android.content.Context
import androidx.preference.PreferenceManager
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Auth @Inject constructor(
    @ApplicationContext context: Context
) {
    private val prefs by lazy {
        PreferenceManager.getDefaultSharedPreferences(context)
    }

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

    fun signOut() {
        token = null
        refreshToken = null
        nickName = null
        userCode = -1
        userId = null
    }

    companion object {
        private const val TOKEN = "token"
        private const val REFRESH_TOKEN = "refresh_token"
        private const val USER_NICKNAME = "user_nickname"
        private const val USER_CODE = "user_code"
        private const val USER_ID = "user_id"
    }
}