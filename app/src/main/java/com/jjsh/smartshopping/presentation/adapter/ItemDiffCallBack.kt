package com.jjsh.smartshopping.presentation.adapter

import androidx.recyclerview.widget.DiffUtil

class ItemDiffCallBack<T : Any>(
    private val checkItemsTheSame: (T, T) -> Boolean,
    private val checkContentsTheSame: (T, T) -> Boolean = { old, new -> old == new }
) : DiffUtil.ItemCallback<T>() {
    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean =
        checkItemsTheSame(oldItem, newItem)

    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean =
        checkContentsTheSame(oldItem, newItem)
}