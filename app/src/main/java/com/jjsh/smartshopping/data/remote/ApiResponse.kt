package com.jjsh.smartshopping.data.remote

data class ApiResponse<T>(
    val success: Boolean,
    val data: T? = null,
    val message: String? = null
)