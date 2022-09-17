package com.jjsh.smartshopping.common

sealed class ErrorException : RuntimeException(){
    object SigninException : ErrorException()
    data class DefaultException(val msg : String?) : ErrorException()
}