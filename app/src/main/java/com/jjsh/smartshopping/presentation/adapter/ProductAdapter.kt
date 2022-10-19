package com.jjsh.smartshopping.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jjsh.smartshopping.R
import com.jjsh.smartshopping.databinding.ItemProductGridBinding
import com.jjsh.smartshopping.domain.model.Product

class ProductAdapter : ListAdapter<Product, RecyclerView.ViewHolder>(
    ItemDiffCallBack<Product>({ old, new -> old.id == new.id })
) {
    private var isGrid: Boolean = true

    private fun setGrid(isGrid: Boolean) {
        this.isGrid = isGrid
        notifyDataSetChanged()
    }

    class ProductGridViewHolder(
        private val binding: ItemProductGridBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item : Product){
            binding.product = item
        }

        companion object {
            fun create(parent: ViewGroup): ProductGridViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_product_grid, parent, false)
                val binding = ItemProductGridBinding.bind(view)
                return ProductGridViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ProductGridViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is ProductGridViewHolder -> {
                holder.bind(getItem(position))
            }
        }
    }
}