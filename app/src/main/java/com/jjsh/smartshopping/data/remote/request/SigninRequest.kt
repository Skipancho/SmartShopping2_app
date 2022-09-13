package com.jjsh.smartshopping.data.remote.request

data class SigninRequest(
    val userId: String?,
    val password: String?
) {
    fun isNotValidUserId() = userId.isNullOrBlank()
    fun isNotValidPassword() = password.isNullOrBlank() || password.length !in 2..20
}

