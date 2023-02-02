package com.jjsh.smartshopping.presentation.adapter

import com.jjsh.smartshopping.R
import com.jjsh.smartshopping.databinding.ItemCartBinding
import com.jjsh.smartshopping.domain.model.CartItem
import com.jjsh.smartshopping.presentation.base.BaseAdapter

class CartAdapter(
    private val _updateCartItem: (CartItem) -> Unit = {},
    private val deleteCartItem: (CartItem) -> Unit = {}
) : BaseAdapter<ItemCartBinding,CartItem> (
    checkItemsTheSame = { old, new -> old.id == new.id },
    R.layout.item_cart
){
    private val updateCartItem: (CartItem, Int) -> Unit
        get() = { item, position ->
            _updateCartItem(item)
            notifyItemChanged(position, item)
        }

    override val bind: (ItemCartBinding, CartItem, Int) -> Unit
        get() = { binding, item, position ->
            binding.cartItem = item
            binding.btnMinus.setOnClickListener {
                if (item.amount > 1) {
                    item.amount--
                    updateCartItem(item, position)
                }
            }
            binding.btnPlus.setOnClickListener {
                if (item.amount < 100) {
                    item.amount++
                    updateCartItem(item, position)
                }
            }
            binding.ibDelete.setOnClickListener {
                deleteCartItem(item)
            }
            binding.cbIsChecked.setOnClickListener {
                item.isChecked = !item.isChecked
                updateCartItem(item, position)
            }
        }
}
