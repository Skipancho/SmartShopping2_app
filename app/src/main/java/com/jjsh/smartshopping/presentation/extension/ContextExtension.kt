package com.jjsh.smartshopping.presentation.extension

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.jjsh.smartshopping.R
import com.jjsh.smartshopping.common.ErrorException

inline fun <reified A : Activity> Context.start() {
    val intent = Intent(this, A::class.java)
    startActivity(intent)
}

inline fun <reified A : Activity> Context.clearTaskAndStart() {
    val intent = Intent(this, A::class.java).apply {
        addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }
    startActivity(intent)
}

fun Context.shortToast(msg: String?) {
    Toast.makeText(this, msg ?: this.getString(R.string.err_msg_unknown), Toast.LENGTH_SHORT).show()
}

fun Context.errorHandling(e: Throwable) {
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
        is ErrorException.SignupCheckedException -> {
            shortToast(getString(R.string.err_msg_signup_checked))
        }
        is ErrorException.EmptyMemberException -> {
            shortToast(getString(R.string.err_msg_empty_member))
        }
        else -> {
            shortToast(getString(R.string.err_msg_unknown))
        }
    }
}