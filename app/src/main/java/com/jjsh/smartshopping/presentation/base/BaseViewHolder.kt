package com.jjsh.smartshopping.presentation.base

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

class BaseViewHolder<B : ViewDataBinding, T>(
    private val binding: B,
    private val bind: (B, T, Int) -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: T) {
        bind(binding, item, adapterPosition)
    }
}