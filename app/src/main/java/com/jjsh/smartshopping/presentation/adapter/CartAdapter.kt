package com.jjsh.smartshopping.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jjsh.smartshopping.R
import com.jjsh.smartshopping.databinding.ItemCartBinding
import com.jjsh.smartshopping.domain.model.CartItem

class CartAdapter(
    private val _updateCartItem: (CartItem) -> Unit = {},
    private val _deleteCartItem: (CartItem) -> Unit = {}
) : ListAdapter<CartItem, CartAdapter.CartItemViewHolder>(
    ItemDiffCallBack<CartItem>({ old, new -> old.id == new.id })
) {
    private val updateCartItem: (CartItem, Int) -> Unit
        get() = { item, position ->
            _updateCartItem(item)
            notifyItemChanged(position, item)
        }
    private val deleteCartItem: (CartItem) -> Unit get() = _deleteCartItem

    class CartItemViewHolder(
        private val binding: ItemCartBinding,
        private val updateCartItem: (CartItem, Int) -> Unit,
        private val deleteCartItem: (CartItem) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: CartItem) {
            binding.cartItem = item
            binding.btnMinus.setOnClickListener {
                if (item.amount > 1) {
                    item.amount--
                    updateCartItem(item, adapterPosition)
                }
            }
            binding.btnPlus.setOnClickListener {
                if (item.amount < 100) {
                    item.amount++
                    updateCartItem(item, adapterPosition)
                }
            }
            binding.ibDelete.setOnClickListener {
                deleteCartItem(item)
            }
            binding.cbIsChecked.setOnClickListener {
                item.isChecked = !item.isChecked
                updateCartItem(item, adapterPosition)
            }
        }

        companion object {
            fun create(
                parent: ViewGroup,
                updateCartItem: (CartItem, Int) -> Unit,
                deleteCartItem: (CartItem) -> Unit
            ): CartItemViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_cart, parent, false)
                val binding = ItemCartBinding.bind(view)
                return CartItemViewHolder(binding, updateCartItem, deleteCartItem)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartItemViewHolder {
        return CartItemViewHolder.create(parent, updateCartItem, deleteCartItem)
    }

    override fun onBindViewHolder(holder: CartItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
