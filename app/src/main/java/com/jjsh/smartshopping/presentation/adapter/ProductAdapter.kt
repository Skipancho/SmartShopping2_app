package com.jjsh.smartshopping.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jjsh.smartshopping.R
import com.jjsh.smartshopping.databinding.ItemProductGridBinding
import com.jjsh.smartshopping.databinding.ItemProductLinearBinding
import com.jjsh.smartshopping.domain.model.Product

class ProductAdapter : ListAdapter<Product, RecyclerView.ViewHolder>(
    ItemDiffCallBack<Product>({ old, new -> old.id == new.id })
) {
    private var _isGrid: Boolean = true
    val isGrid get() = _isGrid

    fun setGrid(isGrid: Boolean) {
        _isGrid = isGrid
        notifyItemRangeChanged(0, currentList.size)
    }

    class ProductGridViewHolder(
        private val binding: ItemProductGridBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Product) {
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

    class ProductLinearViewHolder(
        private val binding: ItemProductLinearBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Product) {
            binding.product = item
        }

        companion object {
            fun create(parent: ViewGroup): ProductLinearViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_product_linear, parent, false)
                val binding = ItemProductLinearBinding.bind(view)
                return ProductLinearViewHolder(binding)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (isGrid) GRID else LINEAR
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            GRID -> ProductGridViewHolder.create(parent)
            else -> ProductLinearViewHolder.create(parent)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ProductGridViewHolder -> {
                holder.bind(getItem(position))
            }
            is ProductLinearViewHolder -> {
                holder.bind(getItem(position))
            }
        }
    }

    companion object {
        const val GRID = 0
        const val LINEAR = 1
    }
}