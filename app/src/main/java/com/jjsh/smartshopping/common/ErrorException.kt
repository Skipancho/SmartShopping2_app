package com.jjsh.smartshopping.common

sealed class ErrorException : RuntimeException() {
    object SigninException : ErrorException()
    object SignupCheckedException : ErrorException()
    object PasswordDiffException : ErrorException()
    object EmptyMemberException : ErrorException()
    object ProductException : ErrorException()
    data class DefaultException(val msg: String?) : ErrorException()
}