package com.jjsh.smartshopping.presentation.extension

import android.content.res.Resources

fun Int.dpToPx(): Int = this * Resources.getSystem().displayMetrics.density.toInt()