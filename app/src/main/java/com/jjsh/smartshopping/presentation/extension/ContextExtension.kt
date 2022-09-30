package com.jjsh.smartshopping.presentation.extension

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.jjsh.smartshopping.R

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