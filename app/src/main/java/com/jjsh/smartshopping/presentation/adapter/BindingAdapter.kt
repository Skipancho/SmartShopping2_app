package com.jjsh.smartshopping.presentation.adapter

import android.graphics.Color
import android.graphics.Paint
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jjsh.smartshopping.R
import com.jjsh.smartshopping.common.DateUtil
import com.jjsh.smartshopping.common.ImageLoader
import com.jjsh.smartshopping.presentation.decoration.CustomDividerDecoration
import com.jjsh.smartshopping.presentation.extension.dpToPx
import java.util.*

@BindingAdapter("image")
fun ImageView.setImage(imageUrl: String) {
    ImageLoader(this, context)
        .setPlaceHolder(R.drawable.icon)
        .setErrorImage(R.drawable.icon)
        .loadImage(imageUrl)
}

@BindingAdapter("centerLine")
fun TextView.setCenterLine(visible: Boolean) {
    paintFlags = if (visible) paintFlags.or(Paint.STRIKE_THRU_TEXT_FLAG) else 0
}

@BindingAdapter("dotFormatDate")
fun TextView.setDotFormatDate(date: Date) {
    text = DateUtil.dateToString(date)
}