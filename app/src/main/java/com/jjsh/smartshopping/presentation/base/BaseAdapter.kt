package com.jjsh.smartshopping.presentation.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.ListAdapter
import com.jjsh.smartshopping.presentation.adapter.ItemDiffCallBack

abstract class BaseAdapter<B : ViewDataBinding, T : Any>(
    checkItemsTheSame: (T, T) -> Boolean,
    @LayoutRes private val resId: Int,
    private val bind: (B, T) -> Unit
) : ListAdapter<T, BaseViewHolder<B, T>>(
    ItemDiffCallBack<T>(
        checkItemsTheSame = checkItemsTheSame
    )
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<B, T> {
        val binding = DataBindingUtil.inflate<B>(
            LayoutInflater.from(parent.context),
            resId,
            parent,
            false
        )
        return BaseViewHolder(binding, bind)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<B, T>, position: Int) {
        holder.bind(getItem(position))
    }
}