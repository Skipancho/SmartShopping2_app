package com.jjsh.smartshopping.data.remote

import com.jjsh.smartshopping.common.ErrorException

data class ApiResponse<T>(
    val success: Boolean,
    val data: T? = null,
    val message: String? = null
){
    fun getOrThrow(e : Throwable): T  = data ?: throw e

    fun successOrThrow() {
        if (!success)
            throw ErrorException.DefaultException(message)
    }

    companion object{
        inline fun <reified T> error(message: String? = null) =
            ApiResponse(false,null as T?, message)
    }
}