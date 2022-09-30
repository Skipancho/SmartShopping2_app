package com.jjsh.smartshopping.presentation

import android.content.Context
import com.jjsh.smartshopping.R
import com.jjsh.smartshopping.common.ErrorException
import com.jjsh.smartshopping.presentation.extension.shortToast

class ErrorHandler(private val context: Context) {
    fun errorHandling(e: Throwable) {
        with(context) {
            when (e) {
                is ErrorException.DefaultException -> {
                    shortToast(e.msg)
                }
                is ErrorException.SigninException -> {
                    shortToast(getString(R.string.err_msg_signin))
                }
                is ErrorException.ProductException -> {
                    shortToast(getString(R.string.err_msg_product))
                }
                else -> {
                    shortToast(getString(R.string.err_msg_unknown))
                }
            }
        }
    }
}