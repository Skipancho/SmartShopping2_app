package com.jjsh.smartshopping.data.remote.request

data class SignupRequest(
    val userId: String,
    val password: String,
    val nickName: String,
    val name: String
)
