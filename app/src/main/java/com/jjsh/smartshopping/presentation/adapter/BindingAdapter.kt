package com.jjsh.smartshopping.presentation.adapter

import android.graphics.Paint
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.jjsh.smartshopping.R
import com.jjsh.smartshopping.common.ImageLoader

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