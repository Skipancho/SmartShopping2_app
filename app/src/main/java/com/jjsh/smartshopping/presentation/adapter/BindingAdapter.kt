package com.jjsh.smartshopping.presentation.adapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.jjsh.smartshopping.R
import com.jjsh.smartshopping.common.ImageLoader

@BindingAdapter("image")
fun ImageView.setImage(imageUrl : String){
    ImageLoader(this, context)
        .setPlaceHolder(R.drawable.icon)
        .setErrorImage(R.drawable.icon)
        .loadImage(imageUrl)
}